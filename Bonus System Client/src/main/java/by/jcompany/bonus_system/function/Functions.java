package by.jcompany.bonus_system.function;

import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.util.Connection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;

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
