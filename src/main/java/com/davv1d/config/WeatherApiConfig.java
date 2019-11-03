package com.davv1d.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class WeatherApiConfig {
    @Value("${weather.api.endpoint.prod}")
    private String weatherApiEndpoint;

    @Value("${weather.api.key}")
    private String weatherApiKey;
}
