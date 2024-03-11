package edu.weather.domain.service;

import edu.weather.controller.dto.UserDTO;
import edu.weather.domain.model.Session;
import edu.weather.domain.model.User;

public class AuthorizationService {
    private final UserService userService = new UserService();
    private final SessionService sessionService = new SessionService();

    public Session getAuthorizationByLoginAndThenReturnSession(UserDTO userDTO){
        User user = userService.getUserByLogin(userDTO.getLogin());
        if (user.getPassword().equals(userDTO.getPassword())){
            return sessionService.createSession(user.getId());

        }
        return null;
    }

}
