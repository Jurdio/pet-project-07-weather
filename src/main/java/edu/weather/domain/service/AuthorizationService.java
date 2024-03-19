package edu.weather.domain.service;

import edu.weather.controller.dto.UserDTO;
import edu.weather.domain.model.Session;
import edu.weather.domain.model.User;
import edu.weather.exception.AuthorizationException;
import edu.weather.exception.RegistrationException;

public class AuthorizationService {
    private final UserService userService = new UserService();
    private final SessionService sessionService = new SessionService();
    private final BCryptPasswordHashingService bCryptPasswordHashingService = new BCryptPasswordHashingService();

    public Session authorizationAndReturnSessionOrElseThrow(UserDTO userDTO) {
        User user = userService.getUserByLogin(userDTO.getLogin())
                .orElseThrow(() -> new AuthorizationException("Login or password invalid"));
        if (!bCryptPasswordHashingService.checkPassword(userDTO.getPassword(), user.getPassword())) {
            throw new AuthorizationException("Login or password invalid");
        }

        return sessionService.createSession(user.getId());
    }


}
