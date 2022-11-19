package by.jcompany.bonus_system.menu;

import by.jcompany.bonus_system.dto.RoleDto;
import by.jcompany.bonus_system.dto.UserDto;
import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.util.Connection;
import by.jcompany.bonus_system.util.HashManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;

public class ClientFunctions {
    private static final Connection connection;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Instant.class,
        (JsonDeserializer<Instant>) (json, type, jsonDeserializationContext) ->
            ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toInstant()).create();
    
    static {
        try {
            connection = new Connection();
            Response.setGson(gson);
            Request.setGson(gson);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    
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
    
    public static void createRole(String roleName) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("CREATE_ROLE",
            new RoleDto(roleName)));
        Response response = connection.getResponse();
        System.out.println(response);
    }
    
    public static void quit() throws IOException {
        connection.makeRequest(new Request("QUIT", null));
    }
}
