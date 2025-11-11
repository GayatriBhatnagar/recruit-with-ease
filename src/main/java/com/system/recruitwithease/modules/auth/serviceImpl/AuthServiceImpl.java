package com.system.recruitwithease.modules.auth.serviceImpl;

import com.system.recruitwithease.modules.auth.dto.AuthResponse;
import com.system.recruitwithease.modules.auth.dto.LoginRequest;
import com.system.recruitwithease.modules.auth.dto.RegisterRequest;
import com.system.recruitwithease.modules.auth.entity.User;
import com.system.recruitwithease.modules.auth.enums.Role;
import com.system.recruitwithease.modules.auth.repository.UserRepository;
import com.system.recruitwithease.modules.auth.service.AuthService;
import com.system.recruitwithease.modules.auth.service.JWTService;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
  //  @Autowired
    private final UserRepository userRepository;
    private final JWTService jwtService;

  public AuthServiceImpl(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public String registerRequest(RegisterRequest registerRequest) throws Exception {
     User emailExist = userRepository.findByEmail(registerRequest.getEmail());
    if(emailExist!=null){
        throw new Exception("User already exist please login");
    }
        User user = User.builder().email(registerRequest.getEmail()).userName(registerRequest.getUserName()).firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName()).role(registerRequest.getRole()).password(registerRequest.getPassword()).build();
       userRepository.save(user);
       return "Registered User";
    }

    @Override
    public AuthResponse loginRequest(LoginRequest loginRequest) throws IOException {
       User userFound = userRepository.findByEmail(loginRequest.getEmail());
       if(userFound!=null) {
           Long uid = userFound.
                   getId();
           Role role = userFound.getRole();
           return getJwt( loginRequest.getUserName(), role );
       }
        throw new IOException("User does not exist");
    }

    private AuthResponse getJwt(String userName, Role role) {

       Instant now = Instant.now();

       Date expiredAtAccessToken = Date.from(now.plus(Duration.ofMinutes(5)));
       Date expiredAtRefreshToken = Date.from(now.plus(Duration.ofMinutes(10)));
       String accessToken =  jwtService.generateToken(userName, expiredAtAccessToken);
       String refreshToken = jwtService.generateToken(userName, expiredAtRefreshToken);
    return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).role(String.valueOf(role)).userName(userName).build();
    }
    }
