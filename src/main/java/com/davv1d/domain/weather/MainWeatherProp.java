package com.davv1d.domain.weather;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MainWeatherProp {
    @JsonProperty("temp")
    private BigDecimal temp;
    @JsonProperty("temp_min")
    private BigDecimal tempMin;
    @JsonProperty("humidity")
    private String humidity;
    @JsonProperty("pressure")
    private String pressure;
    @JsonProperty("temp_max")
    private BigDecimal tempMax;
}
