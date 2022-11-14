package by.jcompany.bonus_system.boot;

import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.service.Service;
import by.jcompany.bonus_system.service.UserService;

import java.util.List;

public class ClientFunctions {
    private static final Service<User, String> userService = new UserService();
    
    public static void createUser(User user) {
        userService.create(user);
    }
    
    public static List<User> readAllUsers() {
        return userService.readAll();
    }
}
