package com.davv1d.mapper.login;

import com.davv1d.domain.user.User;
import com.davv1d.domain.user.login.SingUpDto;
import com.davv1d.domain.user.role.Role;
import com.davv1d.domain.user.role.RoleFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SingUpMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User mapToUser(final SingUpDto singUpDto) {
        Role role = RoleFactory.createRole(singUpDto.getRole());
        String password = passwordEncoder.encode(singUpDto.getPassword());
        return new User(singUpDto.getUsername(), password, singUpDto.getEmail(), role);
    }
}
