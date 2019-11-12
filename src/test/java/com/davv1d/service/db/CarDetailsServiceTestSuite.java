package com.davv1d.service.db;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.Car;
import com.davv1d.domain.car.Model;
import com.davv1d.functional.Result;
import com.davv1d.repository.BrandRepository;
import com.davv1d.repository.CarRepository;
import com.davv1d.repository.ModelRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarDetailsServiceTestSuite {
    @Autowired
    private CarDetailsService carDetailsService;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private CarRepository carRepository;

    @Test
    public void testDoesNotSaveCar() {
        //Given
        Brand brand = new Brand("test audi");
        Brand savedBrand = brandRepository.save(brand);
        Model model = new Model("test A6", brand);
        Model savedModel = modelRepository.save(model);
        Car car1 = new Car("testvin1", savedBrand, savedModel, true);
        Car car2 = new Car("testvin1", savedBrand, savedModel, false);
        carRepository.save(car1);
        //When
        Car car = carDetailsService.saveCarIfItDoesNotExist(car2);
        //Then
        assertTrue(car.isAvailability());
        assertEquals("TESTVIN1", car.getVinNumber());
        //Clean up
        brandRepository.deleteById(brand.getId());
    }

    @Test
    public void testSaveCar() {
        //Given
        Brand brand = new Brand("test audi");
        Brand savedBrand = brandRepository.save(brand);
        Model model = new Model("test A6", brand);
        Model savedModel = modelRepository.save(model);
        Car car1 = new Car("testvin1", savedBrand, savedModel, true);
        Optional<Car> beforeSave = carRepository.findByVinNumber("testvin1");
        //When
        carDetailsService.saveCarIfItDoesNotExist(car1);
        //Then
        Optional<Car> afterSave = carRepository.findByVinNumber("testvin1");
        assertFalse(beforeSave.isPresent());
        assertTrue(afterSave.isPresent());
        assertEquals("TESTVIN1", afterSave.get().getVinNumber());
        //Clean up
        brandRepository.deleteById(brand.getId());
    }

    @Test
    public void testNotDeleteCar() {
        //Given
        //When
        boolean result = carDetailsService.deleteByVinNumber("not exist vin");
        //Then
        assertFalse(result);
    }

    @Test
    public void testDeleteCar() {
        //Given
        Brand brand = new Brand("test audi");
        Brand savedBrand = brandRepository.save(brand);
        Model model = new Model("test A6", brand);
        Model savedModel = modelRepository.save(model);
        Car car1 = new Car("testvin1", savedBrand, savedModel, true);
        carRepository.save(car1);
        //When
        boolean isDeleted = carDetailsService.deleteByVinNumber(car1.getVinNumber());
        //Then
        Optional<Car> deletedCar = carRepository.findByVinNumber("testvin1");
        assertTrue(isDeleted);
        assertFalse(deletedCar.isPresent());
        //Clean up
        brandRepository.deleteById(brand.getId());
    }

    @Test
    public void testChangeAvailabilityCar() {
        //Given
        Brand brand = new Brand("test audi");
        Brand savedBrand = brandRepository.save(brand);
        Model model = new Model("test A6", brand);
        Model savedModel = modelRepository.save(model);
        Car car1 = new Car("testvin1", savedBrand, savedModel, true);
        carRepository.save(car1);
        //When
        Result<Car> carResult = carDetailsService.changeAvailability("testvin1");
        //Then
        Optional<Car> updatedCar = carRepository.findByVinNumber("testvin1");
        assertEquals(Result.Success.class, carResult.getClass());
        assertTrue(updatedCar.isPresent());
        assertFalse(updatedCar.get().isAvailability());
        //Clean up
        brandRepository.deleteById(brand.getId());
    }
}