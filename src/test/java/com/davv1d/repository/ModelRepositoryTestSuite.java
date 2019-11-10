package com.davv1d.repository;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.Model;
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
public class ModelRepositoryTestSuite {
    private Brand brand = new Brand("test brand");

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Test
    public void shouldSaveModel() {
        //Given
        Brand savedBrand = brandRepository.save(brand);
        Model testModel = new Model("test model", brand);
        //When
        modelRepository.save(testModel);
        //Then
        Optional<Model> optionalModel = modelRepository.findById(testModel.getId());
        assertTrue(optionalModel.isPresent());
        assertEquals(brand.getName(), optionalModel.get().getBrand().getName());
        assertTrue(optionalModel.get().getCars().isEmpty());
        //Clean up
        brandRepository.deleteByName(savedBrand.getName());
    }

    @Test
    public void shouldFindModelById() {
        //Given
        Brand savedBrand = brandRepository.save(brand);
        Model testModel = new Model("test model", brand);
        modelRepository.save(testModel);
        //When
        Optional<Model> optionalModel = modelRepository.findById(testModel.getId());
        //Then
        assertTrue(optionalModel.isPresent());
        //Clean up
        brandRepository.deleteByName(savedBrand.getName());
    }

    @Test
    public void shouldFindModelByName() {
        //Given
        Brand savedBrand = brandRepository.save(brand);
        Model testModel = new Model("test model", brand);
        modelRepository.save(testModel);
        //When
        Optional<Model> optionalModel = modelRepository.findByName(testModel.getName());
        //Then
        assertTrue(optionalModel.isPresent());
        //Clean up
        brandRepository.deleteByName(savedBrand.getName());
    }

    @Test
    public void shouldFindAll() {
        //Given
        Brand savedBrand = brandRepository.save(brand);
        Model testModel1 = new Model("test model 1", brand);
        Model testModel2 = new Model("test model 2", brand);
        modelRepository.save(testModel1);
        modelRepository.save(testModel2);
        //When
        List<Model> models = modelRepository.findAll();
        //Then
        assertEquals(2, models.size());
        //Clean up
        brandRepository.deleteByName(savedBrand.getName());
    }

    @Test
    public void shouldDeleteModelByName() {
        //Given
        Brand savedBrand = brandRepository.save(brand);
        Model testModel1 = new Model("test model 1", brand);
        modelRepository.save(testModel1);
        //When
        modelRepository.deleteByName(testModel1.getName());
        //Then
        Optional<Model> optionalModel = modelRepository.findById(testModel1.getId());
        assertFalse(optionalModel.isPresent());
        //Clean up
        brandRepository.deleteByName(savedBrand.getName());
    }
}