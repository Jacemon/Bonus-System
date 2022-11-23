package by.jcompany.bonus_system.util;

import by.jcompany.bonus_system.entity.Role;
import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.server.ClientHandler;
import by.jcompany.bonus_system.server.function.RoleFunctions;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FunctionManager {
    private static final Map<String, ClientFunction> functions = new HashMap<>();
    
    public static void addFunction(String command, ClientFunction function) {
        functions.put(command, function);
    }
    
    public static Object executeFunction(String command, String requestString, User user)
        throws NullPointerException, AccessDeniedException {
        
        ClientFunction clientFunction = functions.get(command);
        
        Integer functionAccessLevel = RoleFunctions.readRoleAccessLevel(clientFunction.accessLevelRole);
        if (functionAccessLevel == null) {
            return clientFunction.function.apply(requestString);
        }
        if (user == null) {
            throw new AccessDeniedException("Forbidden!");
        }
        
        Integer clientAccessLevel = user.getRole().getAccessLevel();
        if (functionAccessLevel >= clientAccessLevel) {
            return clientFunction.function.apply(requestString);
        }
        throw new AccessDeniedException("Forbidden! Low user access level");
    }
    
    public static class ClientFunction {
        public Role accessLevelRole;
        public Function<String, Object> function;
        
        public ClientFunction(Role accessLevelRole, Function<String, Object> function) {
            this.accessLevelRole = accessLevelRole;
            this.function = function;
        }
    }
}
