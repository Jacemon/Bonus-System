package by.jcompany.bonus_system.function;

import by.jcompany.bonus_system.model.dto.UserDto;
import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.util.HashManager;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserFunctions extends Functions {
    public static String createUser(String login, String password) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("CREATE_USER",
            new UserDto(login, HashManager.getHash(password))));
        Response response = connection.getResponse();
        return response.getResponseString();
    }
    
    public static List<UserDto> readAllUsers() throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("READ_ALL_USERS", null));
        Response response = connection.getResponse();
        if (!response.isError()) {
            Type type = new TypeToken<ArrayList<UserDto>>(){}.getType();
            try {
                List<UserDto> users = (List<UserDto>) response.getResponseObject(type);
                return users;
            } catch (Exception exceptio) {
                return null;
            }
        }
        return null;
    }
}
