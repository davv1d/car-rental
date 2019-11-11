package com.davv1d.facade;

import com.davv1d.domain.car.Car;
import com.davv1d.domain.car.RepairStats;
import com.davv1d.domain.car.dto.CarDto;
import com.davv1d.mapper.car.CarMapper;
import com.davv1d.service.EmptyValuesClassCreator;
import com.davv1d.service.db.BrandDbService;
import com.davv1d.service.db.CarDetailServiceService;
import com.davv1d.service.db.ModelDbService;
import com.davv1d.service.db.RepairStatsDbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class CarFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarFacade.class);

    @Autowired
    private CarMapper carMapper;

    @Autowired
    private CarDetailServiceService carDetailService;

    @Autowired
    private BrandDbService brandDbService;

    @Autowired
    private ModelDbService modelDbService;

    @Autowired
    private RepairStatsDbService repairStatsDbService;

    public List<CarDto> getAllCars() {
        return carMapper.mapToCarDtoList(carDetailService.getCars());
    }

    public void createCar(CarDto carDto) {
        carDetailService.saveCarIfItDoesNotExist(carMapper.mapToCar(carDto));
    }

    public void setAvailability(CarDto carDto, Principal principal) {
        Car car = carMapper.mapToCar(carDto);
        if (carDetailService.changeAvailability(car)) {
            repairStatsDbService.save(new RepairStats(principal.getName(), car.getVinNumber(), LocalDateTime.now(), car.isAvailability()));
        }
    }

    public void deleteCarByVin(String vin) {
        carDetailService.deleteByVinNumber(vin);
    }

    public List<CarDto> getAvailabilityCars(String dateOfRent, String dateOfReturn) {
        LocalDateTime dateOfRentLocal = LocalDateTime.parse(dateOfRent);
        LocalDateTime dateOfReturnLocal = LocalDateTime.parse(dateOfReturn);
        return carMapper.mapToCarDtoList(carDetailService.getAvailabilityCars(dateOfRentLocal, dateOfReturnLocal));
    }

    public CarDto getCarByVin(String vin) {
        Optional<Car> optionalCar = carDetailService.getByVinNumber(vin);
        if (optionalCar.isPresent()) {
            return carMapper.mapToCarDto(optionalCar.get());
        } else {
            LOGGER.error("Not found car by vin");
            return EmptyValuesClassCreator.emptyCarDto();
        }
    }

    public void deleteBrand(String name) {
        brandDbService.deleteBrand(name);
    }

    public void deleteModel(String name) {
        modelDbService.deleteModelByName(name);
    }
}
