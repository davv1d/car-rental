package com.davv1d.mapper.user;

import com.davv1d.domain.user.User;
import com.davv1d.domain.user.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDto mapToUserDto(final User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getRole().toString());
    }

    public List<UserDto> mapToUserDtoList(final List<User> users) {
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
}
