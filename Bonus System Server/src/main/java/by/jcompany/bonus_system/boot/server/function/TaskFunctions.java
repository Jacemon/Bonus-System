package by.jcompany.bonus_system.boot.server.function;

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
    
    public static boolean setTaskCompleted(Integer taskId) {
        Task task = taskService.read(taskId);
        task.setCompleted(true);
        return taskService.update(task);
    }
    
    public static boolean setTaskCompletedByEmployee(Integer taskId, Integer employeeId) {
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
        if (task == null || employee == null) {
            return false;
        }
        task.setEmployee(employee);
        return taskService.update(task);
    }
    
    public static boolean setTaskByEmployee(Integer taskId, Integer employeeId) {
        Task task = taskService.read(taskId);
        Employee employee = employeeService.read(employeeId);
        if (task == null || employee == null || task.getEmployee() != null) {
            return false;
        }
        task.setEmployee(employee);
        return taskService.update(task);
    }
}
