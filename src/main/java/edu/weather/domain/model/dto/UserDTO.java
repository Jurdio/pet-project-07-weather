package edu.weather.domain.model.dto;

import jakarta.annotation.Nonnull;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @Nonnull
    private String login;
    private String password;
}
