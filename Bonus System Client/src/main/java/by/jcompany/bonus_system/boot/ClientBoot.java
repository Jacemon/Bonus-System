package by.jcompany.bonus_system.boot;

import by.jcompany.bonus_system.function.GeneralFunctions;
import by.jcompany.bonus_system.function.UserFunctions;

import java.io.IOException;

public class ClientBoot {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        GeneralFunctions.login("login", "password");
        GeneralFunctions.login("admin", "admin");
        UserFunctions.readAllUsers();
        System.in.read();
        UserFunctions.readAllUsers();
        GeneralFunctions.logout();
        UserFunctions.readAllUsers();
        GeneralFunctions.quit();
    }
}
