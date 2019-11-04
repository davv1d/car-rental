package com.davv1d.service.db;

import com.davv1d.domain.user.login.UserLogin;
import com.davv1d.repository.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginDbService {
    @Autowired
    private UserLoginRepository userLoginRepository;

    public void save(UserLogin userLogin) {
        userLoginRepository.save(userLogin);
    }
}
