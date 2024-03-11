package edu.weather.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDTO {
    private String name;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private List<DayDTO> days;
    private String description;
}
