package com.davv1d.repository;

import com.davv1d.domain.car.Model;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ModelRepository extends CrudRepository<Model, Long> {
    @SuppressWarnings("unchecked")
    @Override
    Model save(Model model);

    @Override
    Optional<Model> findById(Long modelId);

    @Override
    List<Model> findAll();

    Optional<Model> findByName(String name);

    void deleteByName(String name);
}
