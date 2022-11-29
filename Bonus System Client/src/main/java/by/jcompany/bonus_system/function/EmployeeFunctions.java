package by.jcompany.bonus_system.function;

import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import by.jcompany.bonus_system.model.dto.UserDto;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFunctions extends Functions {
    public static String createEmployee(String firstName, String lastName) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("CREATE_EMPLOYEE", new EmployeeDto(firstName, lastName)));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return response.getResponseString();
        }
        return null;
    }
    
    public static List<EmployeeDto> readAllEmployees() throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("READ_ALL_EMPLOYEES", null));
        Response response = connection.getResponse();
        if (!response.isError()) {
            Type type = new TypeToken<ArrayList<EmployeeDto>>(){}.getType();
            return (List<EmployeeDto>) response.getResponseObject(type);
        }
        return null;
    }
    
    public static Float calculateBonuses(Integer employeeId) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("CALCULATE_BONUSES", employeeId));
        Response response = connection.getResponse();
        if (!response.isError()) {
            return (Float) response.getResponseObject(Float.TYPE);
        }
        return null;
    }
}
