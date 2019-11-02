package com.davv1d.service;

import com.davv1d.domain.user.EmailUpdater;
import com.davv1d.domain.user.User;
import com.davv1d.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDbService {
    @Autowired
    private UserRepository userRepository;

    public String saveUser(final User user) {
        return userRepository.save(user).getUsername();
    }

    public Optional<User> findUserByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> fetchAll() {
        return userRepository.findAll();
    }

    public void deleteByUsername(final String username) {
        userRepository.deleteByUsername(username);
    }

    public void changeUserEmail(final EmailUpdater emailUpdater) {
        if (!userRepository.existsByEmail(emailUpdater.getEmail()))
        userRepository.findByUsername(emailUpdater.getUsername())
                .ifPresent(foundUser -> {
                    User updatedUser = new User(
                            foundUser.getId(),
                            foundUser.getUsername(),
                            foundUser.getPassword(),
                            emailUpdater.getEmail(),
                            foundUser.getRole(),
                            foundUser.getRentals());
                    userRepository.save(updatedUser);
                });
    }

    public boolean existsByUsername(final String username) {
        return userRepository.existsByUsername(username);
    }
}
