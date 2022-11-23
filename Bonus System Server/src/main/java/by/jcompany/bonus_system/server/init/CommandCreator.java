package by.jcompany.bonus_system.server.init;

import by.jcompany.bonus_system.entity.Role;
import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.server.function.GeneralFunctions;
import by.jcompany.bonus_system.server.function.RoleFunctions;
import by.jcompany.bonus_system.server.function.UserFunctions;
import by.jcompany.bonus_system.util.FunctionManager;
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
            (String requestString) -> {
                User user = gson.fromJson(requestString, User.class);
                return GeneralFunctions.login(user);
                /*if (GeneralFunctions.login(user) != null) {
                    return "User log in";
                }
                return "Login or password is incorrect";*/
            }
        ));
        FunctionManager.addFunction("LOGOUT", new FunctionManager.ClientFunction(
            null,
            (String requestString) -> {
                return GeneralFunctions.logout();
                //return "User log out";
            }
        ));
        
        FunctionManager.addFunction("CREATE_USER", new FunctionManager.ClientFunction(
            new Role("ADMIN"),
            (String requestString) -> {
                User user = gson.fromJson(requestString, User.class);
                if (UserFunctions.createUser(user)) {
                    return "User created";
                }
                return "User not created";
            }
        ));
        FunctionManager.addFunction("READ_ALL_USERS", new FunctionManager.ClientFunction(
            new Role("ADMIN"),
            (String requestString) -> UserFunctions.readAllUsers()
        ));
        FunctionManager.addFunction("CREATE_ROLE", new FunctionManager.ClientFunction(
            new Role("ADMIN"),
            (String requestString) -> {
                Role role = gson.fromJson(requestString, Role.class);
                if (RoleFunctions.createRole(role)) {
                    return "Role created";
                }
                return "Role not created";
            }
        ));
    }
}
