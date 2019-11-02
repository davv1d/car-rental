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
    public List<RentalDto> fetchAll() {
        return rentalMapper.mapToRentalDtoList(rentalDbService.fetchAll());
    }

    @GetMapping("/fetch/{vin}")
    public List<RentalDto> fetchRentalsByCarVinNumber(@PathVariable String vin) {
        return rentalMapper.mapToRentalDtoList(rentalDbService.fetchRentalsByCarVinNumber(vin));
    }

    @GetMapping("/fetch/{username}")
    public List<RentalDto> fetchRentalsByUsername(@PathVariable String username) {
        return rentalMapper.mapToRentalDtoList(rentalDbService.fetchRentalsByUsername(username));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBRentalsById(@PathVariable long id) {
        rentalDbService.deleteById(id);
    }

    @PostMapping("/create")
    public void createRental(@RequestBody RentalDto rentalDto) {
        rentalDbService.save(rentalMapper.mapToRental(rentalDto));
    }
}
