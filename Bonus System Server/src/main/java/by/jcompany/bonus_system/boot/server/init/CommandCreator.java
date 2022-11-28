package by.jcompany.bonus_system.boot.server.init;

import by.jcompany.bonus_system.boot.server.function.GeneralFunctions;
import by.jcompany.bonus_system.boot.server.function.RoleFunctions;
import by.jcompany.bonus_system.entity.Role;
import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.boot.server.function.UserFunctions;
import by.jcompany.bonus_system.model.dto.UserDto;
import by.jcompany.bonus_system.util.CommandManager;
import by.jcompany.bonus_system.util.CommandManager.ClientRequestString;
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
    
    // todo доделать обработчик роли
    public static void create() {
        CommandManager.addCommand("LOGIN", new CommandManager.ServerCommand(
            null,
            (ClientRequestString clientRequestString) -> {
                User user = gson.fromJson(clientRequestString.requestString, User.class);
                user = GeneralFunctions.login(user, clientRequestString.client);
                if (user != null) {
                    return new UserDto(user);
                }
                throw new IllegalArgumentException("Login or password is incorrect");
            }
        ));
        CommandManager.addCommand("LOGOUT", new CommandManager.ServerCommand(
            null,
            (ClientRequestString clientRequestString) -> GeneralFunctions.logout(clientRequestString.client)
        ));
        CommandManager.addCommand("QUIT", new CommandManager.ServerCommand(
            null,
            (ClientRequestString clientRequestString) -> {
                GeneralFunctions.quit(clientRequestString.client);
                return null;
            }
        ));
        CommandManager.addCommand("CREATE_USER", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                User user = gson.fromJson(clientRequestString.requestString, User.class);
                if (UserFunctions.createUser(user)) {
                    return "User created";
                }
                return "User not created";
            }
        ));
        CommandManager.addCommand("READ_ALL_USERS", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> UserFunctions.readAllUsers()
        ));
        CommandManager.addCommand("CREATE_ROLE", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                Role role = gson.fromJson(clientRequestString.requestString, Role.class);
                if (RoleFunctions.createRole(role)) {
                    return "Role created";
                }
                return "Role not created";
            }
        ));
    }
}
