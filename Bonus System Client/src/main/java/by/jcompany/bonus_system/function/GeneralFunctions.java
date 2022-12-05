package by.jcompany.bonus_system.function;

import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.model.dto.UserDto;
import by.jcompany.bonus_system.util.HashManager;

public class GeneralFunctions extends Functions {
    public static UserDto login(String login, String password) {
        try {
            connection.makeRequest(new Request("LOGIN",
                new UserDto(login, HashManager.getHash(password)))
            );
            Response response = connection.getResponse();
            if (!response.isError()) {
                return (UserDto) response.getResponseObject(UserDto.class);
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static String logout() {
        try {
            connection.makeRequest(new Request("LOGOUT", null));
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
    
    public static void quit() {
        try {
            connection.makeRequest(new Request("QUIT", null));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
