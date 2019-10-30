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
        User patient = new User("test name", "test password", "email@test.com", Role.ROLE_PATIENT);
        //When
        userRepository.save(patient);
        //Then
        assertNotNull(patient.getId());
        //Clean up
        userRepository.deleteById(patient.getId());
    }

    @Test
    public void shouldFetchAllUsers() {
        //Given
        User patient = new User("test patient", "test patient", "email@patient.com", Role.ROLE_PATIENT);
        User admin = new User("test admin", "test admin", "email@admin.com", Role.ROLE_ADMIN);
        userRepository.save(patient);
        userRepository.save(admin);
        //When
        List<User> users = userRepository.findAll();
        //Then
        assertEquals(2, users.size());
        //Clean Up
        userRepository.deleteById(patient.getId());
        userRepository.deleteById(admin.getId());
    }

    @Test
    public void shouldFetchUserById() {
        //Given
        User patient = new User("test patient", "test patient", "email@patient.com", Role.ROLE_PATIENT);
        userRepository.save(patient);
        //When
        Optional<User> optionalPatient = userRepository.findById(patient.getId());
        //Then
        assertTrue(optionalPatient.isPresent());
        //Clean Up
        userRepository.deleteById(patient.getId());
    }

    @Test
    public void shouldFetchUserByUsername() {
        //Given
        User patient = new User("test patient", "test patient", "email@patient.com", Role.ROLE_PATIENT);
        userRepository.save(patient);
        //When
        Optional<User> optionalPatient = userRepository.findByUsername(patient.getUsername());
        //Then
        assertTrue(optionalPatient.isPresent());
        //Clean Up
        userRepository.deleteById(patient.getId());
    }

    @Test
    public void shouldExistsByUsername() {
        //Given
        User patient = new User("test patient", "test patient", "email@patient.com", Role.ROLE_PATIENT);
        userRepository.save(patient);
        //When
        boolean exist = userRepository.existsByUsername(patient.getUsername());
        //Then
        assertTrue(exist);
        //Clean Up
        userRepository.deleteById(patient.getId());
    }

    @Test
    public void shouldExistsByEmail() {
        //Given
        User patient = new User("test patient", "test patient", "email@patient.com", Role.ROLE_PATIENT);
        userRepository.save(patient);
        //When
        boolean exist = userRepository.existsByEmail(patient.getEmail());
        //Then
        assertTrue(exist);
        //Clean Up
        userRepository.deleteById(patient.getId());
    }
}