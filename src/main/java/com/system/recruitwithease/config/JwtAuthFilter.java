package com.system.recruitwithease.config;

import com.system.recruitwithease.modules.auth.security.CustomUserDetailService;
import com.system.recruitwithease.modules.auth.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {


    private final CustomUserDetailService customUserDetailService;
    private final JWTService jwtService;

    public JwtAuthFilter(CustomUserDetailService customUserDetailService, JWTService jwtService) {
        this.customUserDetailService = customUserDetailService;
        this.jwtService = jwtService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
         String authHeader = request.getHeader("Authorization");
         String username=null;
         String token=null;
        try {
// 1. get authHeader

// 2. if authheader is there check for bearer and assign username and token
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtService.extractUserName(token);
            }
/// 3. if username is notnull but not authenticated load userdetauks for setting authentication
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

//4. validate the token , if token is valid load the usernamepasswordauthenticationtoken
                if (jwtService.validateToken(token)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

            }
        catch(Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }
}

