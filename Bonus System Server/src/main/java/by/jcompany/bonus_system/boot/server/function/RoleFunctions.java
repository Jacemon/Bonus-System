package by.jcompany.bonus_system.boot.server.function;

import by.jcompany.bonus_system.entity.Role;

public class RoleFunctions extends Functions {
    public static boolean createRole(Role role) {
        return roleService.create(role);
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
