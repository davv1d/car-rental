package com.davv1d.controller;

import com.davv1d.domain.weather.WeatherDto;
import com.davv1d.externalApi.weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/condition")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
    public WeatherDto getCityWeather(@RequestParam String city) {
        return weatherService.getCityWeather(city);
    }

    @GetMapping("/forecast")
    public List<WeatherDto> getCityForecast(@RequestParam String city) {
        return weatherService.getCityForecast(city);
    }
}
