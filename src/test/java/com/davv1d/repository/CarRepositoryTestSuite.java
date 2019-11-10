package com.davv1d.repository;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.Car;
import com.davv1d.domain.car.Model;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    ModelRepository modelRepository;

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