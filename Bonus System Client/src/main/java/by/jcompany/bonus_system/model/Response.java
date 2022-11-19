package by.jcompany.bonus_system.model;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class Response implements Serializable {
    private static Gson gson = null;
    private String responseType;
    private String responseString;
    public Response(String responseType, Object responseObject) {
        if (gson == null) {
            throw new IllegalArgumentException("Gson must be set (Response.setGson)");
        }
        this.responseType = responseType;
        this.responseString = gson.toJson(responseObject);
    }
    
    public static void setGson(Gson gson) {
        Response.gson = gson;
    }
    
    @Override
    public String toString() {
        return '\t' + responseType + '\n' + responseString.indent(4);
    }
}