package com.davv1d.repository;

import com.davv1d.domain.user.login.UserLogin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserLoginRepository extends CrudRepository<UserLogin, Long> {
    @Override
    UserLogin save(UserLogin userLogin);

    List<UserLogin> findByUsername(String username);
}
