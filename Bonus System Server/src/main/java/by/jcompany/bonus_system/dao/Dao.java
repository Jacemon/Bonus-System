package by.jcompany.bonus_system.dao;

import by.jcompany.bonus_system.entity.IdHandler;
import by.jcompany.bonus_system.util.HibernateSessionFactory;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public interface Dao<T extends IdHandler, K> {
    boolean create(T entity);
    
    List<T> readAll();
    
    boolean update(T entity);
    
    boolean delete(T entity);
    
    T read(K Id);
    
    default boolean saveOrUpdate(T entity) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            if (entity.getIdField() == null) {
                session.persist(entity);
            } else {
                session.merge(entity);
            }
            transaction.commit();
        } catch (PersistenceException exception) {
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}
