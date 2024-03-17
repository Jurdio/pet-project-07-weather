package edu.weather.domain.repository;

import edu.weather.domain.model.Location;
import edu.weather.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

@Slf4j
public class LocationRepository implements CrudRepository<Location, Integer> {

    @Override
    public Optional<Location> findById(Integer id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Location location = session.get(Location.class, id);
            return Optional.ofNullable(location);
        } catch (Exception e) {
            log.error("Error while finding location by id", e);
            throw e;
        }
    }
    public List<Location> findAllByUserId(int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Використовуємо HQL (Hibernate Query Language) для складання запиту
            String hql = "FROM Location WHERE userId = :userId";
            Query<Location> query = session.createQuery(hql, Location.class);
            query.setParameter("userId", userId);
            // Виконуємо запит і повертаємо результат у вигляді списку локацій
            return query.list();
        } catch (Exception e) {
            // У випадку помилки виводимо повідомлення у лог
            log.error("Error while finding locations by userId", e);
            throw e;
        }
    }

    @Override
    public List<Location> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Location";
            Query<Location> query = session.createQuery(hql, Location.class);
            return query.list();
        } catch (Exception e) {
            log.error("Error while finding all locations", e);
            throw e;
        }
    }

    @Override
    public Location save(Location entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Error while saving location", e);
            throw e;
        }
        return entity;
    }

    @Override
    public void update(Location entity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Error while updating location", e);
            throw e;
        }
    }

    @Override
    public void delete(Integer id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Location location = session.get(Location.class, id);
            if (location != null) {
                session.delete(location);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            log.error("Error while deleting location", e);
            throw e;
        }
    }
}
