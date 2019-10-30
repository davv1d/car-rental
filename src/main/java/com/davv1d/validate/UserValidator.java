package com.davv1d.validate;

import com.davv1d.domain.User;
import com.davv1d.functional.Result;
import com.davv1d.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {
    @Autowired
    private UserRepository userRepository;

    public Result<User> saveUserValidate(final User user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            if (!userRepository.existsByEmail(user.getEmail())) {
                return Result.success(user);
            }
            return Result.failure("User in this email is exist");
        }
        return Result.failure("User in this name is exist");
    }
}

