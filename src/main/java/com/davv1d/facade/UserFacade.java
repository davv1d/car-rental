package com.davv1d.facade;

import com.davv1d.domain.user.User;
import com.davv1d.domain.user.login.*;
import com.davv1d.functional.Result;
import com.davv1d.mapper.login.LoginRequestMapper;
import com.davv1d.mapper.login.SingUpMapper;
import com.davv1d.security.JwtProvider;
import com.davv1d.service.db.UserDbService;
import com.davv1d.service.db.UserLoginDbService;
import com.davv1d.service.validate.RoleValidator;
import com.davv1d.service.validate.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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

    @Autowired
    private UserLoginDbService userLoginDbService;

    public ResponseEntity<?> authenticateUser(LoginRequestDto loginRequestDto) {
        LoginRequest loginRequest = loginRequestMapper.mapToLoginRequest(loginRequestDto);
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                ));
        String auth = null;
        for (GrantedAuthority authority : authenticate.getAuthorities()) {
            auth = authority.getAuthority();
        }
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String jwtToken = jwtProvider.generateJwtToken(authenticate);
        userLoginDbService.save(new UserLogin(loginRequest.getUsername(), LocalDateTime.now()));
        return ResponseEntity.ok(new LoginResponseDto(jwtToken, auth));
    }

    public ResponseEntity<?> registerUser(SingUpDto singUpDto) {
        String role = singUpDto.getRole();
        if (RoleValidator.isRoleExist(role)) {
            User user = signUpMapper.mapToUser(singUpDto);
            return userValidator.saveUserValidate(user)
                    .effectHttp(userDbService::saveUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect role");
        }
    }
}
