package com.davv1d.domain.car.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CarDto {
    private String vinNumber;
    private String brand;
    private String model;
    private boolean availability;
}
