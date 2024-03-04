package edu.weather.domain.repository;

import edu.weather.domain.model.entity.Session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SessionRepository implements CrudRepository<Session, UUID>{

    @Override
    public Optional<Session> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Session> findAll() {
        return null;
    }

    @Override
    public Session save(Session entity) {
        return null;
    }

    @Override
    public void update(Session entity) {

    }

    @Override
    public void delete(UUID id) {

    }
}
