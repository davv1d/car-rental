package com.davv1d.service.validate;

import com.davv1d.domain.rental.RentalDate;
import com.davv1d.functional.Result;
import org.junit.Test;

import java.time.LocalDateTime;

import static com.davv1d.service.validate.DateValidator.*;
import static org.junit.Assert.assertEquals;

public class DateValidatorTestSuite {
    @Test
    public void ifDateIsBeforeDatePattern() {
        //Given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime patternDate = LocalDateTime.now().plusDays(5);
        //When
        Result<LocalDateTime> result = isDateAfterDatePattern(now, patternDate,"mess");
        //Then
        assertEquals(Result.Failure.class, result.getClass());
        assertEquals("mess", result.getErrorMessage());
    }

    @Test
    public void ifDateIsAfterDatePattern() {
        //Given
        LocalDateTime date1 = LocalDateTime.now().plusDays(9);
        LocalDateTime patternDate = LocalDateTime.now().plusDays(5);
        //When
        Result<LocalDateTime> result = isDateAfterDatePattern(date1, patternDate, DATE_OF_RENT_IS_BEFORE_CURRENT_DATE);
        //Then
        assertEquals(Result.Success.class, result.getClass());
    }

    @Test
    public void ifDateOfReturnIsBeforeDateOfRent() {
        //Given
        LocalDateTime dateOfRent = LocalDateTime.now().plusDays(2);
        LocalDateTime dateOfReturn = LocalDateTime.now();
        RentalDate rentalDate = new RentalDate(dateOfRent, dateOfReturn);
        //When
        Result<RentalDate> result = isDateOfReturnAfterDateOfRent(rentalDate);
        //Then
        assertEquals(Result.Failure.class, result.getClass());
        assertEquals(DATE_OF_RETURN_IS_BEFORE_DATE_OF_RENT, result.getErrorMessage());
    }

    @Test
    public void ifDateOfReturnIsAfterDateOfRent() {
        //Given
        LocalDateTime dateOfRent = LocalDateTime.now().plusDays(2);
        LocalDateTime dateOfReturn = LocalDateTime.now().plusDays(7);
        RentalDate rentalDate = new RentalDate(dateOfRent, dateOfReturn);
        //When
        Result<RentalDate> result = isDateOfReturnAfterDateOfRent(rentalDate);
        //Then
        assertEquals(Result.Success.class, result.getClass());
    }

    @Test
    public void ifDateOfRentIsAfterCurrentDate() {
        //Given
        LocalDateTime dateOfRent = LocalDateTime.now().plusDays(2);
        LocalDateTime dateOfReturn = LocalDateTime.now().plusDays(6);
        RentalDate rentalDate = new RentalDate(dateOfRent, dateOfReturn);
        //When
        Result<RentalDate> result = isDateOfRentAfterCurrentDate(rentalDate);
        //Then
        assertEquals(Result.Success.class, result.getClass());
    }

    @Test
    public void ifDateOfRentIsBeforeCurrentDate() {
        //Given
        LocalDateTime dateOfRent = LocalDateTime.now().minusDays(2);
        LocalDateTime dateOfReturn = LocalDateTime.now().plusDays(6);
        RentalDate rentalDate = new RentalDate(dateOfRent, dateOfReturn);
        //When
        Result<RentalDate> result = isDateOfRentAfterCurrentDate(rentalDate);
        //Then
        assertEquals(Result.Failure.class, result.getClass());
        assertEquals(DATE_OF_RENT_IS_BEFORE_CURRENT_DATE, result.getErrorMessage());
    }
}