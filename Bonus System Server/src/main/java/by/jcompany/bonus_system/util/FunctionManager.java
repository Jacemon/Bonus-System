package by.jcompany.bonus_system.util;

import by.jcompany.bonus_system.entity.Role;
import by.jcompany.bonus_system.server.ClientHandler;
import by.jcompany.bonus_system.server.function.RoleFunctions;
import lombok.AllArgsConstructor;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// todo Как идея: можно сделать этот класс не статическим, и делать его экземпляр в КлиентХендлер,
//  после чего заполнять его заново для каждого клиента функциями. Тогда перенести поле уровня доступа
//  из КлиентХендлер можно в этот класс
public class FunctionManager {
    private static final Map<String, ClientFunction> functions = new HashMap<>();
    
    public static void addFunction(String command, ClientFunction function) {
        functions.put(command, function);
    }
    
    public static Object executeFunction(String command, String requestString, ClientHandler client)
        throws NullPointerException, AccessDeniedException {
        
        ClientFunction clientFunction = functions.get(command);
        
        Integer functionAccessLevel = RoleFunctions.readRoleAccessLevel(clientFunction.accessLevelRole);
        if (functionAccessLevel == null) {
            return clientFunction.function.apply(new ClientRequest(requestString, client));
        }
        if (client.getClientUser() == null) {
            throw new AccessDeniedException("Forbidden!");
        }
        
        Integer clientAccessLevel = client.getClientUser().getRole().getAccessLevel();
        if (functionAccessLevel >= clientAccessLevel) {
            return clientFunction.function.apply(new ClientRequest(requestString, client));
        }
        throw new AccessDeniedException("Forbidden! Low user access level");
    }
    
    @AllArgsConstructor
    public static class ClientRequest {
        public String requestString;
        public ClientHandler client;
    }
    
    @AllArgsConstructor
    public static class ClientFunction {
        public Role accessLevelRole;
        public Function<ClientRequest, Object> function;
    }
}
