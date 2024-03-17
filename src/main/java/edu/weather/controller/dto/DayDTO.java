package edu.weather.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import edu.weather.util.LocalDateDeserializer;
import edu.weather.util.LocalDateTimeDeserializer;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DayDTO {
    @JsonProperty("dt")
    @JsonDeserialize(using = LocalDateDeserializer.class) // Вказуємо Jackson використовувати десеріалізатор
    private LocalDate datetime;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime sunrise;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime sunset;
    @JsonProperty("temp")
    private Temperature temperature;
    @JsonProperty("feels_like")
    private TemperatureFeelsLike feelsLike;
    private int pressure;
    private int humidity;
    @JsonProperty("weather")
    private List<Weather> weatherList;
    private float speed;
    private float pop;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Temperature {
        private int day;
        private int min;
        private int max;
        private int night;
        private int eve;
        private int morn;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TemperatureFeelsLike {
        private int day;
        private int night;
        private int eve;
        private int morn;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private int id;
        private String description;
        private String icon;
    }


}
