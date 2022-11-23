package by.jcompany.bonus_system.boot;

import by.jcompany.bonus_system.menu.function.GeneralFunctions;
import by.jcompany.bonus_system.menu.function.UserFunctions;

import java.io.IOException;

public class ClientBoot {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        GeneralFunctions.login("login", "password");
        GeneralFunctions.login("admin", "admin");
        GeneralFunctions.logout();
        UserFunctions.readAllUsers();
        //RoleFunctions.createRole("GUEST", 2);
        GeneralFunctions.quit();
    }
}
