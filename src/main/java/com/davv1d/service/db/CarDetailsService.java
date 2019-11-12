package com.davv1d.service.db;

import com.davv1d.domain.car.Car;
import com.davv1d.functional.Result;
import org.springframework.stereotype.Service;

import static com.davv1d.service.validate.ExistenceChecker.ifExists;

@Service
public class CarDetailsService extends CarDbService {

    public Car saveCarIfItDoesNotExist(final Car car) {
        return ifExists(car.getVinNumber(), this::getByVinNumber)
                .getOrElse(() -> save(car));
    }

    public boolean deleteByVinNumber(String vinNumber) {
        return ifExists(vinNumber, this::getByVinNumber)
                .effect(this::deleteCar);
    }

    public Result<Car> changeAvailability(final String vinNumber) {
        return ifExists(vinNumber, this::getByVinNumber)
                .map(car1 -> new Car(car1.getId(), car1.getVinNumber(), car1.getBrand(), car1.getModel(), !car1.isAvailability()))
                .map(this::updateCar);
    }
}
