package com.system.recruitwithease.modules.auth.dto;

import com.system.recruitwithease.modules.auth.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
