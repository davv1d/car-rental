package com.davv1d.service.validate;

import com.davv1d.domain.User;
import com.davv1d.functional.Result;
import com.davv1d.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
    @Autowired
    private UserRepository userRepository;

    public Result<User> saveUserValidate(final User user) {
        if (!doesUsernameExistInDatabase(user.getUsername())) {
            if (!doesEmailExistInDatabases(user.getEmail())) {
                return Result.success(user);
            }
            return Result.failure("User in this email is exist");
        }
        return Result.failure("User in this name is exist");
    }

    private boolean doesEmailExistInDatabases(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean doesUsernameExistInDatabase(String username) {
        return userRepository.existsByUsername(username);
    }
}

