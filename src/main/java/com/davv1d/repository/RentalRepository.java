package com.davv1d.repository;

import com.davv1d.domain.rental.Rental;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RentalRepository extends CrudRepository<Rental, Long> {
    @SuppressWarnings("unchecked")
    @Override
    Rental save(Rental rental);

    @Override
    Optional<Rental> findById(Long rentalId);

    @Override
    List<Rental> findAll();

    @Override
    void deleteById(Long rentalId);

    @Query(nativeQuery = true)
    List<Rental> fetchRentalsByCarVinNumber(@Param("VIN_NUMBER") String vinNumber);

    @Query(nativeQuery = true)
    List<Rental> fetchRentalsByUsername(@Param("USERNAME") String username);
}
