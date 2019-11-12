package com.davv1d.controller;

import com.davv1d.domain.user.login.LoginRequestDto;
import com.davv1d.domain.user.login.SignUpDto;
import com.davv1d.facade.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/auth")
public class AuthenticationController {
    @Autowired
    private UserFacade userFacade;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDto loginRequestDto) {
        return userFacade.authenticateUser(loginRequestDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        return userFacade.registerUser(signUpDto);
    }
}
