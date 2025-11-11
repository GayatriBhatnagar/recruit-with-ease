package com.system.recruitwithease.modules.auth.service;

import com.system.recruitwithease.modules.auth.dto.AuthResponse;
import com.system.recruitwithease.modules.auth.dto.LoginRequest;
import com.system.recruitwithease.modules.auth.dto.RegisterRequest;

import java.io.IOException;

public interface AuthService {
    String registerRequest(RegisterRequest registerRequest) throws Exception;

    AuthResponse loginRequest(LoginRequest loginRequest) throws IOException;
}
