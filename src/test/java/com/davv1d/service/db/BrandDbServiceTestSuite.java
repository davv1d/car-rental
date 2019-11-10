package com.davv1d.service.db;

import com.davv1d.domain.car.Brand;
import com.davv1d.repository.BrandRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BrandDbServiceTestSuite {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandDbService brandDbService;

    @Test
    public void shouldDoesNotSaveBrand() {
        //Given
        Brand testBrand1 = new Brand("test");
        brandRepository.save(testBrand1);
        //When
        brandDbService.saveBrandIfItDoesNotExist("test");
        //Then
        List<Brand> test = brandRepository.findAll();
        List<Brand> testNameBrands = test.stream()
                .filter(brand -> brand.getName().equals("test".toUpperCase()))
                .collect(Collectors.toList());
        assertEquals(1, testNameBrands.size());

        //Clean up
        brandRepository.deleteByName("test");
    }

    @Test
    public void shouldSaveBrand() {
        //Given
        //When
        brandDbService.saveBrandIfItDoesNotExist("test");
        //Then
        List<Brand> test = brandRepository.findAll();
        List<Brand> testNameBrands = test.stream()
                .filter(brand -> brand.getName().equals("test".toUpperCase()))
                .collect(Collectors.toList());
        assertEquals(1, testNameBrands.size());
        //Clean up
        brandRepository.deleteByName("test");
    }
}