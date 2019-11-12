package com.davv1d;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.Car;
import com.davv1d.domain.car.Model;
import com.davv1d.domain.user.User;
import com.davv1d.service.db.CarDetailsService;
import com.davv1d.service.db.UserDbDetailsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StartData {
    @Autowired
    private UserDbDetailsService userDbDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CarDetailsService carDetailsService;

    @Test
    public void createUsers() {
        User admin = new User("admin", passwordEncoder.encode("admin"), "admin@email.com", "admin");
        User client = new User("client", passwordEncoder.encode("client"), "client@email.com", "client");
        Optional<User> optionalAdmin = userDbDetailsService.getUserByUsername(admin.getUsername());
        Optional<User> optionalClient = userDbDetailsService.getUserByUsername(client.getUsername());
        if (!optionalAdmin.isPresent()) {
            userDbDetailsService.saveUser(admin);
        }
        if (!optionalClient.isPresent()) {
            userDbDetailsService.saveUser(client);
        }

        Brand brand = new Brand("start brand");
        Car car = new Car("VIN123", brand, new Model("start model", brand), true);
        Car car1 = carDetailsService.saveCarIfItDoesNotExist(car);
    }
}
