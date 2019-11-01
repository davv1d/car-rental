package com.davv1d.repository;

import com.davv1d.domain.User;
import com.davv1d.domain.constant.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTestSuite {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveUser() {
        //Given
        User user = new User("test name", "test password", "email@test.com", Role.ROLE_CLIENT);
        //When
        userRepository.save(user);
        //Then
        assertNotNull(user.getId());
        //Clean up
        userRepository.deleteById(user.getId());
    }

    @Test
    public void shouldFetchAllUsers() {
        //Given
        User user = new User("test user", "test user", "email@user.com", Role.ROLE_CLIENT);
        User admin = new User("test admin", "test admin", "email@admin.com", Role.ROLE_ADMIN);
        userRepository.save(user);
        userRepository.save(admin);
        //When
        List<User> users = userRepository.findAll();
        //Then
        assertEquals(2, users.size());
        //Clean Up
        userRepository.deleteById(user.getId());
        userRepository.deleteById(admin.getId());
    }

    @Test
    public void shouldFetchUserById() {
        //Given
        User user = new User("test user", "test user", "email@user.com", Role.ROLE_CLIENT);
        userRepository.save(user);
        //When
        Optional<User> optionalPatient = userRepository.findById(user.getId());
        //Then
        assertTrue(optionalPatient.isPresent());
        //Clean Up
        userRepository.deleteById(user.getId());
    }

    @Test
    public void shouldFetchUserByUsername() {
        //Given
        User user = new User("test user", "test user", "email@user.com", Role.ROLE_CLIENT);
        userRepository.save(user);
        //When
        Optional<User> optionalPatient = userRepository.findByUsername(user.getUsername());
        //Then
        assertTrue(optionalPatient.isPresent());
        //Clean Up
        userRepository.deleteById(user.getId());
    }

    @Test
    public void shouldExistsByUsername() {
        //Given
        User user = new User("test user", "test user", "email@user.com", Role.ROLE_CLIENT);
        userRepository.save(user);
        //When
        boolean exist = userRepository.existsByUsername(user.getUsername());
        //Then
        assertTrue(exist);
        //Clean Up
        userRepository.deleteById(user.getId());
    }

    @Test
    public void shouldExistsByEmail() {
        //Given
        User user = new User("test user", "test user", "email@user.com", Role.ROLE_CLIENT);
        userRepository.save(user);
        //When
        boolean exist = userRepository.existsByEmail(user.getEmail());
        //Then
        assertTrue(exist);
        //Clean Up
        userRepository.deleteById(user.getId());
    }
}