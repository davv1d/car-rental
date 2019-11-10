package com.davv1d.externalApi.weather;

import com.davv1d.domain.weather.CityForecast;
import com.davv1d.domain.weather.CityWeathers;
import com.davv1d.domain.weather.WeatherDto;
import com.davv1d.mapper.weather.CityWeatherConverter;
import com.davv1d.mapper.weather.WeatherMapper;
import com.davv1d.service.EmptyValuesClassCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WeatherService {
    @Autowired
    private WeatherClient weatherClient;

    @Autowired
    private CityWeatherConverter cityWeatherConverter;

    @Autowired
    private WeatherMapper weatherMapper;

    public WeatherDto getCityWeather(String city) {
        Optional<CityWeathers> optionalCityWeathers = weatherClient.getCityCondition(city, WeatherClient.PATH_WEATHER, CityWeathers.class);
        if (optionalCityWeathers.isPresent()) {
            return weatherMapper.mapToWeatherDto(cityWeatherConverter.convertTempAndWindInCityWeathers(optionalCityWeathers.get()));
        } else {
            return EmptyValuesClassCreator.emptyWeatherDto();
        }
    }

    public List<WeatherDto> getCityForecast(String city) {
        Optional<CityForecast> optionalCityForecast = weatherClient.getCityCondition(city, WeatherClient.PATH_FORECAST, CityForecast.class);
        if (optionalCityForecast.isPresent()) {
            return weatherMapper.mapToListWeatherDto(cityWeatherConverter.convertTempAndWindInCityForecast(optionalCityForecast.get()));
        } else {
            return new ArrayList<>();
        }
    }
}
