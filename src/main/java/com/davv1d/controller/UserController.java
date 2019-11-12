package com.davv1d.controller;

import com.davv1d.domain.user.EmailUpdater;
import com.davv1d.domain.user.User;
import com.davv1d.domain.user.UserDto;
import com.davv1d.errors.UsernameNotFoundException;
import com.davv1d.mapper.user.UserMapper;
import com.davv1d.service.EmptyValuesClassCreator;
import com.davv1d.service.db.UserDbDetailsService;
import com.davv1d.service.validate.ExistenceChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserDbDetailsService userDbDetailsService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = "/users")
    public List<UserDto> getAll() {
        return userMapper.mapToUserDtoList(userDbDetailsService.getAll());
    }

    @GetMapping(value = "/users", params = "username")
    public ResponseEntity<?> getUserByUsername(@RequestParam String username) {
       return ExistenceChecker.ifExists(username, userDbDetailsService::getUserByUsername)
                .map(userMapper::mapToUserDto)
                .effect(ResponseEntity::ok, errorMessage -> {
                    LOGGER.error(errorMessage);
                    return ResponseEntity.badRequest().body(errorMessage);
                });
    }

    @GetMapping(value = "/loggedUser")
    public UserDto getLoggedUser(Principal principal) {
        return userDbDetailsService.getUserByUsername(principal.getName())
                .map(userMapper::mapToUserDto)
                .orElseGet(EmptyValuesClassCreator::emptyUserDto);
    }

    @DeleteMapping(value = "/users", params = "username")
    public void deleteUser(@RequestParam String username) {
        userDbDetailsService.deleteByUsername(username);
    }

    @PutMapping("/users/email")
    public void changeUserEmail(@RequestBody String email, Principal principal) {
        EmailUpdater emailUpdater = new EmailUpdater(principal.getName(), email);
        userDbDetailsService.changeUserEmail(emailUpdater);
    }
}
