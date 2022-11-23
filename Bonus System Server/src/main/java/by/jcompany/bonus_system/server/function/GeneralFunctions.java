package by.jcompany.bonus_system.server.function;

import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.util.FunctionManager;

import java.util.Arrays;

public class GeneralFunctions extends Functions {
    public static boolean login(User user) {
        User dbUser = userService.read(user.getLogin());
        if (dbUser != null && Arrays.equals(dbUser.getPasswordHash(), user.getPasswordHash())) {
            FunctionManager.setCurrentAccessLevel(dbUser.getRole().getAccessLevel());
            return true;
        }
        return false;
    }
    
    public static void logout() {
        FunctionManager.setCurrentAccessLevel(null);
    }
}
