package com.davv1d.facade;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.Car;
import com.davv1d.domain.car.Model;
import com.davv1d.domain.car.RepairStats;
import com.davv1d.domain.car.dto.CarDto;
import com.davv1d.domain.user.User;
import com.davv1d.domain.user.role.Role;
import com.davv1d.repository.BrandRepository;
import com.davv1d.repository.CarRepository;
import com.davv1d.repository.ModelRepository;
import com.davv1d.repository.RepairStatsRepository;
import com.davv1d.service.db.CarDetailsService;
import com.sun.security.auth.UserPrincipal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarFacadeTestSuite {

    @Autowired
    private CarFacade carFacade;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private RepairStatsRepository repairStatsRepository;

    @Test
    public void shouldChangeAvailability() {
        //Given
        Brand brand = new Brand("test brand");
        Brand savedBrand = brandRepository.save(brand);
        Model model = new Model("test model", brand);
        Model savedModel = modelRepository.save(model);
        Car car1 = new Car("testvin1", savedBrand, savedModel, true);
        carRepository.save(car1);
        UserPrincipal user = new UserPrincipal("test name");
        //When
        ResponseEntity<?> result = carFacade.changeAvailability(car1.getVinNumber(), user);
        //Then
        Optional<Car> optionalCar = carRepository.findByVinNumber(car1.getVinNumber());
        List<RepairStats> repairsByVinNumber = repairStatsRepository.findByVinNumber(car1.getVinNumber());
        assertFalse(result.getStatusCode().isError());
        assertTrue(optionalCar.isPresent());
        assertFalse(optionalCar.get().isAvailability());
        assertEquals(1, repairsByVinNumber.size());
        //Clean up
        brandRepository.deleteByName(brand.getName());
        repairStatsRepository.deleteByVinNumber(optionalCar.get().getVinNumber());
    }

    @Test
    public void shouldNotChangeAvailabilityNotFindVinNumber() {
        //Given
        UserPrincipal user = new UserPrincipal("test name");
        String vinNumber = "not found vin";
        //When
        ResponseEntity<?> responseEntity = carFacade.changeAvailability(vinNumber, user);
        //Then
        assertTrue(responseEntity.getStatusCode().isError());
    }

    @Test
    public void shouldGetAvailabilityCars() {
        //Given
        Brand brand = new Brand("test brand");
        Brand savedBrand = brandRepository.save(brand);
        Model model = new Model("test model", brand);
        Model savedModel = modelRepository.save(model);
        Car car1 = new Car("testvin1", savedBrand, savedModel, true);
        carRepository.save(car1);
        //When
        List<CarDto> availabilityCars = carFacade.getAvailabilityCars(LocalDateTime.now().plusDays(1).toString(), LocalDateTime.now().plusDays(2).toString());
        //Then
        assertFalse(availabilityCars.isEmpty());
        //Clean up
        brandRepository.deleteByName(brand.getName());
    }
}