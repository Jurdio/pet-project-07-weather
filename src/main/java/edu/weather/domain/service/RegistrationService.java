package edu.weather.domain.service;

import edu.weather.controller.dto.UserDTO;
import edu.weather.exception.RegistrationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegistrationService {
    private final UserService userService = new UserService();
    private final ValidationService validationService = new ValidationService();

    public int registerUserAndReturnIdOrElseThrow(UserDTO userDTO, String confirmedPassword) {
        try {
            if (!validationService.isValidLogin(userDTO.getLogin())) {
                throw new RegistrationException("Invalid login");
            }
            if (userService.getUserByLogin(userDTO.getLogin()).isPresent()) {
                throw new RegistrationException("User with this login already exists");
            }
            if (!validationService.isValidPassword(userDTO.getPassword())) {
                throw new RegistrationException("Invalid password. Password must meet the following requirements: " +
                        "Minimum length: 3 characters, Maximum length: 25 characters, " +
                        "At least one uppercase letter (A-Z), At least one digit (0-9), " +
                        "At least one special character (@!#$%^&*()[]{};:,.<>?");
            }
            if (!userDTO.getPassword().equals(confirmedPassword)) {
                throw new RegistrationException("Confirmed password does not match the entered one");
            }
            return userService.createUser(userDTO).getId();
        } catch (RegistrationException e) {
            log.error("Registration error: {}", e.getMessage(), e);
            throw e;
        }
    }
}
