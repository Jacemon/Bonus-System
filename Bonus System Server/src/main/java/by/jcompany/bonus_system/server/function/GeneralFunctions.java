package by.jcompany.bonus_system.server.function;

import by.jcompany.bonus_system.entity.Role;
import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.util.FunctionManager;

import java.util.Arrays;

public class GeneralFunctions extends Functions {
    public static User login(User user) {
        User dbUser = userService.read(user.getLogin());
        if (dbUser != null && Arrays.equals(dbUser.getPasswordHash(), user.getPasswordHash())) {
            return dbUser;
        }
        return null;
    }
    
    public static User logout() {
        return null;
    }
}
