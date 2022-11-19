package by.jcompany.bonus_system.boot;

import by.jcompany.bonus_system.entity.Role;
import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.Instant;
import java.time.ZonedDateTime;

public class ClientFunctionsCreator {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Instant.class,
        (JsonDeserializer<Instant>) (json, type, jsonDeserializationContext) ->
            ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toInstant()).create();
    
    static {
        Request.setGson(gson);
        Response.setGson(gson);
    }
    
    public static void create() {
        ClientFunctions.addFunction("CREATE_USER", (String requestString) -> {
            User user = gson.fromJson(requestString, User.class);
            ClientFunctions.createUser(user);
            return "User created";
        });
        ClientFunctions.addFunction("READ_ALL_USERS", (String requestString) -> ClientFunctions.readAllUsers());
        ClientFunctions.addFunction("CREATE_ROLE", (String requestString) -> {
            Role role = gson.fromJson(requestString, Role.class);
            ClientFunctions.createRole(role);
            return "Role created";
        });
    }
}
