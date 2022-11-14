package by.jcompany.bonus_system.boot;

import by.jcompany.bonus_system.menu.MenuFunctions;

import java.io.IOException;

public class ClientBoot {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        MenuFunctions.createUser("login10", "123123");
        MenuFunctions.readAllUsers();
        MenuFunctions.quit();
    }
}
