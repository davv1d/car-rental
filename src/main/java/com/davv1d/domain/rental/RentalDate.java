package com.davv1d.domain.rental;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RentalDate {
    private LocalDateTime dateOfRent;
    private LocalDateTime dateOfReturn;
}
