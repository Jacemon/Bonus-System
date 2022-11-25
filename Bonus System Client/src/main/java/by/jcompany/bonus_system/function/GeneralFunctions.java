package by.jcompany.bonus_system.function;

import by.jcompany.bonus_system.dto.UserDto;
import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.util.HashManager;

import java.io.IOException;

public class GeneralFunctions extends Functions {
    public static void login(String login, String password) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("LOGIN",
            new UserDto(login, HashManager.getHash(password)))
        );
        Response response = connection.getResponse();
        System.out.println(response);
    }
    
    public static void logout() throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("LOGOUT", null));
        Response response = connection.getResponse();
        System.out.println(response);
    }
    
    public static void quit() throws IOException {
        connection.makeRequest(new Request("QUIT", null));
    }
}
