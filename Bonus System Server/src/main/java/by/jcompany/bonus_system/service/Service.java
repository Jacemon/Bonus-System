package by.jcompany.bonus_system.service;

import java.util.List;

public interface Service<T, K> {
    boolean create(T entity);
    
    List<T> readAll();
    
    boolean update(T entity);
    
    boolean delete(T entity);
    
    T read(K id);
}
