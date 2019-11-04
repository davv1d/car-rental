package com.davv1d.controller;

import com.davv1d.domain.weather.CityWeathers;
import com.davv1d.service.weather.Converter.CityWeatherConverter;
import com.davv1d.domain.weather.CityForecast;
import com.davv1d.service.weather.empty.EmptyWeathersClass;
import com.davv1d.externalApi.weather.WeatherClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/condition")
public class WeatherController {
    @Autowired
    private WeatherClient weatherClient;

    @Autowired
    private CityWeatherConverter cityWeatherConverter;

    @Autowired
    private EmptyWeathersClass emptyWeathersClass;

    @GetMapping("/weather/{city}")
    public CityWeathers getCityWeather(@PathVariable String city) {
        Optional<CityWeathers> optionalCityWeathers = weatherClient.getCityCondition(city, WeatherClient.PATH_WEATHER, CityWeathers.class);
        if (optionalCityWeathers.isPresent()) {
            return cityWeatherConverter.convertTempAndWindInCityWeathers(optionalCityWeathers.get());
        } else {
            return emptyWeathersClass.getEmptyCityWeathers();
        }
    }

    @GetMapping("/forecast/{city}")
    public CityForecast getCityForecast(@PathVariable String city) {
        Optional<CityForecast> optionalCityForecast = weatherClient.getCityCondition(city, WeatherClient.PATH_FORECAST, CityForecast.class);
        if (optionalCityForecast.isPresent()) {
            return cityWeatherConverter.convertTempAndWindInCityForecast(optionalCityForecast.get());
        } else {
            return emptyWeathersClass.getEmptyCityForecast();
        }
    }

}
