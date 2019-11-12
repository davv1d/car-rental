package com.davv1d.mapper.rental;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.Car;
import com.davv1d.domain.car.Model;
import com.davv1d.domain.car.dto.CarDto;
import com.davv1d.domain.rental.Rental;
import com.davv1d.domain.rental.RentalDto;
import com.davv1d.domain.rental.SaveRentalDto;
import com.davv1d.domain.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RentalMapperTestSuite {
    private Car testCar;
    private User user;
    private Brand brand;

    @Autowired
    private RentalMapper rentalMapper;

    @Before
    public void init() {
        brand = new Brand("audi");
        testCar = new Car("123", brand, new Model("a8", brand), false);
        user = new User("username", "test@test.com");
    }

    @Test
    public void shouldReturnRentalDto() {
        //Given
        Rental rental = new Rental(1L, user, testCar, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(8));
        //When
        RentalDto rentalDto = rentalMapper.mapToRentalDto(rental);
        //Then
        assertEquals(rental.getCar().getVinNumber(), rentalDto.getCar().getVinNumber());
        assertEquals(rental.getCar().getBrand().getName(), rentalDto.getCar().getBrand());
        assertEquals(rental.getCar().getVinNumber(), rentalDto.getCar().getVinNumber());
        assertEquals(rental.getUser().getUsername(), rentalDto.getUsername());
        assertEquals(rental.getDateOfRent(), rentalDto.getDateOfRent());
    }

    @Test
    public void shouldReturnListOfRentalsDto() {
        //Given
        Rental rental = new Rental(1L, user, testCar, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(8));
        List<Rental> listOfRentals = new ArrayList<>();
        listOfRentals.add(rental);
        listOfRentals.add(rental);
        //When
        List<RentalDto> listOfRentalsDto = rentalMapper.mapToRentalDtoList(listOfRentals);
        //Then
        assertEquals(listOfRentals.size(),listOfRentalsDto.size());
        assertEquals(listOfRentals.get(0).getUser().getUsername(),listOfRentalsDto.get(0).getUsername());
    }
    @Test
    public void shouldReturnRentalFromRentalDto() {
        //Given
        CarDto carDto = new CarDto("123","audi","a8",false);
        RentalDto rentalDto = new RentalDto(1, "username", carDto, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(8));
        //When
        Rental rental = rentalMapper.mapToRental(rentalDto);
        //Then
        assertEquals(rentalDto.getCar().getVinNumber(), rental.getCar().getVinNumber());
        assertEquals(rentalDto.getCar().getBrand(), rental.getCar().getBrand().getName().toLowerCase());
        assertEquals(rentalDto.getCar().getVinNumber(), rental.getCar().getVinNumber());
        assertEquals(rentalDto.getUsername(), rental.getUser().getUsername());
        assertEquals(rentalDto.getDateOfRent(), rental.getDateOfRent());
    }
    @Test
    public void shouldReturnRentalFromSaveRentalDto() {
        //Given
        SaveRentalDto saveRentalDto = new SaveRentalDto(1, "username","123", LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(8));
        //When
        Rental rental = rentalMapper.mapToRental(saveRentalDto);
        //Then
        assertEquals(saveRentalDto.getVin(), rental.getCar().getVinNumber());
        assertEquals(saveRentalDto.getDateOfReturn(), rental.getDateOfReturn());
        assertEquals(saveRentalDto.getUsername(), rental.getUser().getUsername());
        assertEquals(saveRentalDto.getDateOfRent(), rental.getDateOfRent());
    }
    @Test
    public void shouldReturnRentalFromSaveRentalDtoAndUsername() {
        //Given
        SaveRentalDto saveRentalDto = new SaveRentalDto(1,"username","123", LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(8));
        //When
        Rental rental = rentalMapper.mapToRental(saveRentalDto,"username");
        //Then
        assertEquals(saveRentalDto.getVin(), rental.getCar().getVinNumber());
        assertEquals(saveRentalDto.getDateOfReturn(), rental.getDateOfReturn());
        assertEquals(saveRentalDto.getUsername(), rental.getUser().getUsername());
        assertEquals(saveRentalDto.getDateOfRent(), rental.getDateOfRent());
    }
    @Test
    public void shouldReturnRentalList(){
        //Given
        CarDto carDto = new CarDto("123","audi","a8",false);
        RentalDto rentalDto = new RentalDto(1, "username", carDto, LocalDateTime.now().plusDays(2), LocalDateTime.now().plusDays(8));
        List<RentalDto> listOfRentalsDto = new ArrayList<>();
        listOfRentalsDto.add(rentalDto);
        listOfRentalsDto.add(rentalDto);
        //When
        List<Rental> listOfRentals = rentalMapper.mapToRentalList(listOfRentalsDto);
        //Then
        assertEquals(listOfRentalsDto.size(),listOfRentals.size());
        assertEquals(listOfRentalsDto.get(0).getUsername(),listOfRentals.get(0).getUser().getUsername());
        assertEquals(listOfRentalsDto.get(0).getCar().getVinNumber(),listOfRentals.get(0).getCar().getVinNumber());
        assertEquals(listOfRentalsDto.get(0).getDateOfRent(),listOfRentals.get(0).getDateOfRent());
    }
}