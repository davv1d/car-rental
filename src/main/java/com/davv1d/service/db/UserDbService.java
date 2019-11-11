package com.davv1d.service.db;

import com.davv1d.domain.user.EmailUpdater;
import com.davv1d.domain.user.User;
import com.davv1d.domain.user.role.RoleFactory;
import com.davv1d.functional.Result;
import com.davv1d.repository.UserRepository;
import com.davv1d.service.validate.RoleValidator;
import com.davv1d.service.validate.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDbService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    public Result<User> saveUser(final User user) {
        return RoleValidator.test(user.getRole())
                .map(RoleFactory::createRole)
                .map(role -> new User(user.getUsername(), user.getPassword(), user.getEmail(), role))
                .flatMap(userValidator::saveUserValidate)
                .flatMap(this::save);
    }

    private Result<User> save(User user) {
        try {
            User save = userRepository.save(user);
            return Result.success(save);
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }

    public Optional<User> getUserByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAll() {
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
}
