package com.davv1d.repository;

import com.davv1d.domain.car.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CarRepository extends CrudRepository<Car, Long> {
    @SuppressWarnings("unchecked")
    @Override
    Car save(Car car);

    @Override
    Optional<Car> findById(Long carId);

    @Override
    List<Car> findAll();

    Optional<Car> findByVinNumber(String vinNumber);
}
