package by.jcompany.bonus_system.server.init;

import by.jcompany.bonus_system.entity.Role;
import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.service.UserService;
import by.jcompany.bonus_system.util.HashManager;

public class InitDatabaseRows {
    public static void tryInitAdmin() {
        UserService userService = new UserService();
        if (userService.read("admin") == null) {
            userService.create(new User("admin",
                HashManager.getHash("admin"), new Role("ADMIN"))
            );
        }
    }
}
