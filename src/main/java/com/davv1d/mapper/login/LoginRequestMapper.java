package com.davv1d.mapper.login;

import com.davv1d.domain.login.LoginRequest;
import com.davv1d.domain.login.LoginRequestDto;
import org.springframework.stereotype.Component;

@Component
public class LoginRequestMapper {
    public LoginRequest mapToLoginRequest(final LoginRequestDto loginRequestDto) {
        return new LoginRequest(loginRequestDto.getUsername(), loginRequestDto.getPassword());
    }
}
