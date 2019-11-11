package com.davv1d.service.db;

import com.davv1d.domain.user.User;
import com.davv1d.domain.user.role.Role;
import com.davv1d.functional.Result;
import com.davv1d.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.NotNull;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDbServiceTestSuite {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDbService userDbService;

    @Test
    public void shouldSaveUser() {
        //Given
        User user = new User("test name", "test password", "test@email.com", "client");
        //When
        Result<User> userResult = userDbService.saveUser(user);
        //Then
        assertEquals(Result.Success.class, userResult.getClass());
        //Clean up
        userRepository.deleteByUsername(user.getUsername());
    }
}