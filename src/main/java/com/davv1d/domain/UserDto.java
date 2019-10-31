package com.davv1d.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
}
