package edu.weather.domain.repository;

import edu.weather.domain.model.entity.User;
import edu.weather.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.hibernate.cfg.AvailableSettings.URL;


public class UserRepository implements CrudRepository<User, Integer> {
    private final String URL = "jdbc:mariadb://localhost:3306";
    private final String USERNAME = "root";
    private final String PASSWORD = "maria";

    @Override
    public Optional<User> findById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User save(User entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // почати транзакцію
            transaction = session.beginTransaction();

            // зберегти користувача
            session.save(entity);

            // закінчити транзакцію
            transaction.commit();

            return entity;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}
