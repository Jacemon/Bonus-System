package by.jcompany.bonus_system.function;

import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import by.jcompany.bonus_system.model.dto.RoleDto;
import by.jcompany.bonus_system.model.dto.UserDto;
import by.jcompany.bonus_system.util.HashManager;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserFunctions extends Functions {
    public static String createUser(String login, String password, String role, EmployeeDto employee) {
        try {
            UserDto user = new UserDto(login, HashManager.getHash(password));
            user.setRole(new RoleDto(role));
            user.setEmployee(employee);
            connection.makeRequest(new Request("CREATE_USER", user));
            Response response = connection.getResponse();
            if (!response.isError()) {
                return (String) response.getResponseObject(String.class);
            }
            return (String) response.getResponseObject(String.class);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    // Роль по умолчанию 'COMMON'
    public static String createUser(String login, String password) {
        try {
            connection.makeRequest(new Request("CREATE_USER",
                new UserDto(login, HashManager.getHash(password))));
            Response response = connection.getResponse();
            if (!response.isError()) {
                return (String) response.getResponseObject(String.class);
            }
            return (String) response.getResponseObject(String.class);
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
                @SuppressWarnings("unchecked")
                List<UserDto> users = (List<UserDto>) response.getResponseObject(type);
                return users;
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static String updateUser(Integer userId, String login, String password, String role, EmployeeDto employee) {
        try {
            UserDto user;
            if (!password.equals("")) {
                user = new UserDto(login, HashManager.getHash(password));
            } else {
                user = new UserDto(login, null);
            }
            user.setId(userId);
            user.setRole(new RoleDto(role));
            user.setEmployee(employee);
            connection.makeRequest(new Request("UPDATE_USER", user));
            Response response = connection.getResponse();
            if (!response.isError()) {
                return (String) response.getResponseObject(String.class);
            }
            return (String) response.getResponseObject(String.class);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static UserDto updateUserByEmployee(Integer userId, String login, String oldPassword, String newPassword) {
        try {
            UserDto user = new UserDto();
            user.setId(userId);
            if (!newPassword.equals("") && !oldPassword.equals("")) {
                user.setPasswordHash(HashManager.getHash(newPassword));
            } else {
                user.setPasswordHash(null);
            }
            if (!login.equals("")) {
                user.setLogin(login);
            } else {
                user.setLogin(null);
            }
            
            UserDto.UserPair userPair = new UserDto.UserPair(user, HashManager.getHash(oldPassword));
            
            connection.makeRequest(new Request("UPDATE_USER_BY_EMPLOYEE", userPair));
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
    
    public static String deleteUser(Integer userId) {
        try {
            connection.makeRequest(new Request("DELETE_USER", userId));
            Response response = connection.getResponse();
            if (!response.isError()) {
                return (String) response.getResponseObject(String.class);
            }
            return (String) response.getResponseObject(String.class);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
