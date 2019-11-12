package com.davv1d.controller;

import com.davv1d.domain.car.dto.CarDto;
import com.davv1d.facade.CarFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/cars")
public class CarController {
    @Autowired
    private CarFacade carFacade;

    @GetMapping("/getCars")
    public List<CarDto> getCars() {
        return carFacade.getAllCars();
    }

    @PostMapping("/createCars")
    public void createCar(@RequestBody CarDto carDto) {
        carFacade.createCar(carDto);
    }

    @PutMapping("/availability/change")
    public void changeAvailability(@RequestBody String vinNumber, Principal principal) {
        carFacade.changeAvailability(vinNumber, principal);
    }

    @DeleteMapping("/delete")
    public void deleteCar(@RequestParam String vin) {
        carFacade.deleteCarByVin(vin);
    }

    @DeleteMapping("/brand")
    public void deleteBrand(@RequestParam String name) {
        carFacade.deleteBrand(name);
    }

    @DeleteMapping("/model")
    public void deleteModel(@RequestParam String name) {
        carFacade.deleteModel(name);
    }

    @GetMapping("/availability")
    public List<CarDto> getAvailabilityCars(@RequestParam String dateOfRent, @RequestParam String dateOfReturn) {
        return carFacade.getAvailabilityCars(dateOfRent, dateOfReturn);
    }

    @GetMapping("/getCar")
    public ResponseEntity<?> getCarByVin(@RequestParam String vin) {
        return carFacade.getCarByVin(vin);
    }
}
