package edu.weather.domain.repository;



import edu.weather.domain.model.User;
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
            return Optional.of(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<User> findByLogin(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM User WHERE login = :login";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("login", login);

            return Optional.ofNullable(query.uniqueResult());
        } catch (Exception e) {
            log.error("Error while finding user by login", e);

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
            session.persist(entity);
            transaction.commit();
        } catch (Exception e){
            if (transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            log.error("Errror", e);
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
