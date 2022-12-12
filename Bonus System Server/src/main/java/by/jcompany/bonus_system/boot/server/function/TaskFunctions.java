package by.jcompany.bonus_system.boot.server.function;

import by.jcompany.bonus_system.boot.server.init.InitSavedValues;
import by.jcompany.bonus_system.entity.Employee;
import by.jcompany.bonus_system.entity.Task;

import java.util.List;

public class TaskFunctions extends Functions {
    public static boolean createTask(Task task) {
        return taskService.create(task);
    }
    
    public static List<Task> readAllTasks() {
        return taskService.readAll();
    }
    
    public static boolean updateTask(Task task) {
        return taskService.update(task);
    }
    
    public static boolean deleteTask(Integer taskId) {
        Task task = taskService.read(taskId);
        if (task == null) {
            return false;
        }
        return taskService.delete(task);
    }
    
    public static boolean setTaskCompleted(Integer taskId, Integer employeeId) {
        Task task = taskService.read(taskId);
        if (task.getEmployee().getId().equals(employeeId)) {
            task.setCompleted(true);
            return taskService.update(task);
        }
        return false;
    }
    
    public static boolean setTaskToEmployee(Integer taskId, Integer employeeId) {
        Task task = taskService.read(taskId);
        Employee employee = employeeService.read(employeeId);
        if (task.getEmployee() != null || employee == null) {
            return false;
        } else {
            task.setEmployee(employee);
            return taskService.update(task);
        }
    }
    
    public static boolean unsetTaskFromEmployee(Integer taskId, Integer employeeId) {
        Task task = taskService.read(taskId);
        Employee employee = employeeService.read(employeeId);
        if (task.getEmployee().getId().equals(employee.getId())) {
            return false;
        } else {
            task.setEmployee(null);
            return taskService.update(task);
        }
    }
    
    public static boolean setPointCost(Float pointCost) {
        Task.setPointCost(pointCost);
        boolean result = Task.getPointCost() != null;
        if (result) {
            InitSavedValues.trySaveTaskPointCost();
        }
        return result;
    }
    
    public static Float getPointCost() {
        return Task.getPointCost();
    }
}
