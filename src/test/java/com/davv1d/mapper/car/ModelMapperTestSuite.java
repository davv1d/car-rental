package com.davv1d.mapper.car;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.Model;
import com.davv1d.domain.car.dto.ModelDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ModelMapperTestSuite {
    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void shouldReturnModelDto() {
        //Given
        Model model = new Model("a7", new Brand("audi"));
        //When
        ModelDto modelDto = modelMapper.mapToModelDto(model);
        //Then
        Assert.assertEquals(model.getName(), modelDto.getName());
    }

    @Test
    public void shouldReturnModelsDtoList() {
        //Given
        Model modelAudi = new Model("a7", new Brand("audi"));
        Model modelBmw = new Model("x5", new Brand("Bmw"));
        List<Model> listOfModels = new ArrayList<>();
        listOfModels.add(modelAudi);
        listOfModels.add(modelBmw);
        //When
        List<ModelDto> listOfModelsDto = modelMapper.mapToModelDtoList(listOfModels);
        //Then
        Assert.assertEquals(listOfModels.size(), listOfModelsDto.size());
        Assert.assertEquals(listOfModels.get(0).getName(), listOfModelsDto.get(0).getName());
    }
}