package org.example;

import by.jcompany.bonus_system.util.HibernateSessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.Set;

public abstract class DaoA<T, K> {
    public boolean create(T entity) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (HibernateException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    
    
    public Set<T> readAll(Class<T> aClass) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            T t;
            
            System.out.println(aClass.getName());
            System.out.println(aClass);
            return new HashSet<>(session.createQuery("FROM " + aClass.getName(), aClass).getResultList());
        } catch (HibernateException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public boolean update(T entity) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            /*if (entity.getLogin() == null) {
                throw new HibernateException("Entity has null id");
            }*/
            session.merge(entity);
            transaction.commit();
        } catch (HibernateException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean delete(T entity) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (HibernateException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    public T read(K id, Class<T> aClass) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(aClass, id);
        } catch (HibernateException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
