package org.example;

import java.util.Set;

public abstract class ServiceA<T, K> {
    DaoA<T, K> dao = new DaoA<T, K>() { };
    
    public boolean create(T entity) {
        return dao.create(entity);
    }
    
    public Set<T> readAll(Class<T> aClass) {
        return dao.readAll(aClass);
    }
    
    public boolean update(T entity) {
        return dao.update(entity);
    }
    
    public boolean delete(T entity) {
        return dao.delete(entity);
    }
    
    public T read(K id, Class<T> aClass) {
        return dao.read(id, aClass);
    }
}
