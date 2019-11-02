package com.davv1d.service;

import com.davv1d.domain.User;
import com.davv1d.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDbService {
    @Autowired
    private UserRepository userRepository;

    public String saveUser(User user) {
        return userRepository.save(user).getUsername();
    }

    public Optional<User> findUserByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public boolean existsByUsername(final String username) {
        return userRepository.existsByUsername(username);
    }
}
