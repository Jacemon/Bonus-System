package by.jcompany.bonus_system.dao;

import by.jcompany.bonus_system.entity.Bonus;
import by.jcompany.bonus_system.entity.Employee;
import by.jcompany.bonus_system.util.HibernateSessionFactory;
import jakarta.persistence.PersistenceException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BonusDao implements Dao<Bonus, Integer> {
    @Override
    public boolean create(Bonus bonus) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(bonus);
            transaction.commit();
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Override
    public List<Bonus> readAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM Bonus",
                Bonus.class).getResultList());
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean update(Bonus bonus) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (bonus.getId() == null) {
                throw new HibernateException("Entity has null id");
            }
            session.merge(bonus);
            transaction.commit();
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Override
    public boolean delete(Bonus bonus) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(bonus);
            transaction.commit();
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Override
    public Bonus read(Integer id) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(Bonus.class, id);
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
