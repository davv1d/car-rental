package com.davv1d.service.db;

import com.davv1d.domain.user.EmailUpdater;
import com.davv1d.domain.user.User;
import com.davv1d.domain.user.role.RoleFactory;
import com.davv1d.functional.Result;
import com.davv1d.repository.UserRepository;
import com.davv1d.service.validate.ExistenceChecker;
import com.davv1d.service.validate.RoleValidator;
import com.davv1d.service.validate.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDbDetailsService {
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

    public Result<User> changeUserEmail(final EmailUpdater emailUpdater) {
        return ExistenceChecker.ifItDoesNotExist(emailUpdater.getEmail(), userRepository::findByEmail)
                .flatMap(s -> ExistenceChecker.ifExists(emailUpdater.getUsername(), userRepository::findByUsername))
                .map(user -> new User(user.getId(), user.getUsername(), user.getPassword(), emailUpdater.getEmail(), user.getRole(), user.getRentals()))
                .flatMap(this::save);
    }
}
