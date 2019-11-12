package com.davv1d.facade;

import com.davv1d.domain.user.login.*;
import com.davv1d.mapper.login.LoginRequestMapper;
import com.davv1d.mapper.user.UserMapper;
import com.davv1d.repository.UserLoginRepository;
import com.davv1d.security.JwtProvider;
import com.davv1d.service.db.UserDbDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AuthenticationFacade {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDbDetailsService userDbDetailsService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private LoginRequestMapper loginRequestMapper;

    @Autowired
    private UserLoginRepository userLoginRepository;

    public ResponseEntity<?> authenticateUser(LoginRequestDto loginRequestDto) {
        LoginRequest loginRequest = loginRequestMapper.mapToLoginRequest(loginRequestDto);
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                ));
        String role = null;
        for (GrantedAuthority authority : authenticate.getAuthorities()) {
            role = authority.getAuthority();
        }
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String jwtToken = jwtProvider.generateJwtToken(authenticate);
        userLoginRepository.save(new UserLogin(loginRequest.getUsername(), LocalDateTime.now()));
        return ResponseEntity.ok(new LoginResponseDto(jwtToken, role));
    }

    public ResponseEntity<?> registerUser(SignUpDto signUpDto) {
        return userDbDetailsService.saveUser(userMapper.mapToUser(signUpDto))
                .effect(user1 -> ResponseEntity.ok(user1.getUsername()), s -> ResponseEntity.badRequest().body(s));
    }
}
