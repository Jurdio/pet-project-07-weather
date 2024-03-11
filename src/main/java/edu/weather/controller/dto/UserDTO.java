package edu.weather.controller.dto;

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
