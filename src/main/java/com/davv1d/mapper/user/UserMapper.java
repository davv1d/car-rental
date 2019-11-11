package com.davv1d.mapper.user;

import com.davv1d.domain.user.User;
import com.davv1d.domain.user.UserDto;
import com.davv1d.domain.user.login.SingUpDto;
import com.davv1d.domain.user.role.Role;
import com.davv1d.domain.user.role.RoleFactory;
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

    public User mapToUser(final SingUpDto singUpDto) {
        String password = passwordEncoder.encode(singUpDto.getPassword());
        return new User(singUpDto.getUsername(), password, singUpDto.getEmail(), singUpDto.getRole());
    }
}
