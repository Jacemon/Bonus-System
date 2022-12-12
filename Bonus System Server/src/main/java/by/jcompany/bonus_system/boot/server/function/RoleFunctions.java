package by.jcompany.bonus_system.boot.server.function;

import by.jcompany.bonus_system.entity.Role;

import java.util.List;

public class RoleFunctions extends Functions {
    public static boolean createRole(Role role) {
        return roleService.create(role);
    }
    
    public static List<Role> readAllRoles() {
        return roleService.readAll();
    }
    
    public static boolean deleteRole(String roleName) {
        Role role = roleService.read(roleName);
        if (role == null) {
            return false;
        }
        return roleService.delete(role);
    }
    
    public static boolean updateRole(Role role) {
        return roleService.update(role);
    }
    
    public static Integer readRoleAccessLevel(Role role) {
        if (role == null) {
            return null;
        }
        Role dbRole = roleService.read(role.getName());
        if (dbRole != null) {
            return dbRole.getAccessLevel();
        }
        return null;
    }
}
