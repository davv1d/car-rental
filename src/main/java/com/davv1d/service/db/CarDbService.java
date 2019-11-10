package com.davv1d.service.db;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.Car;
import com.davv1d.domain.car.Model;
import com.davv1d.functional.Result;
import com.davv1d.repository.CarRepository;
import com.davv1d.service.validate.ExistValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CarDbService {
    @Autowired
    private CarRepository carRepository;

    @Autowired
    private BrandDbService brandDbService;

    @Autowired
    private ModelDbService modelDbService;

    @Autowired
    private ExistValidator existValidator;

    public Car saveCarIfItDoesNotExist(final Car car) {
        return existValidator.checkExist(car.getVinNumber(), this::getByVinNumber)
                .getOrElse(() -> save(car));
    }

    public Car save(final Car car) {
        String brandName = car.getBrand().getName();
        String modelName = car.getModel().getName();
        Brand brand = brandDbService.saveBrandIfItDoesNotExist(brandName);
        Model model = modelDbService.saveModelIfItDoesNotExist(modelName, brandName);
        return carRepository.save(new Car(car.getVinNumber(), brand, model, car.isAvailability()));
    }

    public Optional<Car> getByVinNumber(final String vinNumber) {
        return carRepository.findByVinNumber(vinNumber.toUpperCase());
    }

    public boolean deleteCar(String vinNumber) {
        return existValidator.checkExist(vinNumber, this::getByVinNumber)
                .effect(this::deleteFromDb);
    }

    private void deleteFromDb(final Car car) {
        car.getModel().getCars().remove(car);
        Car updatedCar = new Car(car.getId(), car.getVinNumber(), null, null, car.isAvailability());
        carRepository.save(updatedCar);
        carRepository.deleteById(car.getId());
    }

    public boolean changeAvailability(final Car car) {
        return existValidator.checkExist(car.getVinNumber(), this::getByVinNumber)
                .effect(car1 -> {
                    Car updatedCar = new Car(car1.getId(), car1.getVinNumber(), car1.getBrand(), car1.getModel(), !car1.isAvailability());
                    carRepository.save(updatedCar);
                });
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }

    public List<Car> getAvailabilityCars(final LocalDateTime dateOfRent, final LocalDateTime dateOfReturn) {
        return carRepository.fetchAvailabilityCars(dateOfRent, dateOfReturn);
    }
}
