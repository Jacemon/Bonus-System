package by.jcompany;

import by.jcompany.bonus_system.entity.*;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import by.jcompany.bonus_system.model.dto.RoleDto;
import by.jcompany.bonus_system.model.dto.TaskDto;
import by.jcompany.bonus_system.model.dto.UserDto;
import by.jcompany.bonus_system.service.RoleService;
import by.jcompany.bonus_system.service.Service;
import by.jcompany.bonus_system.service.UserService;
import by.jcompany.bonus_system.util.HashManager;
import by.jcompany.bonus_system.util.json.GsonManager;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class JsonParserTest {
    Gson gson = GsonManager.getGson();
    
    UserService userService = new UserService();
    Service<Employee, Integer> employeeService = new Service<>();
    RoleService roleService = new RoleService();
    Service<Task, Integer> taskService = new Service<>();
    
    @Test
    void UserToJson() {
        try {
            User user = new User("login", HashManager.getHash("password"),
                new Role("ADMIN"));
            Employee employee = new Employee("First Name", "Second Name", 1000.0f);
            user.setEmployee(employee);
            userService.create(user);
            
            Task task1 = new Task("Try this to json!", new Bonus(Bonus.BonusType.MONEY, 100.0f));
            Task task2 = new Task("And the other one!", new Bonus(Bonus.BonusType.POINTS, 15.0f));
            task1.setEmployee(employee);
            task2.setEmployee(employee);
            taskService.create(task1);
            taskService.create(task2);
            
            user = userService.read(user.getLogin());
            
            taskService.delete(task1);
            taskService.delete(task2);
            userService.delete(user);
            employeeService.delete(employee);
            
            System.out.println(gson.toJson(new UserDto(user)));
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }
    
    @Test
    void TaskToJson() {
        try {
            User user = new User("login", HashManager.getHash("password"),
                new Role("ADMIN"));
            Employee employee = new Employee("First Name", "Second Name", 1000.0f);
            user.setEmployee(employee);
            userService.create(user);
            
            Task task1 = new Task("Try this to json!", new Bonus(Bonus.BonusType.MONEY, 100.0f));
            Task task2 = new Task("And the other one!", new Bonus(Bonus.BonusType.POINTS, 15.0f));
            task1.setEmployee(employee);
            task2.setEmployee(employee);
            taskService.create(task1);
            taskService.create(task2);
            
            task1 = taskService.read(task1.getId());
            
            taskService.delete(task1);
            taskService.delete(task2);
            userService.delete(user);
            employeeService.delete(employee);
            
            System.out.println(gson.toJson(new TaskDto(task1)));
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }
    
    @Test
    void EmployeeToJson() {
        try {
            User user = new User("login", HashManager.getHash("password"),
                new Role("ADMIN"));
            Employee employee = new Employee("First Name", "Second Name", 1000.0f);
            user.setEmployee(employee);
            userService.create(user);
            
            Task task1 = new Task("Try this to json!", new Bonus(Bonus.BonusType.MONEY, 100.0f));
            Task task2 = new Task("And the other one!", new Bonus(Bonus.BonusType.POINTS, 15.0f));
            task1.setEmployee(employee);
            task2.setEmployee(employee);
            taskService.create(task1);
            taskService.create(task2);
            
            employee = employeeService.read(employee.getId());
            
            employeeService.delete(employee);
            taskService.delete(task1);
            taskService.delete(task2);
            userService.delete(user);
            
            System.out.println(gson.toJson(new EmployeeDto(employee)));
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }
    
    @Test
    void RoleToJson() {
        try {
            User user = new User("login", HashManager.getHash("password"),
                new Role("ADMIn"));
            Employee employee = new Employee("First Name", "Second Name", 1000.0f);
            user.setEmployee(employee);
            userService.create(user);
            
            Task task1 = new Task("Try this to json!", new Bonus(Bonus.BonusType.MONEY, 100.0f));
            Task task2 = new Task("And the other one!", new Bonus(Bonus.BonusType.POINTS, 15.0f));
            task1.setEmployee(employee);
            task2.setEmployee(employee);
            taskService.create(task1);
            taskService.create(task2);
            
            Role role = roleService.read("ADMIN");
            
            employeeService.delete(employee);
            taskService.delete(task1);
            taskService.delete(task2);
            userService.delete(user);
            
            System.out.println(gson.toJson(new RoleDto(role)));
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }
}
