package com.davv1d.service.car;

import com.davv1d.domain.car.Brand;
import com.davv1d.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BrandDbService {
    @Autowired
    private BrandRepository brandRepository;

    public Brand saveCarIfItDoesNotExist(final Brand brand) {
        return fetchByName(brand.getName())
                .orElseGet(() -> save(brand));
    }

    private Brand save(Brand brand) {
        return null;
    }

    private Optional<Brand> fetchByName(String name) {
        return brandRepository.findByName(name.toUpperCase());
    }

}
