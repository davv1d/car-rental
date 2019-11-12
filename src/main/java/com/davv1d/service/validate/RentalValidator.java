package com.davv1d.service.validate;

import com.davv1d.domain.car.Car;
import com.davv1d.domain.rental.NewRental;
import com.davv1d.domain.rental.Rental;
import com.davv1d.domain.rental.RentalDate;
import com.davv1d.domain.user.User;
import com.davv1d.functional.Result;
import com.davv1d.service.db.CarDetailsService;
import com.davv1d.service.db.UserDbDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.davv1d.service.validate.DateValidator.*;
import static com.davv1d.service.validate.ExistenceChecker.*;

@Service
public class RentalValidator {
    @Autowired
    private CarDetailsService carDetailsService;

    @Autowired
    private UserDbDetailsService userDbDetailsService;

    public Result<Rental> checkRental(NewRental newRental) {
        RentalDate rentalDate = new RentalDate(newRental.getDateOfRent(), newRental.getDateOfReturn());
        Result<Car> carResult = ifExists(newRental.getVinNumber(), carDetailsService::getByVinNumber);
        Result<User> userResult = ifExists(newRental.getUsername(), userDbDetailsService::getUserByUsername);
        return carResult
                .flatMap(car -> userResult
                        .flatMap(user -> checkDate(rentalDate)
                                .map(rentalDate1 -> new Rental(user, car, rentalDate.getDateOfRent(), rentalDate.getDateOfReturn()))));
    }
}
