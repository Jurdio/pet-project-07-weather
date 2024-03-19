package edu.weather.domain.repository;

import edu.weather.domain.model.Location;
import edu.weather.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    public Optional<Location> findByCoordinates(BigDecimal latitude, BigDecimal longitude, int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Заокруглення координат до двох знаків після коми
            latitude = latitude.setScale(2, RoundingMode.HALF_UP);
            longitude = longitude.setScale(2, RoundingMode.HALF_UP);

            String hql = "FROM Location WHERE latitude = :latitude AND longitude = :longitude AND userId = :userId";
            Query<Location> query = session.createQuery(hql, Location.class);
            query.setParameter("latitude", latitude);
            query.setParameter("longitude", longitude);
            query.setParameter("userId", userId);
            Location location = query.uniqueResult();
            return Optional.ofNullable(location);
        } catch (Exception e) {
            log.error("Error while finding location by coordinates and userId", e);
            throw e;
        }
    }

    public Optional<Location> findByName(String name, int userId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Використовуємо HQL для складання запиту
            String hql = "FROM Location WHERE name = :name AND userId = :userId";
            Query<Location> query = session.createQuery(hql, Location.class);
            query.setParameter("name", name);
            query.setParameter("userId", userId);
            // Виконуємо запит і отримуємо результат
            Location location = query.uniqueResult();
            return Optional.ofNullable(location);
        } catch (Exception e) {
            // У випадку помилки виводимо повідомлення у лог
            log.error("Error while finding location by name and userId", e);
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

    }

    public void deleteByUserId(Location location, Integer userId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Видаляємо записи за вказаними параметрами
            Query query = session.createQuery("DELETE FROM Location WHERE latitude = :latitude AND longitude = :longitude AND userId = :userId");
            query.setParameter("latitude", location.getLatitude());
            query.setParameter("longitude", location.getLongitude());
            query.setParameter("userId", userId);

            int result = query.executeUpdate();

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
