package by.jcompany.bonus_system.boot.server.function;

import by.jcompany.bonus_system.entity.Employee;
import by.jcompany.bonus_system.entity.Task;

import java.util.List;

public class EmployeeFunctions extends Functions {
    public static boolean createEmployee(Employee employee) {
        return employeeService.create(employee);
    }
    
    public static List<Employee> readAllEmployees() {
        return employeeService.readAll();
    }
    
    // todo сделать employee_id unique
    public static boolean changeEmployeeName(String newFirstName, String newLastName) {
        return false;//todo
    }
    
    public static Float getEmployeeBonus(Integer employeeId) {
        Employee employee = employeeService.read(employeeId);
        float finalAmount = 0.0f;
        for (Task task : employee.getTasks()) {
            if (task.isCompleted() && !task.isPaid()) {
                Float amount = task.getAmount(employee);
                if (amount == null) {
                    continue;
                }
                finalAmount += amount;
                task.setPaid(true);
            }
            if (!taskService.update(task)) {
                return null;
            }
        }
        return finalAmount;
    }
    
    public static Float calculateEmployeeBonus(Integer employeeId) {
        Employee employee = employeeService.read(employeeId);
        float finalAmount = 0.0f;
        for (Task task : employee.getTasks()) {
            if (task.isCompleted() && !task.isPaid()) {
                Float amount = task.getAmount(employee);
                if (amount == null) {
                    continue;
                }
                finalAmount += amount;
            }
        }
        return finalAmount;
    }
}
