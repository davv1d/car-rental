package com.davv1d.scheduler;

import com.davv1d.config.CarRentalAdminConfig;
import com.davv1d.domain.Mail;
import com.davv1d.repository.CarRepository;
import com.davv1d.service.mail.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class EmailScheduler {
    private final String SUBJECT = "Information about car";

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private CarRentalAdminConfig adminConfig;

    @Autowired
    private CarRepository carRepository;

    @Scheduled(cron = "0 0 8 * * MON")
    public void sendInformationEmail() {
        long numberOfCars = carRepository.count();
        long numberOfCarsAvailability = carRepository.countByAvailability(true);
        long numberOfCarsInRepair = carRepository.countByAvailability(false);
        double percentageOfCarsInUse = calculateThePercentage(numberOfCarsAvailability, numberOfCars).doubleValue();
        simpleEmailService.send(new Mail(
                adminConfig.getAdminMail(),
                SUBJECT,
                "Number of cars " + numberOfCars + "\n" +
                        "Number of availability cars " + numberOfCarsAvailability + "\n" +
                        "Number of cars in repair " + numberOfCarsInRepair + "\n" +
                        "Percentage of cars in use " + percentageOfCarsInUse + "%"
        ));
    }

    private BigDecimal calculateThePercentage(long nominator, long denominator) {
        BigDecimal nom = BigDecimal.valueOf(nominator);
        BigDecimal den = BigDecimal.valueOf(denominator);
        if (!den.equals(BigDecimal.ZERO)) {
            return nom.divide(den, 4,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        } else {
            return BigDecimal.ZERO;
        }
    }
}
