package by.jcompany.bonus_system.service;

import by.jcompany.bonus_system.dao.Dao;

import java.util.List;

public class Service<T, K> {
    protected Dao<T, K> entityDao = new Dao<>();
    
    public boolean create(T entity) {
        return entityDao.create(entity);
    }

    public List<T> readAll() {
        return entityDao.readAll();
    }
    
    public T read(K key) {
        return entityDao.read(key);
    }
    
    public boolean update(T entity) {
        return entityDao.update(entity);
    }

    public boolean delete(T entity) {
        return entityDao.delete(entity);
    }
}
