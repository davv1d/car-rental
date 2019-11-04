package com.davv1d.facade;

import com.davv1d.domain.user.login.SingUpDto;
import com.davv1d.domain.user.login.LoginRequest;
import com.davv1d.domain.user.login.LoginRequestDto;
import com.davv1d.domain.user.login.LoginResponseDto;
import com.davv1d.mapper.login.SingUpMapper;
import com.davv1d.mapper.login.LoginRequestMapper;
import com.davv1d.security.JwtProvider;
import com.davv1d.service.db.UserDbService;
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
    private UserDbService userDbService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private LoginRequestMapper loginRequestMapper;

    @Autowired
    private SingUpMapper signUpMapper;

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

    public ResponseEntity<?> registerUser(SingUpDto singUpDto) {
        return RoleValidator.roleValidator(singUpDto)
                .map(signUpMapper::mapToUser)
                .flatMap(userValidator::saveUserValidate)
                .effectHttp(userDbService::saveUser);
    }
}
