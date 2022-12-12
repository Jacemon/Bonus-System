package by.jcompany.bonus_system.boot.server.function;

import by.jcompany.bonus_system.entity.Employee;
import by.jcompany.bonus_system.entity.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmployeeFunctions extends Functions {
    public static boolean createEmployee(Employee employee) {
        return employeeService.create(employee);
    }
    
    public static List<Employee> readAllEmployees() {
        return employeeService.readAll();
    }
    
    public static boolean updateEmployee(Employee employee) {
        return employeeService.update(employee);
    }
    
    public static boolean deleteEmployee(Integer employeeId) {
        Employee employee = employeeService.read(employeeId);
        if (employee == null) {
            return false;
        }
        return employeeService.delete(employee);
    }
    
    public static Float payEmployeeBonuses(Integer employeeId, boolean forThisYear) {
        Employee employee = employeeService.read(employeeId);
        float finalAmount = 0.0f;
        
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Calendar taskDate = Calendar.getInstance();
        
        for (Task task : employee.getTasks()) {
            if (task.isCompleted() && !task.isPaid()) {
                if (forThisYear) {
                    taskDate.setTime(Date.from(task.getCreationTime()));
                    int taskYear = taskDate.get(Calendar.YEAR);
                    
                    if (currentYear != taskYear) {
                        continue;
                    }
                }
                Float amount = task.getBonus().getAmount(employee);
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
    
    public static Float payEmployeeBonusesForAll(boolean forThisYear) {
        List<Employee> employees = employeeService.readAll();
        float finalAmount = 0.0f;
        for (Employee employee : employees) {
            Float amount = payEmployeeBonuses(employee.getId(), forThisYear);
            if (amount == null) {
                continue;
            }
            finalAmount += amount;
        }
        return finalAmount;
    }
    
    public static Float calculateEmployeeBonuses(Integer employeeId, boolean forThisYear) {
        Employee employee = employeeService.read(employeeId);
        float finalAmount = 0.0f;
        
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Calendar taskDate = Calendar.getInstance();
        
        for (Task task : employee.getTasks()) {
            if (task.isCompleted() && !task.isPaid()) {
                if (forThisYear) {
                    
                    taskDate.setTime(Date.from(task.getCreationTime()));
                    int taskYear = taskDate.get(Calendar.YEAR);
                    
                    if (currentYear != taskYear) {
                        continue;
                    }
                }
                Float amount = task.getBonus().getAmount(employee);
                if (amount == null) {
                    continue;
                }
                finalAmount += amount;
            }
        }
        return finalAmount;
    }
    
    public static Float calculateEmployeeBonusesForAll(boolean forThisYear) {
        List<Employee> employees = employeeService.readAll();
        float finalAmount = 0.0f;
        for (Employee employee : employees) {
            Float amount = calculateEmployeeBonuses(employee.getId(), forThisYear);
            if (amount == null) {
                continue;
            }
            finalAmount += amount;
        }
        return finalAmount;
    }
}
