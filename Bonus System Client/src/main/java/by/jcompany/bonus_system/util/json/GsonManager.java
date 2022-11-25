package by.jcompany.bonus_system.util.json;

import com.google.gson.*;

import java.time.Instant;
import java.time.ZonedDateTime;

public class GsonManager {
    private static Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        // Instant de(se)rialization
        .registerTypeAdapter(Instant.class,
            (JsonDeserializer<Instant>) (json, type, jsonDeserializationContext) ->
                ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toInstant())
        .registerTypeAdapter(Instant.class,
            (JsonSerializer<Instant>) (json, type, jsonDeserializationContext) ->
                new JsonPrimitive(json.toString()))
        // @Exclude annotation
        .setExclusionStrategies(new AnnotationExclusionStrategy())
        .create();
    
    public static Gson getGson() {
        return gson;
    }
}
