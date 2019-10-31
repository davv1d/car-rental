package com.davv1d.mapper;

import com.davv1d.domain.User;
import com.davv1d.domain.UserDto;
import com.davv1d.domain.constant.Role;
import com.davv1d.domain.constant.RoleFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User mapToUser(final UserDto userDto) {
        Role role = RoleFactory.createRole(userDto.getRole());
        String password = passwordEncoder.encode(userDto.getPassword());
        return new User(userDto.getUsername(), password, userDto.getEmail(), role);
    }
}
