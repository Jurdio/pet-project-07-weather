package edu.weather.domain.repository;


import edu.weather.domain.model.entity.Session;
import edu.weather.exception.DataAccessException;
import edu.weather.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class SessionRepository implements CrudRepository<Session, UUID> {

    @Override
    public Optional<Session> findById(UUID id) {
        try (org.hibernate.Session openSession = HibernateUtil.getSessionFactory().openSession()) {
            Session session = openSession.get(Session.class, id);
            return Optional.ofNullable(session);
        } catch (Exception e) {
            log.error("Error while findById", e);
            throw new DataAccessException("Error while retrieving session by ID");
        }
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
