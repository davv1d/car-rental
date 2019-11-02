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

    @GetMapping("/fetchAll")
    public List<RentalDto> fetchAll() {
        return rentalMapper.mapToRentalDtoList(rentalDbService.fetchAll());
    }

    @GetMapping("/fetch-by-vin")
    public List<RentalDto> fetchRentalsByCarVinNumber(@RequestParam String vinNumber) {
        return rentalMapper.mapToRentalDtoList(rentalDbService.fetchRentalsByCarVinNumber(vinNumber));
    }

    @GetMapping("/fetch-by-username")
    public List<RentalDto> fetchRentalsByUsername(@RequestParam String username) {
        return rentalMapper.mapToRentalDtoList(rentalDbService.fetchRentalsByUsername(username));
    }

    @DeleteMapping("/delete")
    public void deleteBRentalsById(@RequestParam long id) {
        rentalDbService.deleteById(id);
    }

    @PostMapping("/create")
    public void createRental(@RequestBody RentalDto rentalDto) {
        rentalDbService.save(rentalMapper.mapToRental(rentalDto));
    }
}
