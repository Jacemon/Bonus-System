package by.jcompany.bonus_system.service;

import by.jcompany.bonus_system.dao.Dao;
import by.jcompany.bonus_system.entity.Role;

import java.util.List;

public class RoleService extends Service<Role, String> {
    @Override
    public boolean create(Role role) {
        return entityDao.create(prepareRole(role));
    }
    
    @Override
    public List<Role> readAll() {
        return entityDao.readAll();
    }
    
    @Override
    public boolean update(Role role) {
        return entityDao.update(prepareRole(role));
    }
    
    @Override
    public boolean delete(Role role) {
        return entityDao.delete(prepareRole(role));
    }
    
    @Override
    public Role read(String id) {
        return entityDao.read(prepareRoleName(id));
    }
    
    private Role prepareRole(Role role) {
        role.setName(prepareRoleName(role.getName()));
        return role;
    }
    
    private String prepareRoleName(String roleName) {
        roleName = roleName.trim();
        roleName = roleName.toUpperCase();
        return roleName;
    }
}
