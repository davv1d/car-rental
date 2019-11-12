package com.davv1d.service.db;

import com.davv1d.domain.rental.NewRental;
import com.davv1d.domain.rental.Rental;
import com.davv1d.functional.Result;
import com.davv1d.repository.RentalRepository;
import com.davv1d.service.validate.RentalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalDbService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private RentalValidator rentalValidator;

    public Result<Rental> save(final NewRental newRental) {
       return rentalValidator.checkRental(newRental)
                .map(rentalRepository::save);
    }

    public List<Rental> getRentalsByCarVinNumber(final String vinNumber) {
        return rentalRepository.fetchRentalsByCarVinNumber(vinNumber);
    }

    public List<Rental> getRentalsByUsername(final String username) {
        return rentalRepository.fetchRentalsByUsername(username);
    }

    public void deleteById(Long rentalId) {
        rentalRepository.findById(rentalId)
                .ifPresent(this::delete);
    }

    private void delete(Rental rental) {
        Rental updatedRental = new Rental(rental.getId(), null, null, rental.getDateOfRent(), rental.getDateOfReturn());
        rentalRepository.save(updatedRental);
        rentalRepository.deleteById(updatedRental.getId());
    }

    public List<Rental> getAll() {
        return rentalRepository.findAll();
    }
}
