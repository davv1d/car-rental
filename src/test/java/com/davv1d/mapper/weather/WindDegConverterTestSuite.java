package com.davv1d.mapper.weather;

import com.davv1d.domain.weather.Wind;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WindDegConverterTestSuite {
    @Autowired
    private WindDegConverter windDegConverter;

    @Test
    public void shouldReturnWindDirection() {
        //Given
        Wind wind = new Wind("80", "50");
        //When
        Wind resultWind = windDegConverter.mapWinDegreeToDirection(wind);
        //Then
        assertEquals("E", resultWind.getDeg());
        assertEquals("50", resultWind.getSpeed());
    }

    @Test
    public void testWindDirectionWithFailureInputData() {
        //Given
        Wind wind = new Wind("task", "50");
        //When
        Wind resultWind = windDegConverter.mapWinDegreeToDirection(wind);
        //Then
        assertEquals("task", resultWind.getDeg());
        assertEquals("50", resultWind.getSpeed());

    }
}