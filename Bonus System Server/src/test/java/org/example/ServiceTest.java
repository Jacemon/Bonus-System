package org.example;

import by.jcompany.bonus_system.entity.Employee;
import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.service.EmployeeService;
import by.jcompany.bonus_system.service.Service;
import by.jcompany.bonus_system.service.UserService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class ServiceTest {
    
    @Test
    void test() {
        Service<Employee, Integer> employeeService = new EmployeeService();
        Service<User, String> userService = new UserService();
        
        User user = userService.read("login2");
        Employee employee = employeeService.read(1);
        user.setEmployee(employee);
        userService.update(user);
        fail("Successful");
    }
}
