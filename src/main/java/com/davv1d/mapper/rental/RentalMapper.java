package com.davv1d.mapper.rental;

import com.davv1d.domain.car.Car;
import com.davv1d.domain.rental.Rental;
import com.davv1d.domain.rental.RentalDto;
import com.davv1d.domain.rental.NewRentalDto;
import com.davv1d.domain.user.User;
import com.davv1d.mapper.car.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalMapper {
    @Autowired
    private CarMapper carMapper;

    public RentalDto mapToRentalDto(final Rental rental) {
        return new RentalDto(
                rental.getId(),
                rental.getUser().getUsername(),
                carMapper.mapToCarDto(rental.getCar()),
                rental.getDateOfRent(),
                rental.getDateOfReturn());
    }

    public List<RentalDto> mapToRentalDtoList(final List<Rental> rentals) {
        return rentals.stream()
                .map(this::mapToRentalDto)
                .collect(Collectors.toList());
    }

    public Rental mapToRental(final RentalDto rentalDto) {
        return new Rental(
                rentalDto.getId(),
                new User(rentalDto.getUsername()),
                carMapper.mapToCar(rentalDto.getCar()),
                rentalDto.getDateOfRent(),
                rentalDto.getDateOfReturn());
    }

    public Rental mapToRental(final NewRentalDto newRentalDto, String username) {
        return new Rental(
                new User(username),
                new Car(newRentalDto.getVin()),
                newRentalDto.getDateOfRent(),
                newRentalDto.getDateOfReturn()
        );
    }
}
