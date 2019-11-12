package com.davv1d.mapper.car;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.dto.BrandDto;
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
public class BrandMapperTestSuite {
    @Autowired
    private BrandMapper brandMapper;

    @Test
    public void shouldReturnBrandDto() {
        //Given
        Brand brand = new Brand("BMW");
        //When
        BrandDto brandDto = brandMapper.mapToBrandDto(brand);
        //Then
        Assert.assertEquals(brand.getName(), brandDto.getName());
    }

    @Test
    public void shouldReturnBrandDtoList() {
        //Given
        Brand brandBmw = new Brand("BMW");
        Brand brandAudi = new Brand("Audi");
        List<Brand> listOfBrands = new ArrayList<>();
        listOfBrands.add(brandAudi);
        listOfBrands.add(brandBmw);
        //When
        List<BrandDto> listOfBrandsDto = brandMapper.mapToBrandDtoList(listOfBrands);
        //Then
        Assert.assertEquals(listOfBrands.size(), listOfBrandsDto.size());
        Assert.assertEquals(listOfBrands.get(0).getName(), listOfBrandsDto.get(0).getName());
    }
}