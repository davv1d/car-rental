package com.davv1d.repository;

import com.davv1d.domain.car.Brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BrandRepository extends CrudRepository<Brand, Long> {
    @SuppressWarnings("unchecked")
    @Override
    Brand save(Brand brand);

    @Override
    Optional<Brand> findById(Long id);

    @Override
    List<Brand> findAll();

    Optional<Brand> findByName(String name);
}
