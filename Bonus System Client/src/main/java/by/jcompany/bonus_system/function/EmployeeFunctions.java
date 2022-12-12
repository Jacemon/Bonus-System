package by.jcompany.bonus_system.function;

import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class EmployeeFunctions extends Functions {
    public static String createEmployee(String firstName, String lastName, Float salary) {
        try {
            connection.makeRequest(new Request("CREATE_EMPLOYEE",
                new EmployeeDto(firstName, lastName, salary)));
            Response response = connection.getResponse();
            if (!response.isError()) {
                return (String) response.getResponseObject(String.class);
            }
            return (String) response.getResponseObject(String.class);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static List<EmployeeDto> readAllEmployees() {
        try {
            connection.makeRequest(new Request("READ_ALL_EMPLOYEES", null));
            Response response = connection.getResponse();
            if (!response.isError()) {
                Type type = new TypeToken<ArrayList<EmployeeDto>>() {
                }.getType();
                @SuppressWarnings("unchecked")
                List<EmployeeDto> employees = (List<EmployeeDto>) response.getResponseObject(type);
                return employees;
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static String updateEmployee(Integer employeeId, String firstName, String lastName, Float salary) {
        try {
            EmployeeDto employee = new EmployeeDto(firstName, lastName, salary);
            employee.setId(employeeId);
            connection.makeRequest(new Request("UPDATE_EMPLOYEE", employee));
            Response response = connection.getResponse();
            if (!response.isError()) {
                return (String) response.getResponseObject(String.class);
            }
            return (String) response.getResponseObject(String.class);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static String deleteEmployee(Integer employeeId) {
        try {
            connection.makeRequest(new Request("DELETE_EMPLOYEE", employeeId));
            Response response = connection.getResponse();
            if (!response.isError()) {
                return (String) response.getResponseObject(String.class);
            }
            return (String) response.getResponseObject(String.class);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static Float payBonuses(Integer employeeId) {
        try {
            connection.makeRequest(new Request("PAY_BONUSES", employeeId));
            Response response = connection.getResponse();
            if (!response.isError()) {
                return (Float) response.getResponseObject(Float.class);
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static Float calculateBonuses(Integer employeeId, boolean forAll) {
        try {
            if (forAll) {
                connection.makeRequest(new Request("CALCULATE_BONUSES", employeeId));
            } else {
                connection.makeRequest(new Request("CALCULATE_BONUSES_BY_EMPLOYEE", null));
            }
            
            Response response = connection.getResponse();
            if (!response.isError()) {
                return (Float) response.getResponseObject(Float.TYPE);
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
