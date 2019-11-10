package com.davv1d.repository;

import com.davv1d.domain.car.Brand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BrandRepositoryTestSuite {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void shouldSaveBrand() {
        //Given
        Brand testBrand = new Brand("test brand");
        //When
        brandRepository.save(testBrand);
        //Then
        assertNotNull(testBrand.getId());
        //Clean up
        brandRepository.deleteByName(testBrand.getName());
    }

    @Test
    public void shouldFindBrandById() {
        //Given
        Brand testBrand = new Brand("test brand");
        brandRepository.save(testBrand);
        //When
        Optional<Brand> optionalBrand = brandRepository.findById(testBrand.getId());
        //Then
        assertTrue(optionalBrand.isPresent());
        //Clean up
        brandRepository.deleteByName(testBrand.getName());
    }

    @Test
    public void shouldFindAllBrand() {
        //Given
        Brand testBrand1 = new Brand("test brand 1");
        Brand testBrand2 = new Brand("test brand 2");
        brandRepository.save(testBrand1);
        brandRepository.save(testBrand2);
        //When
        List<Brand> brands = brandRepository.findAll();
        //Then
        assertEquals(2, brands.size());
        //Clean up
        brandRepository.deleteByName(testBrand1.getName());
        brandRepository.deleteByName(testBrand2.getName());
    }

    @Test
    public void shouldDeleteBrandByName() {
        //Given
        Brand testBrand1 = new Brand("test brand 1");
        Long id = brandRepository.save(testBrand1).getId();
        //When
        brandRepository.deleteByName(testBrand1.getName());
        //Then
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        assertFalse(optionalBrand.isPresent());
    }

    @Test
    public void shouldFindBrandByName() {
        //Given
        Brand testBrand = new Brand("test brand");
        brandRepository.save(testBrand);
        //When
        Optional<Brand> optionalBrand = brandRepository.findByName(testBrand.getName());
        //Then
        assertTrue(optionalBrand.isPresent());
        //Clean up
        brandRepository.deleteByName(testBrand.getName());
    }
}