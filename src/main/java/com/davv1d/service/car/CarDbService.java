package com.davv1d.service.car;

import com.davv1d.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarDbService {
    @Autowired
    private CarRepository carRepository;
}
