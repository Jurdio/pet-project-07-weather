package edu.weather.domain.service;

import edu.weather.domain.model.entity.Session;
import edu.weather.domain.repository.SessionRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class SessionService {
    private final SessionRepository sessionRepository = new SessionRepository();
    private final UserService userService = new UserService();
    public Session createSession(int userId){
        return sessionRepository.save(Session.builder()
                .userId(userId)
                .expiredAt(LocalDateTime.now().plusHours(1))
                .build());
    }
    public Session getSessionById(UUID sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow();
    }
    public void deleteSessionById(UUID sessionId){
        sessionRepository.delete(sessionId);
    }


}
