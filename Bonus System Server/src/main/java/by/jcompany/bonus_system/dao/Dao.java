package by.jcompany.bonus_system.dao;

import by.jcompany.bonus_system.util.HibernateSessionFactory;
import jakarta.persistence.Id;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class Dao<T, K> {
    private final Class<T> entityClass;
    
    public Dao() {
        @SuppressWarnings("unchecked")
        Class<T> tClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];
        this.entityClass = tClass;
    }
    
    public boolean create(T entity) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
    
    public List<T> readAll() {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return new ArrayList<>(session.createQuery("FROM " + entityClass.getName(),
                entityClass).getResultList());
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public T read(K key) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            return session.get(entityClass, key);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    // Наверное будет некорректно работать с двумя Id
    public boolean update(T entity) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            
            for (Field field : entity.getClass().getDeclaredFields()) {
                if (field.getAnnotation(Id.class) != null) {
                    field.setAccessible(true);

                    @SuppressWarnings("unchecked")
                    K key = (K) field.get(entity);

                    if (key == null) {
                        throw new HibernateException("Entity has null id");
                    }

                    if (read(key) == null) {
                        throw new HibernateException("Entity not exist");
                    }
                    break;
                }
            }
            
            session.merge(entity);
            transaction.commit();
        } catch (Exception exception) {
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
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}
