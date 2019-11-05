package com.davv1d.controller;

import com.davv1d.domain.rental.RentalDto;
import com.davv1d.mapper.rental.RentalMapper;
import com.davv1d.service.db.RentalDbService;
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

    @GetMapping("/rental")
    public List<RentalDto> getRentals() {
        return rentalMapper.mapToRentalDtoList(rentalDbService.getAll());
    }

    @GetMapping("/rental/{vin}")
    public List<RentalDto> getRentalsByCarVinNumber(@PathVariable String vin) {
        return rentalMapper.mapToRentalDtoList(rentalDbService.getRentalsByCarVinNumber(vin));
    }

    @GetMapping("/rental/{username}")
    public List<RentalDto> getRentalsByUsername(@PathVariable String username) {
        return rentalMapper.mapToRentalDtoList(rentalDbService.getRentalsByUsername(username));
    }

    @DeleteMapping("/rental/{id}")
    public void deleteRentalsById(@PathVariable long id) {
        rentalDbService.deleteById(id);
    }

    @PostMapping("/rental")
    public void createRental(@RequestBody RentalDto rentalDto) {
        rentalDbService.save(rentalMapper.mapToRental(rentalDto));
    }
}
