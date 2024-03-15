package edu.weather.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class City {
    @NonNull
    @JsonProperty("city")
    private String name;
    private double latitude;
    private double longitude;
    private boolean success;
}
