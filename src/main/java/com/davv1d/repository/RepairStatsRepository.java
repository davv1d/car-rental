package com.davv1d.repository;

import com.davv1d.domain.car.RepairStats;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface RepairStatsRepository extends CrudRepository<RepairStats, Long> {
    @Override
    RepairStats save(RepairStats repairStats);

    List<RepairStats> findByVinNumber(String vinNumber);
}
