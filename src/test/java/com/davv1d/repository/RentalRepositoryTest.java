package com.davv1d.repository;

import com.davv1d.domain.car.Car;
import com.davv1d.domain.rental.Rental;
import com.davv1d.domain.user.User;
import com.davv1d.service.db.UserDbService;
import com.davv1d.service.db.CarDbService;
import com.davv1d.service.db.RentalDbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RentalRepositoryTest {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserDbService userDbService;

    @Autowired
    private CarDbService carDbService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalDbService rentalDbService;

    @Test
    public void test() {
//        User user = new User("username", "password", "email", Role.ROLE_CLIENT);
//        userService.saveUser(user);
//        User user = userRepository.findByUsername("john vaadin").get();
//        Brand volvo = new Brand("Volvo");
//        Model v50 = new Model("v60", volvo);
//        Car car1 = new Car("VIN TEST 7", volvo, v50, false);
//        carDbService.saveCarIfItDoesNotExist(car1);
//        Car car1 = carRepository.findById(29L).get();
//        Date date = new Date();
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//        Car vin_vaadin = carDbService.getByVinNumber("VIN VADIN").get();
//        LocalDateTime localDateTime = LocalDateTime.now();
//        System.out.println(localDateTime);
//        Rental rental = new Rental(user, vin_vaadin, LocalDateTime.now(), LocalDateTime.now().plusHours(10));
//        rentalRepository.save(rental);
//        LocalDateTime dateOfRent = LocalDateTime.now().minusDays(5);
//        LocalDateTime dateOfReturn  = LocalDateTime.now().plusDays(1);
//        List<Car> cars = carDbService.fetchAvailabilityCars(dateOfRent, dateOfReturn);
//        assertEquals(7, cars.size());
//        System.out.println(cars);
//        Optional<Rental> rentals = rentalRepository.fetchRentalsByCarVinNumber("VIN TEST 1");
//        assertTrue(rentals.isPresent());
//        List<Rental> username = rentalRepository.fetchRentalsByUsername("username");
//        System.out.println(username);
//        assertNotEquals(0, username.size());
//        rentalRepository.deleteById(32L);
//        rentalDbService.deleteById(32L);
    }
}