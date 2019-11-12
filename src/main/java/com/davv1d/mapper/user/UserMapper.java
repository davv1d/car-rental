package com.davv1d.mapper.user;

import com.davv1d.domain.user.User;
import com.davv1d.domain.user.UserDto;
import com.davv1d.domain.user.login.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto mapToUserDto(final User user) {
        return new UserDto(user.getUsername(), user.getEmail(), user.getRole());
    }

    public List<UserDto> mapToUserDtoList(final List<User> users) {
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }

    public User mapToUser(final SignUpDto signUpDto) {
        String password = passwordEncoder.encode(signUpDto.getPassword());
        return new User(signUpDto.getUsername(), password, signUpDto.getEmail(), signUpDto.getRole());
    }
}
