package com.davv1d.repository;

import com.davv1d.domain.car.RepairStats;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RepairStatsRepositoryTestSuite {

    @Autowired
    private RepairStatsRepository repairStatsRepository;

    @Test
    public void shouldSave() {
        //Given
        LocalDateTime changeDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        RepairStats repairStats = new RepairStats("test username", "VIN TEST", changeDate, false);
        //When
        repairStatsRepository.save(repairStats);
        //Then
        Optional<RepairStats> optionalRepairStats = repairStatsRepository.findById(repairStats.getId());
        assertTrue(optionalRepairStats.isPresent());
        RepairStats repairStats1 = optionalRepairStats.get();
        assertEquals("test username", repairStats1.getUsername());
        assertEquals("VIN TEST", repairStats1.getVinNumber());
        assertEquals(changeDate, repairStats1.getDate());
        assertFalse(repairStats.isAvailability());
        //Clean up
        repairStatsRepository.deleteById(repairStats.getId());
    }

    @Test
    public void shouldFindByVinNUmber() {
        //Given
        LocalDateTime changeDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        RepairStats repairStats1 = new RepairStats("test username", "VIN TEST", changeDate, false);
        RepairStats repairStats2 = new RepairStats("test username", "VIN TEST", changeDate, true);
        repairStatsRepository.save(repairStats1);
        repairStatsRepository.save(repairStats2);
        //When
        List<RepairStats> repairStatsList = repairStatsRepository.findByVinNumber("VIN TEST");
        //Then
        assertEquals(2, repairStatsList.size());
        //Clean up
        for (RepairStats stats : repairStatsList) {
            repairStatsRepository.deleteById(stats.getId());
        }
    }

}