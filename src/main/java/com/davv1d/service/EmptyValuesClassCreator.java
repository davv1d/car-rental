package com.davv1d.service;

import com.davv1d.domain.car.dto.CarDto;
import com.davv1d.domain.user.UserDto;
import com.davv1d.domain.weather.WeatherDto;

import java.math.BigDecimal;

public class EmptyValuesClassCreator {
    public static WeatherDto emptyWeatherDto() {
        return new WeatherDto(
                "",
                "",
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                "",
                "",
                BigDecimal.ZERO,
                "",
                "",
                "");
    }

    public static CarDto emptyCarDto() {
        return new CarDto(
                "",
                "",
                "",
                false
        );
    }

    public static UserDto emptyUserDto() {
        return new UserDto(
                "",
                "",
                ""
        );
    }
}
