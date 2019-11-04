package com.davv1d.controller;

import com.davv1d.domain.rental.RentalDto;
import com.davv1d.mapper.rental.RentalMapper;
import com.davv1d.service.rental.RentalDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/rental")
public class RentalController {
    @Autowired
    private RentalDbService rentalDbService;

    @Autowired
    private RentalMapper rentalMapper;

    @GetMapping("/fetch")
    public List<RentalDto> getRentals() {
        return rentalMapper.mapToRentalDtoList(rentalDbService.fetchAll());
    }

    @GetMapping("/fetch/{vin}")
    public List<RentalDto> getRentalsByCarVinNumber(@PathVariable String vin) {
        return rentalMapper.mapToRentalDtoList(rentalDbService.fetchRentalsByCarVinNumber(vin));
    }

    @GetMapping("/fetch/{username}")
    public List<RentalDto> getRentalsByUsername(@PathVariable String username) {
        return rentalMapper.mapToRentalDtoList(rentalDbService.fetchRentalsByUsername(username));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteRentalsById(@PathVariable long id) {
        rentalDbService.deleteById(id);
    }

    @PostMapping("/create")
    public void createRental(@RequestBody RentalDto rentalDto) {
        rentalDbService.save(rentalMapper.mapToRental(rentalDto));
    }
}
