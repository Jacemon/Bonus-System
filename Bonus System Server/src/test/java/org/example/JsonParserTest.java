package org.example;

import by.jcompany.bonus_system.entity.*;
import by.jcompany.bonus_system.service.EmployeeService;
import by.jcompany.bonus_system.service.Service;
import by.jcompany.bonus_system.service.TaskService;
import by.jcompany.bonus_system.service.UserService;
import by.jcompany.bonus_system.util.HashManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.fail;

public class JsonParserTest {
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(Instant.class,
            (JsonDeserializer<Instant>) (json, type, jsonDeserializationContext) ->
                ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toInstant())
        .create();
    
    UserService userService = new UserService();
    Service<Employee, Integer> employeeService = new EmployeeService();
    Service<Task, Integer> taskService = new TaskService();
    
    // TODO не забудь
    @Test
    void UserToJson() {
        try {
            User user = new User("login", HashManager.getHash("password"),
                new Role("ADMIN"));
            Employee employee = new Employee("First Name", "Second Name");
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
            
            System.out.println(user);
            System.out.println(gson.toJson(user));
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }
}
