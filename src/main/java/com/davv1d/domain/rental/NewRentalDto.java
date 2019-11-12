package com.davv1d.domain.rental;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NewRentalDto {
    private long id;
    private String vin;
    private LocalDateTime dateOfRent;
    private LocalDateTime dateOfReturn;

}
