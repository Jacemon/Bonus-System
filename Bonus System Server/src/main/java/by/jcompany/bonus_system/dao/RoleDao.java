package by.jcompany.bonus_system.dao;

import by.jcompany.bonus_system.entity.Role;
import by.jcompany.bonus_system.util.HibernateSessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class RoleDao implements Dao<Role, String> {
    @Override
    public boolean create(Role role) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(role);
            transaction.commit();
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Override
    public List<Role> readAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM Role", Role.class).getResultList());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean update(Role role) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (role.getName() == null) {
                throw new HibernateException("Entity has null id");
            }
            if (read(role.getName()) == null) {
                throw new HibernateException("Entity not exist");
            }
            session.merge(role);
            transaction.commit();
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    @Override
    public boolean delete(Role role) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(role);
            transaction.commit();
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    public Role read(String name) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(Role.class, name);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
