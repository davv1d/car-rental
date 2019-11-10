package com.davv1d.service.db;

import com.davv1d.domain.car.Car;
import com.davv1d.domain.rental.Rental;
import com.davv1d.domain.user.User;
import com.davv1d.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalDbService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CarDetailServiceService carDetailService;

    @Autowired
    private UserDbService userDbService;

    public void save(final Rental rental) {
        Optional<Car> optionalCar = carDetailService.getByVinNumber(rental.getCar().getVinNumber());
        Optional<User> optionalUser = userDbService.getUserByUsername(rental.getUser().getUsername());
        if (optionalCar.isPresent() && optionalUser.isPresent()) {
            rentalRepository.save(new Rental(optionalUser.get(), optionalCar.get(), rental.getDateOfRent(), rental.getDateOfReturn()));
        }
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
