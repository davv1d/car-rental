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
public class CityWeathers {
    @JsonProperty("main")
    private MainWeatherProp mainWeatherProp;
    @JsonProperty("clouds")
    private Clouds clouds;
    @JsonProperty("weather")
    private List<Weather> weather;
    @JsonProperty("name")
    private String name;
    @JsonProperty("wind")
    private Wind wind;
    @JsonProperty("dt")
    private String date;
    @JsonProperty("cod")
    private String cod;
}
