package com.davv1d.domain.weather;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WeatherDto {
    private String date;
    private String percentageCloudy;
    private BigDecimal temp;
    private BigDecimal tempMin;
    private String humidity;
    private String pressure;
    private BigDecimal tempMax;
    private String description;
    private String deg;
    private String speed;
}
