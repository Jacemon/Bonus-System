package by.jcompany.bonus_system.service;

import by.jcompany.bonus_system.dao.Dao;
import by.jcompany.bonus_system.dao.RoleDao;
import by.jcompany.bonus_system.entity.Role;

import java.util.List;

public class RoleService implements Service<Role, String> {
    Dao<Role, String> roleDao = new RoleDao();
    
    @Override
    public boolean create(Role role) {
        return roleDao.create(prepareRole(role));
    }
    
    @Override
    public List<Role> readAll() {
        return roleDao.readAll();
    }
    
    @Override
    public boolean update(Role role) {
        return roleDao.update(prepareRole(role));
    }
    
    @Override
    public boolean delete(Role role) {
        return roleDao.delete(prepareRole(role));
    }
    
    @Override
    public Role read(String id) {
        return roleDao.read(prepareRoleName(id));
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
