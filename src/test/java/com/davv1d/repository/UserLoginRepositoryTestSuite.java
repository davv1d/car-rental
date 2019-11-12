package com.davv1d.repository;

import com.davv1d.domain.user.login.UserLogin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserLoginRepositoryTestSuite {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Test
    public void shouldSave() {
        //Given
        LocalDateTime loginDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        UserLogin userLogin = new UserLogin("test username", loginDate);
        //When
        userLoginRepository.save(userLogin);
        //Then
        Optional<UserLogin> optionalUserLogin = userLoginRepository.findById(userLogin.getId());
        assertTrue(optionalUserLogin.isPresent());
        assertEquals("test username", optionalUserLogin.get().getUsername());
        assertEquals(loginDate, optionalUserLogin.get().getLoginDate());
        //Clean up
        userLoginRepository.deleteById(userLogin.getId());
    }

    @Test
    public void shouldFindByUsername() {
        //Given
        LocalDateTime loginDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        UserLogin userLogin1 = new UserLogin("test username", loginDate);
        UserLogin userLogin2 = new UserLogin("test username", loginDate.plusDays(2));
        userLoginRepository.save(userLogin1);
        userLoginRepository.save(userLogin2);
        //When
        List<UserLogin> userLogins = userLoginRepository.findByUsername("test username");
        //Then
        assertEquals(2, userLogins.size());
        //Clean up
        for (UserLogin userLogin: userLogins) {
            userLoginRepository.deleteById(userLogin.getId());
        }
    }
}