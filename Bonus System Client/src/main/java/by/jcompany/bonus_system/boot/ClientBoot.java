package by.jcompany.bonus_system.boot;

import by.jcompany.bonus_system.MenuFunctions;

import java.io.IOException;

public class ClientBoot {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        MenuFunctions.createUser();
    }
}
