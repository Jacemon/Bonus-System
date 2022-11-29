package by.jcompany.bonus_system.boot.server.function;

import by.jcompany.bonus_system.boot.server.ClientHandler;
import by.jcompany.bonus_system.entity.User;

import java.util.Arrays;

public class GeneralFunctions extends Functions {
    public static User login(User user, ClientHandler client) {
        User dbUser = userService.read(user.getLogin());
        if (dbUser != null && Arrays.equals(dbUser.getPasswordHash(), user.getPasswordHash())) {
            client.setClientUser(dbUser);
            return dbUser;
        }
        return null;
    }
    
    public static User logout(ClientHandler client) {
        client.setClientUser(null);
        return null;
    }
    
    public static void quit(ClientHandler client) {
        client.setQuit(true);
    }
}
