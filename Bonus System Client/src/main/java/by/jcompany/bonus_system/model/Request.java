package by.jcompany.bonus_system.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;

@NoArgsConstructor
@Getter
@Setter
public class Request implements Serializable {
    private String requestType;
    private String requestString;
    
    public Request(String requestType, Object requestObject) {
        this.requestType = requestType;
        Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Instant.class,
            (JsonDeserializer<Instant>) (json, type, jsonDeserializationContext) ->
            ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toInstant()).create();
        this.requestString = gson.toJson(requestObject);
    }
    
    @Override
    public String toString() {
        return '\t' + requestType + '\n' + requestString.indent(4);
    }
}