package com.davv1d.controller;

import com.davv1d.domain.car.dto.CarDto;
import com.davv1d.mapper.car.CarMapper;
import com.davv1d.service.car.BrandDbService;
import com.davv1d.service.car.CarDbService;
import com.davv1d.service.car.ModelDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/cars")
public class CarController {
    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CarDbService carDbService;

    @Autowired
    private BrandDbService brandDbService;

    @Autowired
    private ModelDbService modelDbService;

    @GetMapping("/getCars")
    public List<CarDto> getCars() {
        return carMapper.mapToCarDtoList(carDbService.fetchAllCars());
    }

    @PostMapping("/createCars")
    public void createCar(@RequestBody CarDto carDto) {
        carDbService.saveCarIfItDoesNotExist(carMapper.mapToCar(carDto));
    }

    @PutMapping("/setAvailability")
    public void setAvailability(@RequestBody CarDto carDto) {
        carDbService.setAvailability(carMapper.mapToCar(carDto));
    }

    @DeleteMapping("/deleteCar")
    public void deleteCar(@RequestParam String vinNumber) {
        carDbService.deleteCar(vinNumber);
    }

    @DeleteMapping("/brand")
    public void deleteBrand(@RequestParam String brandName) {
        brandDbService.deleteBrand(brandName);
    }

    @DeleteMapping("/model")
    public void deleteModel(@RequestParam String modelName) {
        modelDbService.deleteByName(modelName);
    }

    @GetMapping("/availability")
    public List<CarDto> fetchAvailabilityCars(@RequestParam LocalDateTime dateOfRent, @RequestParam LocalDateTime dateOfReturn) {
        return carMapper.mapToCarDtoList(carDbService.fetchAvailabilityCars(dateOfRent, dateOfReturn));
    }
}
