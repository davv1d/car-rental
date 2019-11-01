package com.davv1d.domain.car.dto;

import com.davv1d.domain.car.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CarDto {
    private String vinNumber;
    private String brand;
    private String model;
    private boolean availability;
}
