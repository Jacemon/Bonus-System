package by.jcompany.bonus_system.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class Response implements Serializable {
    private String responseType;
    private String responseString;
    
    public Response(String responseType, Object responseObject) {
        this.responseType = responseType;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        this.responseString = gson.toJson(responseObject);
    }
    
    @Override
    public String toString() {
        return '\t' + responseType + '\n' + responseString.indent(4);
    }
}