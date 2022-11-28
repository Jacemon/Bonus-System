package by.jcompany.bonus_system.boot.server.init;

import by.jcompany.bonus_system.entity.Role;
import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.service.RoleService;
import by.jcompany.bonus_system.service.UserService;
import by.jcompany.bonus_system.util.HashManager;

public class InitDatabaseRows {
    public static void tryInitUndefinedRole() {
        RoleService roleService = new RoleService();
        if (roleService.read("UNDEFINED") == null) {
            if (roleService.create(new Role("UNDEFINED"))) {
                System.out.println("Role 'UNDEFINED' with 'null' access level was created!");
            }
        }
    }
    
    public static void tryInitAdminRole() {
        RoleService roleService = new RoleService();
        if (roleService.read("ADMIN") == null) {
            if (roleService.create(new Role("ADMIN", 0))) {
                System.out.println("Role 'ADMIN' with 'MAX(0)' access level was created!");
            }
        }
    }
    
    public static void tryInitAdmin() {
        tryInitAdminRole();
        UserService userService = new UserService();
        if (userService.read("admin") == null) {
            if (userService.create(new User("admin",
                HashManager.getHash("admin"), new Role("ADMIN"))
            )) {
                System.out.println("Login 'admin' with role 'ADMIN' was created!");
            }
        }
    }
    
    public static void tryInitCommonRole() {
        RoleService roleService = new RoleService();
        if (roleService.read("COMMON") == null) {
            if (roleService.create(new Role("COMMON", 4))) {
                System.out.println("Role 'COMMON' with '4' access level was created!");
            }
        }
    }
}
