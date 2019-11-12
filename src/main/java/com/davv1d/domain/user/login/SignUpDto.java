package com.davv1d.domain.user.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SignUpDto {
    private String username;
    private String password;
    private String email;
    private String role;
}
