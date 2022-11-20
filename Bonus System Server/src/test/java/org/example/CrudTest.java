package org.example;

import by.jcompany.bonus_system.entity.Bonus;
import by.jcompany.bonus_system.entity.Employee;
import by.jcompany.bonus_system.entity.Task;
import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.service.*;
import by.jcompany.bonus_system.util.HashManager;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.fail;

public class CrudTest {
    Service<User, String> userService = new UserService();
    Service<Employee, Integer> employeeService = new EmployeeService();
    Service<Task, Integer> taskService = new TaskService();
    Service<Bonus, Integer> bonusService = new BonusService();
    
    String getRandomNumberString() {
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();
    
        for (int i = 0; i < 6; i++) {
            randomString.append(random.nextInt(9));
        }
        return randomString.toString();
    }
    
    // Correct CRUD operations
    @Test
    void CrudUser() {
        try {
            String login = "testUser" + getRandomNumberString();
            if (!userService.create(new User(login, HashManager.getHash(login)))) {
                throw new Exception("Cannot create user " + login + " !");
            }
            
            User user = userService.read(login);
            System.out.println(user);
    
            String newLogin = "testUser" + getRandomNumberString();
            user.setLogin(newLogin);
            user.setPasswordHash(HashManager.getHash(newLogin));
    
            if (!userService.update(user)) {
                throw new Exception("Cannot update user!");
            }
    
            System.out.println(user);
            
            if (!userService.delete(user)) {
                throw new Exception("Cannot delete user!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to made CRUD user operations.");
        }
    }
    
    @Test
    void CrudEmployee() {
        try {
            String name = "testEmpl" + getRandomNumberString();
            if (!employeeService.create(new Employee(name, name))) {
                throw new Exception("Cannot create employee " + name + " !");
            }
            
            Employee employee = employeeService.read(employeeService.readAll().get(0).getId());
            System.out.println(employee);
            
            String newName = "testEmpl" + getRandomNumberString();
            employee.setFirstName(newName);
            employee.setLastName(newName);
            
            if (!employeeService.update(employee)) {
                throw new Exception("Cannot update employee!");
            }
            
            System.out.println(employee);
            
            if (!employeeService.delete(employee)) {
                throw new Exception("Cannot delete employee!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to made CRUD employee operations.");
        }
    }
    
    // CRUD for Role don't needed
    
    @Test
    void CrudTask() {
        try {
            String desc = "testTask" + getRandomNumberString();
            if (!taskService.create(new Task(desc))) {
                throw new Exception("Cannot create task " + desc + " !");
            }
            
            Task task = taskService.read(taskService.readAll().get(0).getId());
            System.out.println(task);
            
            String newDesc = "testTask" + getRandomNumberString();
            task.setDescription(newDesc);
            task.setStatus(Task.Status.COMPLETED);
            
            if (!taskService.update(task)) {
                throw new Exception("Cannot update task!");
            }
            
            System.out.println(task);
            
            if (!taskService.delete(task)) {
                throw new Exception("Cannot delete task!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to made CRUD task operations.");
        }
    }
    
    @Test
    void CrudBonus() {
        try {
            Float amount = new Random().nextFloat() * 10;
            taskService.create(new Task("testBonsTask" + getRandomNumberString()));
            
            Bonus bonus = new Bonus(Bonus.BonusType.MONEY, amount);
            bonus.setTask(taskService.read(taskService.readAll().get(0).getId()));
            
            if (!bonusService.create(bonus)) {
                throw new Exception("Cannot create bonus " + amount + " !");
            }
            
            bonus = bonusService.read(bonusService.readAll().get(0).getId());
            System.out.println(bonus);
            
            bonus.setAmount(new Random().nextFloat());
            bonus.setType(Bonus.BonusType.POINTS);
            
            if (!bonusService.update(bonus)) {
                throw new Exception("Cannot update bonus!");
            }
            
            System.out.println(bonus);
            
            if (!bonusService.delete(bonus)) {
                throw new Exception("Cannot delete bonus!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to made CRUD bonus operations.");
        }
    }
}
