package com.davv1d.mapper.car;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.dto.BrandDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BrandMapper {
    @Autowired
    private ModelMapper modelMapper;

    public BrandDto mapToBrandDto(final Brand brand) {
        return new BrandDto(brand.getName(), modelMapper.mapToModelDtoList(brand.getModels()));
    }

    public List<BrandDto> mapToBrandDtoList(final List<Brand> brands) {
        return brands.stream()
                .map(this::mapToBrandDto)
                .collect(Collectors.toList());
    }
}
