package by.jcompany.bonus_system.boot;

import by.jcompany.bonus_system.entity.Role;
import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.service.RoleService;
import by.jcompany.bonus_system.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ClientFunctions {
    private static final Map<String, Function<String, Object>> functions = new HashMap<>();
    private static final UserService userService = new UserService();
    private static final RoleService roleService = new RoleService();
    
    public static void addFunction(String command, Function<String, Object> function) {
        functions.put(command, function);
    }

    public static Object executeFunction(String requestType, String requestString) throws NullPointerException {
        return functions.get(requestType).apply(requestString);
    }
    
    public static boolean createUser(User user) {
        user.setRole(roleService.read(user.getRole().getName()));
        return userService.create(user);
    }
    
    public static List<User> readAllUsers() {
        return userService.readAll();
    }
    
    public static boolean createRole(Role role) {
        return roleService.create(role);
    }
}
