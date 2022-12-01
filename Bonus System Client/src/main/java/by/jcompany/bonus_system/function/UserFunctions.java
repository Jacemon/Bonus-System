package by.jcompany.bonus_system.function;

import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.model.dto.UserDto;
import by.jcompany.bonus_system.util.HashManager;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserFunctions extends Functions {
    public static String createUser(String login, String password) {
        try {
            connection.makeRequest(new Request("CREATE_USER",
                new UserDto(login, HashManager.getHash(password))));
            Response response = connection.getResponse();
            if (!response.isError()) {
                return response.getResponseString();
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static List<UserDto> readAllUsers() {
        try {
            connection.makeRequest(new Request("READ_ALL_USERS", null));
            Response response = connection.getResponse();
            if (!response.isError()) {
                Type type = new TypeToken<ArrayList<UserDto>>() {
                }.getType();
                return (List<UserDto>) response.getResponseObject(type);
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
