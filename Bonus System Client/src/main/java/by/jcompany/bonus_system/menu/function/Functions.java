package by.jcompany.bonus_system.menu.function;

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
    static final Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(Instant.class,
            (JsonDeserializer<Instant>) (json, type, jsonDeserializationContext) ->
                ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toInstant())
        .create();
    
    static {
        try {
            connection = new Connection();
            Response.setGson(gson);
            Request.setGson(gson);
        } catch (IOException exception) {
            // ошибка соединения
            throw new RuntimeException(exception);
        }
    }
}
