package by.jcompany.bonus_system.util;

import by.jcompany.bonus_system.server.function.RoleFunctions;
import by.jcompany.bonus_system.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FunctionManager {
    private static final Map<String, ClientFunction> functions = new HashMap<>();
    @Getter @Setter
    private static Integer currentAccessLevel = null;
    
    public static class ClientFunction {
        public Role accessLevelRole;
        public Function<String, Object> function;
    
        public ClientFunction(Role accessLevelRole, Function<String, Object> function) {
            this.accessLevelRole = accessLevelRole;
            this.function = function;
        }
    }
    
    public static void addFunction(String command, ClientFunction function) {
        functions.put(command, function);
    }
    
    public static Object executeFunction(String command, String requestString)
        throws NullPointerException, AccessDeniedException {
        
        ClientFunction clientFunction = functions.get(command);
        Integer functionAccessLevel = RoleFunctions.readRoleAccessLevel(clientFunction.accessLevelRole);
        
        if (functionAccessLevel == null) {
            return clientFunction.function.apply(requestString);
        }
        if (currentAccessLevel == null) {
            throw new AccessDeniedException("Forbidden!");
        }
        if (functionAccessLevel >= currentAccessLevel) {
            return clientFunction.function.apply(requestString);
        }
        throw new AccessDeniedException("Forbidden! Low user access level");
    }
}
