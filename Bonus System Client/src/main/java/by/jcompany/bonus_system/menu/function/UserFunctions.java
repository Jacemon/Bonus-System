package by.jcompany.bonus_system.menu.function;

import by.jcompany.bonus_system.dto.UserDto;
import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.util.HashManager;

import java.io.IOException;

public class UserFunctions extends Functions {
    public static void createUser(String login, String password) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("CREATE_USER",
            new UserDto(login, HashManager.getHash(password))));
        Response response = connection.getResponse();
        System.out.println(response);
    }
    
    public static void readAllUsers() throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("READ_ALL_USERS", null));
        Response response = connection.getResponse();
        System.out.println(response);
    }
}
