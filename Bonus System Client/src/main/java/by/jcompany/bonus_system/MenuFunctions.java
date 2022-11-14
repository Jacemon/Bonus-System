package by.jcompany.bonus_system;

import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.util.HashManager;

import java.io.IOException;

public class MenuFunctions {
    public static void createUser() throws IOException, ClassNotFoundException {
        Connection connection = Connection.getConnection();
        connection.makeRequest(new Request("CREATE_USER",
            new User("login4", HashManager.getHash("asd"))));
        Response response = connection.getResponse();
        System.out.println(response.responseString);
    }
}
