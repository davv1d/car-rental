package com.davv1d.mapper.rental;

import com.davv1d.domain.rental.NewRental;
import com.davv1d.domain.rental.NewRentalDto;
import org.springframework.stereotype.Component;

@Component
public class NewRentalMapper {
    public NewRental mapToNewRental(NewRentalDto newRentalDto, String username) {
        return new NewRental(
                username,
                newRentalDto.getVin(),
                newRentalDto.getDateOfRent(),
                newRentalDto.getDateOfReturn()
        );
    }
}
