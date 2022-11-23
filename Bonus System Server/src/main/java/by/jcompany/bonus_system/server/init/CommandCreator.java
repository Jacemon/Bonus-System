package by.jcompany.bonus_system.server.init;

import by.jcompany.bonus_system.entity.Role;
import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.server.function.GeneralFunctions;
import by.jcompany.bonus_system.server.function.RoleFunctions;
import by.jcompany.bonus_system.server.function.UserFunctions;
import by.jcompany.bonus_system.util.FunctionManager;
import by.jcompany.bonus_system.util.FunctionManager.ClientRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.Instant;
import java.time.ZonedDateTime;

public class CommandCreator {
    private static final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(Instant.class,
            (JsonDeserializer<Instant>)
                (json, type, jsonDeserializationContext) ->
                    ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toInstant())
        .create();
    
    static {
        Request.setGson(gson);
        Response.setGson(gson);
    }
    
    // todo доделать обработчик роли
    public static void create() {
        FunctionManager.addFunction("LOGIN", new FunctionManager.ClientFunction(
            null,
            (ClientRequest clientRequest) -> {
                User user = gson.fromJson(clientRequest.requestString, User.class);
                return GeneralFunctions.login(user, clientRequest.client);
            }
        ));
        FunctionManager.addFunction("LOGOUT", new FunctionManager.ClientFunction(
            null,
            (ClientRequest clientRequest) -> GeneralFunctions.logout(clientRequest.client)
        ));
        
        FunctionManager.addFunction("QUIT", new FunctionManager.ClientFunction(
            null,
            (ClientRequest clientRequest) -> {
                GeneralFunctions.quit(clientRequest.client);
                return null;
            }
        ));
        
        
        FunctionManager.addFunction("CREATE_USER", new FunctionManager.ClientFunction(
            new Role("ADMIN"),
            (ClientRequest clientRequest) -> {
                User user = gson.fromJson(clientRequest.requestString, User.class);
                if (UserFunctions.createUser(user)) {
                    return "User created";
                }
                return "User not created";
            }
        ));
        FunctionManager.addFunction("READ_ALL_USERS", new FunctionManager.ClientFunction(
            new Role("ADMIN"),
            (ClientRequest clientRequest) -> UserFunctions.readAllUsers()
        ));
        FunctionManager.addFunction("CREATE_ROLE", new FunctionManager.ClientFunction(
            new Role("ADMIN"),
            (ClientRequest clientRequest) -> {
                Role role = gson.fromJson(clientRequest.requestString, Role.class);
                if (RoleFunctions.createRole(role)) {
                    return "Role created";
                }
                return "Role not created";
            }
        ));
    }
}
