package com.davv1d.service.weather.empty;

import com.davv1d.domain.weather.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;

@Component
public class EmptyWeathersClass {

    public CityForecast getEmptyCityForecast() {
        return new CityForecast("", "", Collections.emptyList());
    }

    public CityWeathers getEmptyCityWeathers() {
        return new CityWeathers(getMainWeatherProp(), getEmptyClouds(), Collections.emptyList(), "", getEmptyWind(),"", "");
    }

    public Clouds getEmptyClouds() {
        return new Clouds("");
    }

    public MainWeatherProp getMainWeatherProp() {
        return new MainWeatherProp(BigDecimal.ZERO, BigDecimal.ZERO, "", "", BigDecimal.ZERO);
    }

    public Wind getEmptyWind() {
        return new Wind("", "");
    }
}
