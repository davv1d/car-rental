package com.davv1d.service.car;

import com.davv1d.domain.car.Car;
import com.davv1d.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarDbService {
    @Autowired
    private CarRepository carRepository;


    public Car saveCarIfItDoesNotExist(final Car car) {
        return fetchByVinNumber(car.getVinNumber())
                .orElseGet(() -> save(car));
    }

    private Car save(Car car) {
        return null;
    }

    private Optional<Car> fetchByVinNumber(String vinNumber) {
        return carRepository.findByVinNumber(vinNumber.toUpperCase());
    }

    //
//    public OwnedCar saveOwnedCarIfItDoesNotExist(final Car car) {
//        return fetchByVinNumber(car.getVinNumber())
//                .orElseGet(() -> save(car));
//    }
//
//    private OwnedCar save(final Car car) {
//        CarModel carModel = carModelDbService.saveCarModelIfItDoesNotExist(car.getModel(), car.getBrand());
//        return ownedCarRepository.save(new OwnedCar(car.getVinNumber(), carModel, car.isAvailability()));
//    }
//
//    public Optional<OwnedCar> fetchByVinNumber(String vinNumber) {
//        return ownedCarRepository.findByVinNumber(vinNumber.toUpperCase());
//    }
//
//    public void deleteCar(String vinNumber) {
//        fetchByVinNumber(vinNumber)
//                .ifPresent(this::delete);
//    }
//
//    private void delete(final OwnedCar ownedCar) {
//        ownedCar.getModel().getOwnedCars().remove(ownedCar);
//        OwnedCar updatedOwnedCar = new OwnedCar(ownedCar.getId(), ownedCar.getVinNumber(), null, ownedCar.isAvailability());
//        ownedCarRepository.save(updatedOwnedCar);
//        ownedCarRepository.deleteById(ownedCar.getId());
//    }
//
//    public void setAvailability(final Car car) {
//        fetchByVinNumber(car.getVinNumber())
//                .ifPresent(ownedCar -> {
//                    OwnedCar updatedCar = new OwnedCar(ownedCar.getId(), ownedCar.getVinNumber(), ownedCar.getModel(), car.isAvailability());
//                    ownedCarRepository.save(updatedCar);
//                });
//    }
//
//    public List<OwnedCar> fetchAllCars() {
//        return ownedCarRepository.findAll();
//    }
}
