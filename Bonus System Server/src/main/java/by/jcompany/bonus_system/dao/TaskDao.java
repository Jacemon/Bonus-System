package by.jcompany.bonus_system.dao;

import by.jcompany.bonus_system.entity.Task;
import by.jcompany.bonus_system.util.HibernateSessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TaskDao implements Dao<Task, Integer> {
    @Override
    public boolean create(Task task) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (task.getBonus().getId() == null) {
                session.persist(task.getBonus());
            }
            session.persist(task);
            transaction.commit();
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Override
    public List<Task> readAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM Task", Task.class).getResultList());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean update(Task task) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (task.getId() == null) {
                throw new HibernateException("Entity has null id");
            }
            if (task.getBonus().getId() == null) {
                session.persist(task.getBonus());
            }
            if (read(task.getId()) == null) {
                throw new HibernateException("Entity not exist");
            }
            session.merge(task);
            transaction.commit();
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Override
    public boolean delete(Task task) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(task);
            transaction.commit();
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Override
    public Task read(Integer id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(Task.class, id);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
