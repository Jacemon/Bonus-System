package by.jcompany.bonus_system.menu;

import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.util.Connection;
import by.jcompany.bonus_system.util.HashManager;

import java.io.IOException;

public class MenuFunctions {
    private static final Connection connection;
    
    static {
        try {
            connection = new Connection();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
    public static void createUser(String login, String password) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("CREATE_USER",
            new User(login, HashManager.getHash(password))));
        Response response = connection.getResponse();
        System.out.println(response);
    }
    
    public static void readAllUsers() throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("READ_ALL_USERS", null));
        Response response = connection.getResponse();
        System.out.println(response);
    }
    
    public static void quit() throws IOException {
        connection.makeRequest(new Request("QUIT", null));
    }
}
