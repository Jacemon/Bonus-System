package by.jcompany.bonus_system.dao;

import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.util.HibernateSessionFactory;
import jakarta.persistence.PersistenceException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User, String> {
    @Override
    public boolean create(User user) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
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
            if (user.getLogin() == null) {
                throw new HibernateException("Entity has null id");
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
    public User read(String login) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(User.class, login);
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
