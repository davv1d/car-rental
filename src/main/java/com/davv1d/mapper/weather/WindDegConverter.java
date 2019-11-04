package com.davv1d.mapper.weather;

import com.davv1d.domain.weather.Wind;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class WindDegConverter {
    private final List<String> directions = Arrays.asList("N","NNE","NE","ENE","E","ESE", "SE", "SSE","S","SSW","SW","WSW","W","WNW","NW","NNW");
    
    public Wind mapWinDegreeToDirection(Wind wind) {
        double degree = Double.parseDouble(wind.getDeg());
        int number = (int)((degree / 22.5) + 0.5);
        return new Wind(directions.get(number % 16), wind.getSpeed());
    }
}
