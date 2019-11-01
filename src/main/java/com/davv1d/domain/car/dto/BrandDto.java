package com.davv1d.domain.car.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BrandDto {
    private String name;
    private List<ModelDto> modelList;
}
