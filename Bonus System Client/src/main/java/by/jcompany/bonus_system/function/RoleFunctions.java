package by.jcompany.bonus_system.function;

import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.model.dto.RoleDto;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RoleFunctions extends Functions {
    public static String createRole(String roleName, int permissionLevel) {
        try {
            connection.makeRequest(new Request("CREATE_ROLE", new RoleDto(roleName, permissionLevel)));
            Response response = connection.getResponse();
            if (!response.isError()) {
                return (String) response.getResponseObject(String.class);
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static List<RoleDto> readAllRoles() {
        try {
            connection.makeRequest(new Request("READ_ALL_ROLES", null));
            Response response = connection.getResponse();
            if (!response.isError()) {
                Type type = new TypeToken<ArrayList<RoleDto>>() {
                }.getType();
                return (List<RoleDto>) response.getResponseObject(type);
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
