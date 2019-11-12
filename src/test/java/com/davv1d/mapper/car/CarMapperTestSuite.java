package com.davv1d.mapper.car;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.Car;
import com.davv1d.domain.car.Model;
import com.davv1d.domain.car.dto.CarDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarMapperTestSuite {
    @Autowired
    private CarMapper carMapper;

    @Test
    public void testReturnCarDto() {
        //Given
        Model model = new Model("a6", new Brand("audi"));
        Car car = new Car("123", model.getBrand(), model, false);
        //When
        CarDto carDto = carMapper.mapToCarDto(car);
        //Then
        Assert.assertEquals(carDto.getVinNumber(), car.getVinNumber());
    }

    @Test
    public void shouldReturnCar() {
        //Given
        CarDto carDto = new CarDto("123", "audi", "a6", false);
        //When
        Car car = carMapper.mapToCar(carDto);
        //Then
        Assert.assertEquals(car.getVinNumber(), carDto.getVinNumber());
    }

    @Test
    public void shouldReturnCarDtoList() {
        //Given
        Model modelAudi = new Model("a6", new Brand("audi"));
        Car carAudi = new Car("123", modelAudi.getBrand(), modelAudi, false);
        Model modelBmw = new Model("x5", new Brand("bmw"));
        Car carBmw = new Car("124", modelBmw.getBrand(), modelBmw, false);
        List<Car> listOfCars = new ArrayList<>();
        listOfCars.add(carAudi);
        listOfCars.add(carBmw);
        //When
        List<CarDto> listOfCarsDto = carMapper.mapToCarDtoList(listOfCars);
        //Then
        Assert.assertEquals(listOfCars.get(0).getVinNumber(), listOfCarsDto.get(0).getVinNumber());
        Assert.assertEquals(listOfCars.size(), listOfCarsDto.size());
    }
}