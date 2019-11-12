package com.davv1d.service.validate;

import com.davv1d.domain.rental.RentalDate;
import com.davv1d.functional.Result;

import java.time.LocalDateTime;

public class DateValidator {
    public static final String DATE_OF_RENT_IS_BEFORE_CURRENT_DATE = "DATE OF RENT IS BEFORE CURRENT DATE";
    public static final String DATE_OF_RETURN_IS_BEFORE_DATE_OF_RENT = "DATE OF RETURN IS BEFORE DATE OF RENT";

    public static Result<RentalDate> checkDate(RentalDate rentalDate) {
        return isDateOfRentAfterCurrentDate(rentalDate)
                .flatMap(DateValidator::isDateOfReturnAfterDateOfRent);
    }

    public static Result<RentalDate> isDateOfRentAfterCurrentDate(final RentalDate rentalDate) {
        return isDateAfterDatePattern(rentalDate.getDateOfRent(), LocalDateTime.now(), DATE_OF_RENT_IS_BEFORE_CURRENT_DATE)
                .map(dateOfRent -> new RentalDate(dateOfRent, rentalDate.getDateOfReturn()));
    }

    public static Result<RentalDate> isDateOfReturnAfterDateOfRent(final RentalDate rentalDate) {
        LocalDateTime dateOfReturn = rentalDate.getDateOfReturn();
        LocalDateTime dateOfRent = rentalDate.getDateOfRent();
        return isDateAfterDatePattern(dateOfReturn, dateOfRent, DATE_OF_RETURN_IS_BEFORE_DATE_OF_RENT)
                .map(dateOfReturn1 -> new RentalDate(dateOfRent, dateOfReturn1));
    }

    public static Result<LocalDateTime> isDateAfterDatePattern(LocalDateTime date, LocalDateTime patternDate, String errorMessage) {
        if (date.isAfter(patternDate)) {
            return Result.success(date);
        } else {
            return Result.failure(errorMessage);
        }
    }
}
