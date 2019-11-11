package com.davv1d.service.validate;

import com.davv1d.domain.user.User;
import com.davv1d.functional.Result;
import com.davv1d.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
    public static final String ERROR_EMAIL_EXIST = "USER WITH THIS EMAIL ADDRESS EXISTS";
    public static final String ERROR_NAME_EXIST = "USER WITH THIS NAME EXISTS";
    @Autowired
    private UserRepository userRepository;

    public Result<User> saveUserValidate(final User user) {
        if (!doesUsernameExistInDatabase(user.getUsername())) {
            if (!doesEmailExistInDatabases(user.getEmail())) {
                return Result.success(user);
            }
            return Result.failure(ERROR_EMAIL_EXIST);
        }
        return Result.failure(ERROR_NAME_EXIST);
    }

    private boolean doesEmailExistInDatabases(String email) {
        return userRepository.existsByEmail(email);
    }

    private boolean doesUsernameExistInDatabase(String username) {
        return userRepository.existsByUsername(username);
    }
}

