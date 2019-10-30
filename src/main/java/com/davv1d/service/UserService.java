package com.davv1d.service;

import com.davv1d.domain.User;
import com.davv1d.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String saveUser(User user) {
        return userRepository.save(user).getUsername();
    }
}
