package com.davv1d.domain.user.login;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LoginResponseDto {
    private String token;
    private String type = "Bearer";

    public LoginResponseDto(String token) {
        this.token = token;
    }
}
