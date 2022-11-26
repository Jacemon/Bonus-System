package by.jcompany.bonus_system.function;

import by.jcompany.bonus_system.model.dto.RoleDto;
import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;

import java.io.IOException;

public class RoleFunctions extends Functions {
    public static void createRole(String roleName, int permissionLevel) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("CREATE_ROLE",
            new RoleDto(roleName, permissionLevel)));
        Response response = connection.getResponse();
        System.out.println(response);
    }
}
