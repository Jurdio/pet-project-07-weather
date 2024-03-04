package edu.weather.domain.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, S> {
    Optional<T> findById(S id);
    List<T> findAll();
    T save(T entity);
    void update(T entity);
    void delete(S id);
}