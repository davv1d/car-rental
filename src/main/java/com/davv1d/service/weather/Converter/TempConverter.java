package com.davv1d.service.weather.Converter;

import com.davv1d.domain.weather.MainWeatherProp;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TempConverter {

    public MainWeatherProp mapToMainWeatherInCelsius(final MainWeatherProp mainWeatherProp) {
        return new MainWeatherProp(
                replaceKelvinToCelsius(mainWeatherProp.getTemp()),
                replaceKelvinToCelsius(mainWeatherProp.getTempMin()),
                mainWeatherProp.getHumidity(),
                mainWeatherProp.getPressure(),
                replaceKelvinToCelsius(mainWeatherProp.getTempMax())
        );
    }

    private BigDecimal replaceKelvinToCelsius(BigDecimal temp) {
        return temp.subtract(BigDecimal.valueOf(273.15));
    }
}
