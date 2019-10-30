package com.davv1d.mapper;

import com.davv1d.domain.User;
import com.davv1d.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto mapToUserDto(final User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getRole());
    }

    public User mapToUser(final UserDto userDto) {
        return new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()), userDto.getEmail(), userDto.getRole());
    }
}
