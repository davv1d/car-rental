package com.davv1d.service.db;

import com.davv1d.domain.user.EmailUpdater;
import com.davv1d.domain.user.User;
import com.davv1d.domain.user.role.Role;
import com.davv1d.functional.Result;
import com.davv1d.repository.UserRepository;
import com.davv1d.service.validate.RoleValidator;
import com.davv1d.service.validate.UserValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDbDetailsServiceTestSuite {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDbDetailsService userDbDetailsService;

    @Test
    public void shouldSaveUser() {
        //Given
        User user = new User("test name", "test password", "test@email.com", "client");
        //When
        Result<User> userResult = userDbDetailsService.saveUser(user);
        //Then
        assertEquals(Result.Success.class, userResult.getClass());
        //Clean up
        userRepository.deleteByUsername(user.getUsername());
    }

    @Test
    public void shouldNotSaveUserEmailExists() {
        //Given
        User unsavedUser = new User("test name", "test password", "test@email.com", "client");
        User userToSave = new User("test name 2", "test password", "test@email.com", Role.ROLE_CLIENT.name());
        userRepository.save(userToSave);
        //When
        Result<User> userResult = userDbDetailsService.saveUser(unsavedUser);
        //Then
        Optional<User> optionalUser = userRepository.findByUsername(unsavedUser.getUsername());
        assertFalse(optionalUser.isPresent());
        assertEquals(UserValidator.ERROR_EMAIL_EXIST, userResult.getErrorMessage());
        //Clean up
        userRepository.deleteByUsername(userToSave.getUsername());
    }

    @Test
    public void shouldNotSaveUserNameExists() {
        //Given
        User unsavedUser = new User("test name", "test password", "test2@email.com", "client");
        User userToSave = new User("test name", "test password", "test@email.com", Role.ROLE_CLIENT.name());
        userRepository.save(userToSave);
        //When
        Result<User> userResult = userDbDetailsService.saveUser(unsavedUser);
        //Then
        Optional<User> optionalUser = userRepository.findByEmail("test2@email.com");
        assertFalse(optionalUser.isPresent());
        assertEquals(UserValidator.ERROR_NAME_EXIST, userResult.getErrorMessage());
        //Clean up
        userRepository.deleteByUsername(userToSave.getUsername());
    }

    @Test
    public void shouldNotSaveUserIncorrectRole() {
        //Given
        User user = new User("test name", "test password", "test@email.com", "test role");
        //When
        Result<User> userResult = userDbDetailsService.saveUser(user);
        //Then
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        assertFalse(optionalUser.isPresent());
        assertEquals(RoleValidator.ERROR, userResult.getErrorMessage());
    }


    @Test
    public void shouldChangeUserEmail() {
        //Given
        User user = new User("test name", "test password", "test@email.com", Role.ROLE_CLIENT.name());
        userRepository.save(user);
        EmailUpdater emailUpdater = new EmailUpdater("test name", "testupdate@email.com");
        Optional<User> userBeforeUpdate = userRepository.findById(user.getId());
        //When
        userDbDetailsService.changeUserEmail(emailUpdater);
        //Then
        Optional<User> userAfterUpdate = userRepository.findById(user.getId());
        assertTrue(userBeforeUpdate.isPresent());
        assertEquals("test@email.com", userBeforeUpdate.get().getEmail());
        assertTrue(userAfterUpdate.isPresent());
        assertEquals("testupdate@email.com", userAfterUpdate.get().getEmail());
        //Clean up
        userRepository.deleteByUsername(user.getUsername());
    }

    @Test
    public void shouldNotChangeUserEmailExists() {
        //Given
        User user1 = new User("test name 1", "test password", "test1@email.com", Role.ROLE_CLIENT.name());
        userRepository.save(user1);
        User user2 = new User("test name 2", "test password", "test2@email.com", Role.ROLE_CLIENT.name());
        userRepository.save(user2);
        EmailUpdater emailUpdater = new EmailUpdater("test name 2", "test1@email.com");
        Optional<User> userBeforeUpdate = userRepository.findById(user2.getId());
        //When
        Result<User> result = userDbDetailsService.changeUserEmail(emailUpdater);
        //Then
        Optional<User> userAfterUpdate = userRepository.findById(user2.getId());
        assertTrue(userBeforeUpdate.isPresent());
        assertEquals("test2@email.com", userBeforeUpdate.get().getEmail());
        assertTrue(userAfterUpdate.isPresent());
        assertEquals("test2@email.com", userAfterUpdate.get().getEmail());
        assertEquals(Result.Failure.class, result.getClass());
        //Clean up
        userRepository.deleteByUsername(user1.getUsername());
        userRepository.deleteByUsername(user2.getUsername());
    }

    @Test
    public void shouldNotChangeUserEmailUserDoesNotExist() {
        //Given
        User user2 = new User("test name 2", "test password", "test2@email.com", Role.ROLE_CLIENT.name());
        userRepository.save(user2);
        EmailUpdater emailUpdater = new EmailUpdater("test name 3", "test1@email.com");
        Optional<User> userBeforeUpdate = userRepository.findById(user2.getId());
        //When
        Result<User> result = userDbDetailsService.changeUserEmail(emailUpdater);
        //Then
        Optional<User> userAfterUpdate = userRepository.findById(user2.getId());
        assertTrue(userBeforeUpdate.isPresent());
        assertEquals("test2@email.com", userBeforeUpdate.get().getEmail());
        assertTrue(userAfterUpdate.isPresent());
        assertEquals("test2@email.com", userAfterUpdate.get().getEmail());
        assertEquals(Result.Failure.class, result.getClass());
        System.out.println(result.getErrorMessage());
        //Clean up
        userRepository.deleteByUsername(user2.getUsername());
    }
}