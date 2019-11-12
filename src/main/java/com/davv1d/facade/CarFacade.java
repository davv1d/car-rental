package com.davv1d.facade;

import com.davv1d.domain.car.RepairStats;
import com.davv1d.domain.car.dto.CarDto;
import com.davv1d.domain.rental.RentalDate;
import com.davv1d.functional.Result;
import com.davv1d.mapper.car.CarMapper;
import com.davv1d.repository.RepairStatsRepository;
import com.davv1d.service.db.BrandDbService;
import com.davv1d.service.db.CarDetailsService;
import com.davv1d.service.db.ModelDbService;
import com.davv1d.service.validate.DateValidator;
import com.davv1d.service.validate.ExistenceChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@Component
public class CarFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarFacade.class);

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CarDetailsService carDetailsService;

    @Autowired
    private BrandDbService brandDbService;

    @Autowired
    private ModelDbService modelDbService;

    @Autowired
    private RepairStatsRepository repairStatsRepository;

    public List<CarDto> getAllCars() {
        return carMapper.mapToCarDtoList(carDetailsService.getCars());
    }

    public void createCar(CarDto carDto) {
        carDetailsService.saveCarIfItDoesNotExist(carMapper.mapToCar(carDto));
    }

    public ResponseEntity<?> changeAvailability(String vinNumber, Principal principal) {
        return carDetailsService.changeAvailability(vinNumber)
                .effect(car1 -> {
                    repairStatsRepository.save(new RepairStats(principal.getName(), car1.getVinNumber(), LocalDateTime.now(), car1.isAvailability()));
                    return ok().body(car1);
                }, error -> {
                    LOGGER.error(error);
                    return badRequest().body(error);
                });
    }

    public void deleteCarByVin(String vin) {
        carDetailsService.deleteByVinNumber(vin);
    }

    public List<CarDto> getAvailabilityCars(String dateOfRent, String dateOfReturn) {
        LocalDateTime dateOfRentLocal = LocalDateTime.parse(dateOfRent);
        LocalDateTime dateOfReturnLocal = LocalDateTime.parse(dateOfReturn);
        return DateValidator.checkDate(new RentalDate(dateOfRentLocal, dateOfReturnLocal))
                .map(rentalDate -> carDetailsService.getAvailabilityCars(rentalDate.getDateOfRent(), rentalDate.getDateOfReturn()))
                .map(cars -> carMapper.mapToCarDtoList(cars))
                .effect(carsDto -> carsDto, s -> new ArrayList<>());
//        return carMapper.mapToCarDtoList(carDetailsService.getAvailabilityCars(dateOfRentLocal, dateOfReturnLocal));
    }

    public ResponseEntity<?> getCarByVin(String vin) {
        return ExistenceChecker.ifExists(vin, carDetailsService::getByVinNumber)
                .map(carMapper::mapToCarDto)
                .effect(ResponseEntity::ok, error -> {
                    LOGGER.error(error);
                    return badRequest().body(error);
                });
    }

    public void deleteBrand(String name) {
        brandDbService.deleteBrand(name);
    }

    public void deleteModel(String name) {
        modelDbService.deleteModelByName(name);
    }
}
