package com.davv1d.externalApi.weather;

import com.davv1d.config.WeatherApiConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
public class WeatherClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherClient.class);
    public static final String PATH_FORECAST = "forecast";
    public static final String PATH_WEATHER = "weather";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherApiConfig weatherApiConfig;

    public <T> Optional<T> getCityCondition(String city, String path, Class<T> responseType) {
        URI uri = getUri(city, path);
        try {
            return Optional.ofNullable(restTemplate.getForObject(uri, responseType));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    private URI getUri(String city, String path) {
        return UriComponentsBuilder.fromHttpUrl(weatherApiConfig.getWeatherApiEndpoint() + path)
                .queryParam("q", city)
                .queryParam("appid", weatherApiConfig.getWeatherApiKey()).build().encode().toUri();
    }
}
