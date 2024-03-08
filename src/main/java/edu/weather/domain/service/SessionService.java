package edu.weather.domain.service;

import edu.weather.domain.model.entity.Session;
import edu.weather.domain.repository.SessionRepository;

import java.time.LocalDateTime;

public class SessionService {
    private final SessionRepository sessionRepository = new SessionRepository();
    private final UserService userService = new UserService();
    public Session createSession(int userId){
        return sessionRepository.save(Session.builder()
                .userId(userId)
                .expiredAt(LocalDateTime.now().plusHours(1))
                .build());
    }


}
