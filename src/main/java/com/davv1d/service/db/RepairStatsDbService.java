package com.davv1d.service.db;

import com.davv1d.domain.car.RepairStats;
import com.davv1d.repository.RepairStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairStatsDbService {
    @Autowired
    private RepairStatsRepository repairStatsRepository;

    public void save(final RepairStats repairStats) {
        repairStatsRepository.save(repairStats);
    }

    public List<RepairStats> getRepairStatsByVin(final String vinNumber) {
        return repairStatsRepository.findByVinNumber(vinNumber);
    }
}
