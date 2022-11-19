package by.jcompany.bonus_system.boot;

import by.jcompany.bonus_system.menu.ClientFunctions;

import java.io.IOException;

public class ClientBoot {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClientFunctions.readAllUsers();
        ClientFunctions.createRole("GUEST");
        ClientFunctions.quit();
    }
}
