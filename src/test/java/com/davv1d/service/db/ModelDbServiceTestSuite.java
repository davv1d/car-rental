package com.davv1d.service.db;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.Model;
import com.davv1d.repository.BrandRepository;
import com.davv1d.repository.ModelRepository;
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
public class ModelDbServiceTestSuite {
    @Autowired
    private ModelDbService modelDbService;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Test
    public void testDoesNotSaveModel() {
        //Given
        Brand testBrand = new Brand("test brand");
        brandRepository.save(testBrand);
        Model testModel = new Model("test model", testBrand);
        modelRepository.save(testModel);
        //When
        modelDbService.saveModelIfItDoesNotExist("test model", "test brand");
        //Then
        List<Model> models = modelRepository.findAll();
        List<Model> testModels = models.stream()
                .filter(model -> model.getName().equalsIgnoreCase("test model"))
                .collect(Collectors.toList());
        assertEquals(1, testModels.size());
        //Clean up
        brandRepository.deleteByName(testBrand.getName());
    }

    @Test
    public void testSaveModel() {
        //Given
        Brand testBrand = new Brand("test brand");
        brandRepository.save(testBrand);
        //When
        modelDbService.saveModelIfItDoesNotExist("test model", "test brand");
        //Then
        List<Model> models = modelRepository.findAll();
        List<Model> testModels = models.stream()
                .filter(model -> model.getName().equalsIgnoreCase("test model"))
                .collect(Collectors.toList());
        assertEquals(1, testModels.size());
        //Clean up
        brandRepository.deleteByName(testBrand.getName());
    }
    
    @Test
    public void shouldDeleteModel() {
        //Given
        Brand testBrand = new Brand("test brand");
        brandRepository.save(testBrand);
        Model testModel = new Model("test model", testBrand);
        modelRepository.save(testModel);
        Optional<Model> beforeDelete = modelRepository.findByName("test model");
        //When
        boolean result = modelDbService.deleteModelByName("test model");
        //Then
        Optional<Model> afterDelete = modelRepository.findByName("test model");
        Optional<Brand> optionalBrand = brandRepository.findByName("test brand");
        assertTrue(beforeDelete.isPresent());
        assertTrue(result);
        assertFalse(afterDelete.isPresent());
        assertTrue(optionalBrand.isPresent());
        //Clean up
        brandRepository.deleteByName(testBrand.getName());
    }
}