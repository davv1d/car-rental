package com.davv1d.repository;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.Car;
import com.davv1d.domain.car.Model;
import com.davv1d.domain.rental.Rental;
import com.davv1d.domain.user.User;
import com.davv1d.domain.user.role.Role;
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
public class CarRepositoryTestSuite {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldFetchAvailabilityCars() {
        //Given
        User user = new User("test name", "test password", "email@test.com", Role.ROLE_CLIENT);
        User saveUser = userRepository.save(user);
        Brand brand = new Brand("test audi");
        Brand savedBrand = brandRepository.save(brand);
        Model model = new Model("test A6", brand);
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
        List<Car> cars = carRepository.fetchAvailabilityCars(dateOfRent.plusDays(2), dateOfReturn.plusDays(8));
        //Then
        try {
            assertEquals(1, cars.size());
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

    @Test
    public void shouldGetAllCars() {
        //Given
        Brand brand = new Brand("test audi");
        Brand savedBrand = brandRepository.save(brand);
        Model model = new Model("test A6", brand);
        Model savedModel = modelRepository.save(model);
        Car car1 = new Car("testvin1", savedBrand, savedModel, true);
        Car car2 = new Car("testvin2", savedBrand, savedModel, false);
        carRepository.save(car1);
        carRepository.save(car2);
        //When
        List<Car> cars = carRepository.findAll();
        //Then
        try {
            assertEquals(2, cars.size());
        } finally {
            //Clean up
            brandRepository.deleteByName(savedBrand.getName());
        }
    }

    @Test
    public void shouldSaveCar() {
        //Given
        Brand brand = new Brand("test audi");
        Brand savedBrand = brandRepository.save(brand);
        Model model = new Model("test A6", brand);
        Model savedModel = modelRepository.save(model);
        Car car1 = new Car("testvin1", savedBrand, savedModel, true);
        //When
        carRepository.save(car1);
        //Then
        Optional<Car> carById = carRepository.findById(car1.getId());
        try {
            assertTrue(carById.isPresent());
            assertEquals("TEST AUDI", carById.get().getBrand().getName());
            assertEquals("TEST A6", carById.get().getModel().getName());
            assertEquals("TESTVIN1", carById.get().getVinNumber());
            assertEquals("TEST AUDI", carById.get().getBrand().getName());
            assertTrue(carById.get().isAvailability());
        } finally {
            //Clean up
            brandRepository.deleteByName(savedBrand.getName());
        }
    }

    @Test
    public void shouldGetCountNumber() {
        //Given
        Brand brand = new Brand("test audi");
        Brand savedBrand = brandRepository.save(brand);
        Model model = new Model("test A6", brand);
        Model savedModel = modelRepository.save(model);
        Car car1 = new Car("testvin1", savedBrand, savedModel, true);
        Car car2 = new Car("testvin2", savedBrand, savedModel, false);
        carRepository.save(car1);
        carRepository.save(car2);
        //When
        long numberOfCars = carRepository.count();
        //Then
        try {
            assertEquals(2, numberOfCars);
        } finally {
            //Clean up
            brandRepository.deleteByName(savedBrand.getName());
        }
    }

    @Test
    public void shouldGetCountByAvailability() {
        //Given
        Brand brand = new Brand("test audi");
        Brand savedBrand = brandRepository.save(brand);
        Model model = new Model("test A6", brand);
        Model savedModel = modelRepository.save(model);
        Car car1 = new Car("testvin1", savedBrand, savedModel, true);
        Car car2 = new Car("testvin2", savedBrand, savedModel, false);
        carRepository.save(car1);
        carRepository.save(car2);
        //When
        long numberOfAvailabilityCars = carRepository.countByAvailability(true);
        long numberOfNotAvailabilityCars = carRepository.countByAvailability(false);
        //Then
        try {
            assertEquals(1, numberOfAvailabilityCars);
            assertEquals(1, numberOfNotAvailabilityCars);
        } finally {
            //Clean up
            brandRepository.deleteByName(savedBrand.getName());
        }
    }

    @Test
    public void shouldFindByVinNumber() {
        //Given
        Brand brand = new Brand("test audi");
        Brand savedBrand = brandRepository.save(brand);
        Model model = new Model("test A6", brand);
        Model savedModel = modelRepository.save(model);
        Car car1 = new Car("testvin1", savedBrand, savedModel, true);
        Car car2 = new Car("testvin2", savedBrand, savedModel, false);
        carRepository.save(car1);
        carRepository.save(car2);
        //When
        Optional<Car> carOptional = carRepository.findByVinNumber("TESTVIN2");
        //Then
        try {
            assertTrue(carOptional.isPresent());
            assertEquals("TESTVIN2", carOptional.get().getVinNumber());
        } finally {
            //Clean up
            brandRepository.deleteByName(savedBrand.getName());
        }
    }
}