package com.davv1d.controller;

import com.davv1d.domain.rental.Rental;
import com.davv1d.domain.rental.RentalDto;
import com.davv1d.domain.rental.SaveRentalDto;
import com.davv1d.mapper.rental.RentalMapper;
import com.davv1d.service.db.RentalDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class RentalController {
    @Autowired
    private RentalDbService rentalDbService;

    @Autowired
    private RentalMapper rentalMapper;

    @GetMapping("/rental")
    public List<RentalDto> getRentals() {
        return rentalMapper.mapToRentalDtoList(rentalDbService.getAll());
    }

    @GetMapping(value = "/rental/", params = "vin")
    public List<RentalDto> getRentalsByCarVinNumber(@RequestParam String vin) {
        return rentalMapper.mapToRentalDtoList(rentalDbService.getRentalsByCarVinNumber(vin));
    }

    @GetMapping(value = "/rental/", params = "username")
    public List<RentalDto> getRentalsByUsername(@RequestParam String username) {
        return rentalMapper.mapToRentalDtoList(rentalDbService.getRentalsByUsername(username));
    }

    @GetMapping(value = "/rental/user")
    public List<RentalDto> getLoggedUserRentals(Principal principal) {
        return rentalMapper.mapToRentalDtoList(rentalDbService.getRentalsByUsername(principal.getName()));
    }

    @DeleteMapping(value = "/rental/", params = "id")
    public void deleteRentalsById(@RequestParam long id) {
        rentalDbService.deleteById(id);
    }

    @PostMapping("/rental/create")
    public void createRental(@RequestBody SaveRentalDto rentalDto, Principal principal) {
        Rental rental = rentalMapper.mapToRental(rentalDto, principal.getName());
        rentalDbService.save(rental);
    }
}
