package com.davv1d.repository;

import com.davv1d.domain.car.Car;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    @Query(nativeQuery = true)
    List<Car> fetchAvailabilityCars(@Param("DATE_OF_RENT") LocalDateTime dateOfRent, @Param("DATE_OF_RETURN") LocalDateTime dateOfReturn);

    @Override
    long count();

    long countByAvailability(boolean availability);
}
