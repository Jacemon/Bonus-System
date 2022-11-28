package by.jcompany.bonus_system.boot;

import by.jcompany.bonus_system.function.GeneralFunctions;
import by.jcompany.bonus_system.function.UserFunctions;

import java.io.IOException;

public class ClientBoot {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println(GeneralFunctions.login("login", "password"));
        System.out.println(GeneralFunctions.login("admin", "admin"));
        System.out.println(UserFunctions.createUser("login", "password"));
        System.out.println(UserFunctions.createUser("login", "password"));
        System.out.println(UserFunctions.readAllUsers());
        System.out.println(UserFunctions.readAllUsers());
        System.out.println(GeneralFunctions.logout());
        System.out.println(UserFunctions.readAllUsers());
        GeneralFunctions.quit();
    }
}
