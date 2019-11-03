package com.davv1d.domain.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityForecast {
    @JsonProperty("cod")
    private String cod;
    @JsonProperty("message")
    private String message;
    @JsonProperty("list")
    private List<CityWeathers> cityWeathers;
}