package com.davv1d.domain.user.login;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginResponseDto {
    private String token;
    private String type = "Bearer";
    private String role;

    public LoginResponseDto(String token) {
        this.token = token;
    }

    public LoginResponseDto(String token, String role) {
        this.token = token;
        this.role = role;
    }
}
