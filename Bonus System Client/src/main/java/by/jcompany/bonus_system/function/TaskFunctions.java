package by.jcompany.bonus_system.function;

import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;
import by.jcompany.bonus_system.model.dto.BonusDto;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import by.jcompany.bonus_system.model.dto.TaskDto;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TaskFunctions extends Functions {
    public static String createTask(String taskDescription, Float amount, BonusDto.BonusType bonusType) {
        try {
            TaskDto task = new TaskDto(taskDescription);
            task.setBonus(new BonusDto(bonusType, amount));
            connection.makeRequest(new Request("CREATE_TASK", task));
            Response response = connection.getResponse();
            if (!response.isError()) {
                return (String) response.getResponseObject(String.class);
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static List<TaskDto> readAllTasks() {
        try {
            connection.makeRequest(new Request("READ_ALL_TASKS", null));
            Response response = connection.getResponse();
            if (!response.isError()) {
                Type type = new TypeToken<ArrayList<TaskDto>>() {
                }.getType();
                @SuppressWarnings("unchecked")
                List<TaskDto> tasks = (List<TaskDto>) response.getResponseObject(type);
                return tasks;
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static String updateTask(Integer taskId, String taskDescription, Float amount, boolean isCompleted,
                                    boolean isPaid, EmployeeDto employee, BonusDto.BonusType bonusType) {
        try {
            TaskDto task = new TaskDto();
            task.setId(taskId);
            task.setDescription(taskDescription);
            task.setCompleted(isCompleted);
            task.setPaid(isPaid);
            task.setEmployee(employee);
            task.setBonus(new BonusDto(bonusType, amount));
            
            connection.makeRequest(new Request("UPDATE_TASK", task));
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
    
    public static String deleteTask(Integer taskId) {
        try {
            connection.makeRequest(new Request("DELETE_TASK", taskId));
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
    
    public static String setTaskCompleted(Integer taskId) {
        try {
            connection.makeRequest(new Request("SET_TASK_COMPLETED", taskId));
            Response response = connection.getResponse();
            if (!response.isError()) {
                return response.getResponseString();
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static String setTaskToEmployee(Integer taskId) {
        try {
            connection.makeRequest(new Request("SET_TASK_TO_EMPLOYEE", taskId));
            Response response = connection.getResponse();
            if (!response.isError()) {
                return (String) response.getResponseObject(String.class);
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static String unsetTaskFromEmployee(Integer taskId) {
        try {
            connection.makeRequest(new Request("UNSET_TASK_FROM_EMPLOYEE", taskId));
            Response response = connection.getResponse();
            if (!response.isError()) {
                return (String) response.getResponseObject(String.class);
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static String setPointCost(Float pointCost) {
        try {
            connection.makeRequest(new Request("SET_POINT_COST", pointCost));
            Response response = connection.getResponse();
            if (!response.isError()) {
                return (String) response.getResponseObject(String.class);
            }
            return null;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static Float getPointCost() {
        try {
            connection.makeRequest(new Request("GET_POINT_COST", null));
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
}
