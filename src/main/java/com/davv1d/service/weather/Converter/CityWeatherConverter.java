package com.davv1d.service.weather.Converter;

import com.davv1d.domain.weather.CityForecast;
import com.davv1d.domain.weather.CityWeathers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CityWeatherConverter {
    @Autowired
    private WindDegConverter windDegConverter;

    @Autowired
    private TempConverter tempConverter;

    public CityWeathers convertTempAndWindInCityWeathers(final CityWeathers cityWeathers) {
        return new CityWeathers(
                tempConverter.mapToMainWeatherInCelsius(cityWeathers.getMainWeatherProp()),
                cityWeathers.getClouds(),
                cityWeathers.getWeather(),
                cityWeathers.getName(),
                windDegConverter.mapWinDegreeToDirection(cityWeathers.getWind()),
                cityWeathers.getDate(),
                cityWeathers.getCod());
    }

    public CityForecast convertTempAndWindInCityForecast(final CityForecast cityForecast) {
        return new CityForecast(cityForecast.getCod(), cityForecast.getMessage(), convertTempAndWindInCityWeathersList(cityForecast.getCityWeathers()));
    }

    private List<CityWeathers> convertTempAndWindInCityWeathersList(final List<CityWeathers> cityWeathersList) {
        return cityWeathersList.stream()
                .map(this::convertTempAndWindInCityWeathers)
                .collect(Collectors.toList());
    }
}
