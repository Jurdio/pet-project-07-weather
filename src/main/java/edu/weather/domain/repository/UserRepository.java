package edu.weather.domain.repository;



import edu.weather.domain.model.entity.User;
import edu.weather.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


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

    public User findByLogin(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM User WHERE login = :login";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("login", login);

            return query.uniqueResult();
        } catch (Exception e) {
            log.error("Error while finding user by login", e);
            throw e;
        }
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User save(User entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.save(entity);
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
