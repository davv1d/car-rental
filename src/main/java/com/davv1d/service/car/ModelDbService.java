package com.davv1d.service.car;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.Model;
import com.davv1d.repository.BrandRepository;
import com.davv1d.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModelDbService {
    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private BrandDbService brandDbService;

    public Model saveModelIfItDoesNotExist(final String modelName, final String brandName) {
        return fetchByName(modelName)
                .orElseGet(() -> save(modelName, brandName));
    }

    private Model save(String modelName, String brandName) {
        Brand brand = brandDbService.saveBrandIfItDoesNotExist(brandName);
        return modelRepository.save(new Model(modelName, brand));
    }

    private Optional<Model> fetchByName(String name) {
        return modelRepository.findByName(name.toUpperCase());
    }

    public void deleteByName(String modelName) {
        fetchByName(modelName)
                .ifPresent(model -> {
                    model.getBrand().getModels().remove(model);
                    Model updatedModel = new Model(model.getId(), model.getName(), null, model.getCars());
                    modelRepository.save(updatedModel);
                    modelRepository.deleteByName(updatedModel.getName());
                });
    }
}
