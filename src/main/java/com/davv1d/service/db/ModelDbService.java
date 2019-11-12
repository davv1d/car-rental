package com.davv1d.service.db;

import com.davv1d.domain.car.Brand;
import com.davv1d.domain.car.Model;
import com.davv1d.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.davv1d.service.validate.ExistenceChecker.ifExists;

@Service
public class ModelDbService {
    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private BrandDbService brandDbService;

    public Model saveModelIfItDoesNotExist(final String modelName, final String brandName) {
        return ifExists(modelName, this::getByName)
                .getOrElse(() -> save(modelName, brandName));
    }

    private Model save(String modelName, String brandName) {
        Brand brand = brandDbService.saveBrandIfItDoesNotExist(brandName);
        return modelRepository.save(new Model(modelName, brand));
    }

    private Optional<Model> getByName(String name) {
        return modelRepository.findByName(name.toUpperCase());
    }

    public boolean deleteModelByName(String modelName) {
        return ifExists(modelName, this::getByName)
                .effect(this::deleteModel);
    }

    public void deleteModel(Model model) {
        Model updatedModel = new Model(model.getId(), model.getName(), null, model.getCars());
        modelRepository.save(updatedModel);
        modelRepository.deleteByName(updatedModel.getName());
    }
}
