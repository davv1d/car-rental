package com.davv1d.facade;

import com.davv1d.domain.UserDto;
import com.davv1d.domain.login.LoginRequest;
import com.davv1d.domain.login.LoginRequestDto;
import com.davv1d.domain.login.LoginResponseDto;
import com.davv1d.mapper.UserMapper;
import com.davv1d.mapper.login.LoginRequestMapper;
import com.davv1d.security.JwtProvider;
import com.davv1d.service.UserService;
import com.davv1d.service.validate.RoleValidator;
import com.davv1d.service.validate.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private LoginRequestMapper loginRequestMapper;

    @Autowired
    private UserMapper userMapper;

    public ResponseEntity<?> authenticateUser(LoginRequestDto loginRequestDto) {
        LoginRequest loginRequest = loginRequestMapper.mapToLoginRequest(loginRequestDto);
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                ));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String jwtToken = jwtProvider.generateJwtToken(authenticate);
        return ResponseEntity.ok(new LoginResponseDto(jwtToken));
    }

    public String registerUser(UserDto userDto) {
        return RoleValidator.roleValidator(userDto)
                .map(userMapper::mapToUser)
                .flatMap(userValidator::saveUserValidate)
                .effect(userService::saveUser);
    }
}
