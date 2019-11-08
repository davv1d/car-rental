package com.davv1d.mapper.weather;

import com.davv1d.domain.weather.CityForecast;
import com.davv1d.domain.weather.CityWeathers;
import com.davv1d.domain.weather.Weather;
import com.davv1d.domain.weather.WeatherDto;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WeatherMapper {
    public WeatherDto mapToToWeatherDto(CityWeathers cityWeathers) {
        return new WeatherDto(
                new Date(Long.parseLong(cityWeathers.getDate()) * 1000).toString(),
                cityWeathers.getClouds().getPercentageCloudy(),
                cityWeathers.getMainWeatherProp().getTemp(),
                cityWeathers.getMainWeatherProp().getTempMin(),
                cityWeathers.getMainWeatherProp().getHumidity(),
                cityWeathers.getMainWeatherProp().getPressure(),
                cityWeathers.getMainWeatherProp().getTempMax(),
                mapToStringList(cityWeathers.getWeather()),
                cityWeathers.getWind().getDeg(),
                cityWeathers.getWind().getSpeed()
        );
    }

    private String mapToStringList(List<Weather> weathers) {
        return weathers.stream()
                .map(Weather::getDescription)
                .collect(Collectors.toList()).get(0);
    }

    public List<WeatherDto> mapToListWeatherDto(CityForecast cityForecast) {
        return cityForecast.getCityWeathers().stream()
                .flatMap(cityWeathers -> cityForecast.getCityWeathers().stream())
                .map(this::mapToToWeatherDto)
                .collect(Collectors.toList());
    }
}
