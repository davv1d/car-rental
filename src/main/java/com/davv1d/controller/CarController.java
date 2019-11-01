package com.davv1d.controller;

import com.davv1d.domain.car.dto.CarDto;
import com.davv1d.mapper.car.CarMapper;
import com.davv1d.service.car.BrandDbService;
import com.davv1d.service.car.CarDbService;
import com.davv1d.service.car.ModelDbService;
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
    private CarDbService carDbService;

    @Autowired
    private BrandDbService brandDbService;

    @Autowired
    private ModelDbService modelDbService;

    @GetMapping("/cars")
    public List<CarDto> getCars() {
        return carMapper.mapToCarDtoList(carDbService.fetchAllCars());
    }

    @PostMapping("/cars")
    public void createCar(@RequestBody CarDto carDto) {
        carDbService.saveCarIfItDoesNotExist(carMapper.mapToCar(carDto));
    }

    @PutMapping("/cars")
    public void setAvailability(@RequestBody CarDto carDto) {
        carDbService.setAvailability(carMapper.mapToCar(carDto));
    }

    @DeleteMapping("/cars")
    public void deleteCar(@RequestParam String vinNumber) {
        carDbService.deleteCar(vinNumber);
    }

    @DeleteMapping("/cars/brand")
    public void deleteBrand(@RequestParam String brandName) {
        brandDbService.deleteBrand(brandName);
    }

    @DeleteMapping("/cars/model")
    public void deleteModel(@RequestParam String modelName) {
        modelDbService.deleteByName(modelName);
    }
}
