package com.davv1d.domain.rental;

import com.davv1d.domain.car.dto.CarDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RentalDto {
    private long id;
    private String username;
    private CarDto car;
    private LocalDateTime dateOfRent;
    private LocalDateTime dateOfReturn;
}
