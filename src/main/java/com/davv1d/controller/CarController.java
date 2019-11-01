package com.davv1d.controller;

import com.davv1d.domain.car.dto.CarDto;
import com.davv1d.mapper.car.CarMapper;
import com.davv1d.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class CarController {
    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CarRepository carRepository;

    @GetMapping("/cars")
    public List<CarDto> getCars() {
        return carMapper.mapToCarDtoList(carRepository.findAll());
    }

    @PostMapping("/cars")
    public void createCar(@RequestBody CarDto carDto) {

    }
}
