package com.davv1d.mapper.car;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.Car;
import com.davv1d.domain.car.Model;
import com.davv1d.domain.car.dto.CarDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarMapper {
    public CarDto mapToCarDto(final Car car) {
        return new CarDto(car.getVinNumber(), car.getBrand().getName(), car.getModel().getName(), car.isAvailability());
    }

    public List<CarDto> mapToCarDtoList(final List<Car> cars) {
        return cars.stream()
                .map(this::mapToCarDto)
                .collect(Collectors.toList());
    }

    public Car mapToCar(final CarDto carDto) {
        Brand brand = new Brand(carDto.getBrand());
        return new Car(carDto.getVinNumber(), brand, new Model(carDto.getModel(), brand), carDto.isAvailability());
    }
}
