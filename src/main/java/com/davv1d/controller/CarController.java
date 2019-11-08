package com.davv1d.controller;

import com.davv1d.domain.car.Car;
import com.davv1d.domain.car.RepairStats;
import com.davv1d.domain.car.dto.CarDto;
import com.davv1d.errors.CarNotFoundException;
import com.davv1d.mapper.car.CarMapper;
import com.davv1d.service.db.BrandDbService;
import com.davv1d.service.db.CarDbService;
import com.davv1d.service.db.ModelDbService;
import com.davv1d.service.db.RepairStatsDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @Autowired
    private RepairStatsDbService repairStatsDbService;

    @GetMapping("/getCars")
    public List<CarDto> getCars() {
        return carMapper.mapToCarDtoList(carDbService.getCars());
    }

    @PostMapping("/createCars")
    public void createCar(@RequestBody CarDto carDto) {
        carDbService.saveCarIfItDoesNotExist(carMapper.mapToCar(carDto));
    }

    @PutMapping("/availability")
    public void setAvailability(@RequestBody CarDto carDto, Principal principal) {
        Car car = carMapper.mapToCar(carDto);
        if (carDbService.setAvailability(car)) {
            repairStatsDbService.save(new RepairStats(principal.getName(), car.getVinNumber(), LocalDateTime.now(), car.isAvailability()));
        }
    }

    @DeleteMapping("/delete")
    public void deleteCar(@RequestParam String vin) {
        carDbService.deleteCar(vin);
    }

    @DeleteMapping("/brand")
    public void deleteBrand(@RequestParam String name) {
        brandDbService.deleteBrand(name);
    }

    @DeleteMapping("/model")
    public void deleteModel(@RequestParam String name) {
        modelDbService.deleteByName(name);
    }

    @GetMapping("/availability")
    public List<CarDto> getAvailabilityCars(@RequestParam String dateOfRent, @RequestParam String dateOfReturn) {
        LocalDateTime dateOfRentLocal = LocalDateTime.parse(dateOfRent);
        LocalDateTime dateOfReturnLocal = LocalDateTime.parse(dateOfReturn);
        return carMapper.mapToCarDtoList(carDbService.getAvailabilityCars(dateOfRentLocal, dateOfReturnLocal));
    }

    @GetMapping("/getCar")
    public CarDto getCarByVin(@RequestParam String vin) throws CarNotFoundException {
        return carMapper.mapToCarDto(carDbService.getByVinNumber(vin)
                .orElseThrow(() -> new CarNotFoundException("Not found car with this vin")));
    }
}
