package com.davv1d.repository;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.Car;
import com.davv1d.domain.car.Model;
import com.davv1d.domain.rental.Rental;
import com.davv1d.domain.user.User;
import com.davv1d.domain.user.role.Role;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RentalRepositoryTest {
    private User user = new User("test name", "test password", "email@test.com", Role.ROLE_CLIENT);
    private Brand brand = new Brand("test audi");
    private Model model = new Model("test A6", brand);
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldGetAllRentals() {
        //Given
        User saveUser = userRepository.save(user);
        Brand savedBrand = brandRepository.save(brand);
        Model savedModel = modelRepository.save(model);
        Car car1 = new Car("testvin1", savedBrand, savedModel, true);
        Car car2 = new Car("testvin2", savedBrand, savedModel, true);
        Car savedCar1 = carRepository.save(car1);
        carRepository.save(car2);
        LocalDateTime dateOfRent = LocalDateTime.now().plusDays(1);
        LocalDateTime dateOfReturn = dateOfRent.plusDays(3);
        LocalDateTime dateOfRent2 = LocalDateTime.now().plusDays(10);
        LocalDateTime dateOfReturn2 = dateOfRent.plusDays(13);
        Rental rental1 = new Rental(saveUser, savedCar1, dateOfRent, dateOfReturn);
        Rental rental2 = new Rental(saveUser, savedCar1, dateOfRent2, dateOfReturn2);
        rentalRepository.save(rental1);
        rentalRepository.save(rental2);
        //When
        List<Rental> rentals = rentalRepository.findAll();
        //Then
        try {
            assertEquals(2, rentals.size());
        } finally {
            //Clean up
            Optional<Rental> savedRental = rentalRepository.findById(rental1.getId());
            savedRental.ifPresent(this::deleteRental);
            Optional<Rental> savedRental2 = rentalRepository.findById(rental2.getId());
            savedRental2.ifPresent(this::deleteRental);
            userRepository.deleteByUsername(saveUser.getUsername());
            brandRepository.deleteByName(savedBrand.getName());
        }
    }

    private void deleteRental(Rental rental) {
        Rental updatedRental = new Rental(rental.getId(), null, null, rental.getDateOfRent(), rental.getDateOfReturn());
        rentalRepository.save(updatedRental);
        rentalRepository.deleteById(updatedRental.getId());
    }
}