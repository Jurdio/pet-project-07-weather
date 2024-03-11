package edu.weather.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DayDTO {
    private LocalDate datetime;
    private String pathToImage;
    private int tempmax;
    private int tempmin;
    private int feelslike;
}
