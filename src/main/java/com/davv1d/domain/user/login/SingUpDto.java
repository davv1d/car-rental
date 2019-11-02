package com.davv1d.domain.user.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SingUpDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
}
