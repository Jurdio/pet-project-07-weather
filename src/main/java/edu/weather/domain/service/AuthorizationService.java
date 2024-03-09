package edu.weather.domain.service;

import edu.weather.domain.model.dto.UserDTO;
import edu.weather.domain.model.entity.Session;
import edu.weather.domain.model.entity.User;

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
