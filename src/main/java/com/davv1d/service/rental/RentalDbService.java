package com.davv1d.service.rental;

import com.davv1d.domain.user.User;
import com.davv1d.domain.car.Car;
import com.davv1d.domain.rental.Rental;
import com.davv1d.repository.RentalRepository;
import com.davv1d.service.UserDbService;
import com.davv1d.service.car.CarDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalDbService {
    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CarDbService carDbService;

    @Autowired
    private UserDbService userDbService;

    public void save(final Rental rental) {
        Optional<Car> optionalCar = carDbService.fetchByVinNumber(rental.getCar().getVinNumber());
        Optional<User> optionalUser = userDbService.findUserByUsername(rental.getUser().getUsername());
        if (optionalCar.isPresent() && optionalUser.isPresent()) {
            rentalRepository.save(new Rental(optionalUser.get(), optionalCar.get(), rental.getDateOfRent(), rental.getDateOfReturn()));
        }
    }

    public List<Rental> fetchRentalsByCarVinNumber(final String vinNumber) {
        return rentalRepository.fetchRentalsByCarVinNumber(vinNumber);
    }

    public List<Rental> fetchRentalsByUsername(final String username) {
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

    public List<Rental> fetchAll() {
        return rentalRepository.findAll();
    }
}
