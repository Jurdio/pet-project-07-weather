package edu.weather.domain.repository;

import edu.weather.domain.model.entity.User;
import edu.weather.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;



@Slf4j
public class UserRepository implements CrudRepository<User, Integer> {

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
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e){
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Error while opening Hibernate session for saving user", e);
            throw e;
        }
        return entity;
    }


    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(Integer id) {

    }
}
