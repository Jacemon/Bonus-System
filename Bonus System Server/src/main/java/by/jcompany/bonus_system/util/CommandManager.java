package by.jcompany.bonus_system.util;

import by.jcompany.bonus_system.boot.server.ClientHandler;
import by.jcompany.bonus_system.boot.server.function.RoleFunctions;
import by.jcompany.bonus_system.entity.Role;
import by.jcompany.bonus_system.model.Response;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// todo Как идея: можно сделать этот класс не статическим, и делать его экземпляр в КлиентХендлер,
//  после чего заполнять его заново для каждого клиента функциями. Тогда перенести поле уровня доступа
//  из юзера КлиентХендлера можно в этот класс
public class CommandManager {
    private static final Map<String, ServerCommand> commands = new HashMap<>();
    
    public static void addCommand(String command, ServerCommand function) {
        commands.put(command, function);
    }
    
    public static Response executeCommand(String command, String requestString, ClientHandler client)
        throws RuntimeException {
        
        // Checking command existence
        ServerCommand serverCommand = commands.get(command);
        if (serverCommand == null) {
            return new Response(Response.ResponseType.ERROR, "Command not exist");
        }
        // Checking command access level
        Integer functionAccessLevel = RoleFunctions.readRoleAccessLevel(serverCommand.accessLevelRole);
        if (functionAccessLevel == null) {
            // Checking exceptions in command
            try {
                return new Response(Response.ResponseType.OK,
                    serverCommand.command.apply(new ClientRequestString(requestString, client)));
            } catch (Exception exception) {
                return new Response(Response.ResponseType.ERROR, exception.getMessage());
            }
        }
        // Checking that command access level higher than user access level
        if (client.getClientUser() != null &&
            functionAccessLevel >= client.getClientUser().getRole().getAccessLevel()) {
            // Checking exceptions in command
            try {
                return new Response(Response.ResponseType.OK,
                    serverCommand.command.apply(new ClientRequestString(requestString, client)));
            } catch (Exception exception) {
                return new Response(Response.ResponseType.ERROR, exception.getMessage());
            }
        }
        return new Response(Response.ResponseType.ERROR, "Forbidden");
    }
    
    @AllArgsConstructor
    public static class ClientRequestString {
        public String requestString;
        public ClientHandler client;
    }
    
    @AllArgsConstructor
    public static class ServerCommand {
        public Role accessLevelRole;
        public Function<ClientRequestString, Object> command;
    }
}
