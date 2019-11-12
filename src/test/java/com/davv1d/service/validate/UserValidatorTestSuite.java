package com.davv1d.service.validate;

import com.davv1d.domain.user.User;
import com.davv1d.functional.Result;
import com.davv1d.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.davv1d.domain.user.role.Role.ROLE_CLIENT;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserValidatorTestSuite {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    @Test
    public void testSaveUserValidateSameEmail() {
        //Given
        User user1 = new User("test username", "test", "test@test.com", ROLE_CLIENT.name());
        userRepository.save(user1);
        User user2 = new User("test username 2", "test", "test@test.com", ROLE_CLIENT.name());
        //When
        Result<User> userResult = userValidator.saveUserValidate(user2);
        //Then
        assertEquals(Result.Failure.class, userResult.getClass());
        //Clean up
        userRepository.deleteById(user1.getId());
    }

    @Test
    public void testSaveUserValidateSameUsername() {
        //Given
        User user1 = new User("test username", "test", "test@test.com", ROLE_CLIENT.name());
        userRepository.save(user1);
        User user2 = new User("test username", "test", "test@test2.com", ROLE_CLIENT.name());
        //When
        Result<User> userResult = userValidator.saveUserValidate(user2);
        //Then
        assertEquals(Result.Failure.class, userResult.getClass());
        //Clean up
        userRepository.deleteById(user1.getId());
    }

    @Test
    public void testSaveUserValidateSuccessDifferentUsers() {
        //Given
        User user1 = new User("test username", "test", "test@test.com", ROLE_CLIENT.name());
        userRepository.save(user1);
        User user2 = new User("test username 1", "test", "test@test1.com", ROLE_CLIENT.name());
        //When
        Result<User> userResult = userValidator.saveUserValidate(user2);
        //Then
        assertEquals(Result.Success.class, userResult.getClass());
        //Clean up
        userRepository.deleteById(user1.getId());
    }
}