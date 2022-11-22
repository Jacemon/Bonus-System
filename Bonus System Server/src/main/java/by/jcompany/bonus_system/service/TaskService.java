package by.jcompany.bonus_system.service;

import by.jcompany.bonus_system.dao.Dao;
import by.jcompany.bonus_system.dao.TaskDao;
import by.jcompany.bonus_system.entity.Task;

import java.util.List;

public class TaskService implements Service<Task, Integer> {
    Dao<Task, Integer> taskDao = new TaskDao();
    
    @Override
    public boolean create(Task task) {
        task.getBonus().setTask(task);
        return taskDao.create(task);
    }
    
    @Override
    public List<Task> readAll() {
        return taskDao.readAll();
    }
    
    @Override
    public boolean update(Task task) {
        task.getBonus().setTask(task);
        return taskDao.update(task);
    }
    
    @Override
    public boolean delete(Task task) {
        return taskDao.delete(task);
    }
    
    @Override
    public Task read(Integer id) {
        return taskDao.read(id);
    }
}
