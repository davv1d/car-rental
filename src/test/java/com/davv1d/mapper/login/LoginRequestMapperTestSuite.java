package com.davv1d.mapper.login;

import com.davv1d.domain.user.login.LoginRequest;
import com.davv1d.domain.user.login.LoginRequestDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginRequestMapperTestSuite {
    @Autowired
    private LoginRequestMapper loginRequestMapper;

    @Test
    public void shouldFetchLoginAndPassword() {
        //Given
        LoginRequestDto loginRequestDto = new LoginRequestDto("login", "password");
        //When
        LoginRequest request = loginRequestMapper.mapToLoginRequest(loginRequestDto);
        //Then
        Assert.assertEquals(loginRequestDto.getUsername(), request.getUsername());
        Assert.assertEquals(loginRequestDto.getPassword(), request.getPassword());
    }
}