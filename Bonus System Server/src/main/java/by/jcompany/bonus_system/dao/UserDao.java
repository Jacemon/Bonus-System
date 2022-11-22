package by.jcompany.bonus_system.dao;

import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.util.HibernateSessionFactory;
import jakarta.persistence.PersistenceException;
import org.hibernate.HibernateException;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User, Integer> {
    @Override
    public boolean create(User user) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (user.getEmployee() != null) {
                if (user.getEmployee().getId() == null) {
                    session.persist(user.getEmployee());
                } else {
                    session.merge(user.getEmployee());
                }
            }
            session.persist(user);
            transaction.commit();
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Override
    public List<User> readAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM User", User.class).getResultList());
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean update(User user) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (user.getId() == null) {
                throw new HibernateException("Entity has null id");
            }
            if (user.getEmployee() != null && user.getEmployee().getId() == null) {
                session.persist(user.getEmployee());
            }
            session.merge(user);
            transaction.commit();
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Override
    public boolean delete(User user) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Override
    public User read(Integer id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(User.class, id);
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public User read(String login) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            //return session.bySimpleNaturalId(User.class).load(login);
            return session.createQuery("from User where login = '" + login + '\'', User.class)
                .getResultList().get(0);
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
