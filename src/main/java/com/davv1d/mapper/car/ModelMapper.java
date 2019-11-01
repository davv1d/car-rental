package com.davv1d.mapper.car;

import com.davv1d.domain.car.Model;
import com.davv1d.domain.car.dto.ModelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelMapper {
    @Autowired
    private CarMapper carMapper;

    public ModelDto mapToModelDto(final Model model) {
        return new ModelDto(model.getName(), model.getBrand().getName(), carMapper.mapToCarDtoList(model.getCars()));
    }

    public List<ModelDto> mapToModelDtoList(final List<Model> models) {
        return models.stream()
                .map(this::mapToModelDto)
                .collect(Collectors.toList());
    }
}
