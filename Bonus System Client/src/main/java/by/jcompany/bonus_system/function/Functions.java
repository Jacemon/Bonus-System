package by.jcompany.bonus_system.function;

import by.jcompany.bonus_system.util.Connection;

public abstract class Functions {
    protected static Connection connection = new Connection();
    
    public static boolean isConnected() {
        return connection != null;
    }
    
    public static void reconnect() {
        connection = new Connection();
    }
}
