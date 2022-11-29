package by.jcompany.bonus_system.function;

import by.jcompany.bonus_system.util.Connection;

import java.io.IOException;

public abstract class Functions {
    static final Connection connection;
    
    static {
        try {
            connection = new Connection();
        } catch (IOException exception) {
            // Ошибка соединения
            throw new RuntimeException(exception);
        }
    }
}
