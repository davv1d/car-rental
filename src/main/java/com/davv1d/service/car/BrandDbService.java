package com.davv1d.service.car;

import com.davv1d.domain.car.Brand;
import com.davv1d.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandDbService {
    @Autowired
    private BrandRepository brandRepository;

    public Brand saveBrandIfItDoesNotExist(final String name) {
        return fetchByName(name)
                .orElseGet(() -> save(name));
    }

    private Brand save(String name) {
        return brandRepository.save(new Brand(name));
    }

    private Optional<Brand> fetchByName(String name) {
        return brandRepository.findByName(name.toUpperCase());
    }

    public List<Brand> fetchAllBrands() {
        return brandRepository.findAll();
    }

    public void deleteBrand(String brandName) {
        brandRepository.deleteByName(brandName.toUpperCase());
    }
}
