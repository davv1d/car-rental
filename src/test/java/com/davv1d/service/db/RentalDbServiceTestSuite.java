package com.davv1d.service.db;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.Car;
import com.davv1d.domain.car.Model;
import com.davv1d.domain.rental.Rental;
import com.davv1d.domain.user.User;
import com.davv1d.domain.user.role.Role;
import com.davv1d.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.davv1d.domain.user.role.Role.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RentalDbServiceTestSuite {
    @Autowired
    private RentalDbService rentalDbService;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Test
    public void shouldDeleteRental() {
        //Given
        User user = new User("test name", "test password", "email@test.com", ROLE_CLIENT.name());
        User saveUser = userRepository.save(user);
        Brand brand = new Brand("test brand");
        Brand savedBrand = brandRepository.save(brand);
        Model model = new Model("test model", brand);
        Model savedModel = modelRepository.save(model);
        Car car1 = new Car("testvin1", savedBrand, savedModel, true);
        carRepository.save(car1);
        LocalDateTime dateOfRent = LocalDateTime.now().plusDays(1);
        LocalDateTime dateOfReturn = dateOfRent.plusDays(2);
        Rental rental = new Rental(saveUser, car1, dateOfRent, dateOfReturn);
        Long rentalId = rentalRepository.save(rental).getId();
        Optional<Rental> beforeDelete = rentalRepository.findById(rentalId);
        //When
        rentalDbService.deleteById(rental.getId());
        //Then
        try {
            Optional<Rental> afterDelete = rentalRepository.findById(rentalId);
            assertTrue(beforeDelete.isPresent());
            assertFalse(afterDelete.isPresent());
        } finally {
            //Clean up
            userRepository.deleteByUsername(user.getUsername());
            brandRepository.deleteByName(brand.getName());
        }
    }

    @Test
    public void shouldSaveRental() {
        //Given
        User user = new User("test name", "test password", "email@test.com", ROLE_CLIENT.name());
        User saveUser = userRepository.save(user);
        Brand brand = new Brand("test brand");
        Brand savedBrand = brandRepository.save(brand);
        Model model = new Model("test model", brand);
        Model savedModel = modelRepository.save(model);
        Car car1 = new Car("testvin1", savedBrand, savedModel, true);
        carRepository.save(car1);
        LocalDateTime dateOfRent = LocalDateTime.now().plusDays(1);
        LocalDateTime dateOfReturn = dateOfRent.plusDays(2);
        Rental rental = new Rental(saveUser, car1, dateOfRent, dateOfReturn);
        //When
        Rental savedRental = rentalDbService.save(rental);
        //Then
        try {
            assertNotNull(savedRental.getId());
            Optional<Rental> optionalRental = rentalRepository.findById(savedRental.getId());
            assertTrue(optionalRental.isPresent());
        } finally {
            //Clean up
            rentalRepository.deleteById(savedRental.getId());
            userRepository.deleteByUsername(user.getUsername());
            brandRepository.deleteByName(brand.getName());
        }
    }

    @Test
    public void shouldNotSaveRentalUsersDoesNotExist() {
        //Given
        User user = new User("test name", "test password", "email@test.com", ROLE_CLIENT.name());
        Brand brand = new Brand("test brand");
        Brand savedBrand = brandRepository.save(brand);
        Model model = new Model("test model", brand);
        Model savedModel = modelRepository.save(model);
        Car car1 = new Car("testvin1", savedBrand, savedModel, true);
        carRepository.save(car1);
        LocalDateTime dateOfRent = LocalDateTime.now().plusDays(1);
        LocalDateTime dateOfReturn = dateOfRent.plusDays(2);
        Rental rental = new Rental(user, car1, dateOfRent, dateOfReturn);
        //When
        Rental savedRental = rentalDbService.save(rental);
        //Then
        try {
            assertNull(savedRental.getId());
        } finally {
            //Clean up
            brandRepository.deleteByName(brand.getName());
        }
    }
}