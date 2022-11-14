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
public class Request implements Serializable {
    private String requestType;
    private String requestString;
    
    public Request(String requestType, Object requestObject) {
        this.requestType = requestType;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        this.requestString = gson.toJson(requestObject);
    }
    
    @Override
    public String toString() {
        return '\t' + requestType + '\n' + requestString.indent(4);
    }
}