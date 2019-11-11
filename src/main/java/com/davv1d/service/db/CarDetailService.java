package com.davv1d.service.db;

import com.davv1d.domain.car.Car;
import org.springframework.stereotype.Service;

import static com.davv1d.service.validate.ExistValidator.*;

@Service
public class CarDetailService extends CarDbService {

    public Car saveCarIfItDoesNotExist(final Car car) {
        return ifExists(car.getVinNumber(), this::getByVinNumber)
                .getOrElse(() -> save(car));
    }

    public boolean deleteByVinNumber(String vinNumber) {
        return ifExists(vinNumber, this::getByVinNumber)
                .effect(this::deleteCar);
    }

    public boolean changeAvailability(final Car car) {
        return ifExists(car.getVinNumber(), this::getByVinNumber)
                .effect(car1 -> {
                    Car updatedCar = new Car(car1.getId(), car1.getVinNumber(), car1.getBrand(), car1.getModel(), !car1.isAvailability());
                    updateCar(updatedCar);
                });
    }
}