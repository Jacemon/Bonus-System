package by.jcompany.bonus_system.dao;

import java.util.List;

public interface Dao<T, K> {
    boolean create(T order);
    
    List<T> readAll();
    
    boolean update(T order);
    
    boolean delete(T order);
    
    T read(K orderId);
}
