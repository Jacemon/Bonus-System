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
    
    // Common CRUD
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
            fail("Failed to make CRUD user operations.");
        }
    }
    
    @Test
    void CrudEmployee() {
        try {
            String name = "testEmpl" + getRandomNumberString();
            Employee employee = new Employee(name, name);
            if (!employeeService.create(employee)) {
                throw new Exception("Cannot create employee " + name + " !");
            }
            
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
            fail("Failed to make CRUD employee operations.");
        }
    }
    
    // CRUD for Role don't needed
    
    @Test
    void CrudTask() {
        try {
            String desc = "testTask" + getRandomNumberString();
            Task task = new Task(desc, new Bonus(Bonus.BonusType.MONEY, 1.0f));
            if (!taskService.create(task)) {
                throw new Exception("Cannot create task " + desc + " !");
            }
            
            System.out.println(task);
            
            String newDesc = "testTask" + getRandomNumberString();
            task.setDescription(newDesc);
            task.setCompleted(true);
            
            if (!taskService.update(task)) {
                throw new Exception("Cannot update task!");
            }
            
            System.out.println(task);
            
            if (!taskService.delete(task)) {
                throw new Exception("Cannot delete task!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to make CRUD task operations.");
        }
    }
    
    @Test
    void CrudBonus() {
        try {
            Float amount = new Random().nextFloat() * 10;
            Task task = new Task("testBonsTask" + getRandomNumberString());
            taskService.create(task);
            
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
            if (!taskService.delete(task)) {
                throw new Exception("Cannot delete bonus!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to make CRUD bonus operations.");
        }
    }
    
    // Composite CRUD
    
    @Test
    void CrudUserWithEmployee() {
        try {
            String login = "testUser" + getRandomNumberString();
            String name = "testEmpl" + getRandomNumberString();
            String name2 = "testEmpl" + getRandomNumberString();
            
            User user = new User(login, HashManager.getHash(login));
            Employee employee = new Employee(name, name);
            Employee employee2 = new Employee(name2, name2);
            user.setEmployee(employee);
            
            if (!userService.create(user)) {
                throw new Exception("Cannot create user " + login + " !");
            }
            System.out.println(user);
            System.out.println(user.getEmployee());
            
            user.setEmployee(employee2);
            if (!userService.update(user)) {
                throw new Exception("Cannot create user " + login + " !");
            }
            
            System.out.println(user);
            System.out.println(user.getEmployee());
    
            if (!userService.delete(user)) {
                throw new Exception("Cannot delete user!");
            }
            if (!employeeService.delete(employee)) {
                throw new Exception("Cannot delete employee!");
            }
            if (!employeeService.delete(employee2)) {
                throw new Exception("Cannot delete employee!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to make new User with new Employee.");
        }
    }
    
    @Test
    void CrudEmployeeWithTask() {
        try {
            String desc = "testTask" + getRandomNumberString();
            String desc2 = "testTask" + getRandomNumberString();
            String name = "testEmpl" + getRandomNumberString();
            
            Task task = new Task(desc);
            Employee employee = new Employee(name, name);
            task.setEmployee(employee);
            
            task.setBonus(new Bonus(Bonus.BonusType.POINTS, 100.0f));
            
            if (!employeeService.create(employee)) {
                throw new Exception("Cannot create employee " + name + " !");
            }
            
            Task task2 = new Task(desc2);
            task2.setBonus(new Bonus(Bonus.BonusType.POINTS, 100.0f));
            if (!taskService.create(task2)) {
                throw new Exception("Cannot create task " + desc2 + " !");
            }
            if (!taskService.create(task)) {
                throw new Exception("Cannot create task " + desc + " !");
            }
            
            task.setEmployee(null);
            task2.setEmployee(employee);
    
            System.out.println(task);
            System.out.println(task2);
    
            if (!taskService.update(task)) {
                throw new Exception("Cannot update task " + desc + " !");
            }
            if (!taskService.update(task2)) {
                throw new Exception("Cannot update task " + desc2 + " !");
            }
            
            task.setBonus(new Bonus(Bonus.BonusType.MONEY, 10.0f));
            if (!taskService.update(task)) {
                throw new Exception("Cannot update task " + desc + " !");
            }
            
            if (!employeeService.delete(employee)) {
                throw new Exception("Cannot delete employee!");
            }
    
            if (!taskService.delete(task) || !taskService.delete(task2)) {
                throw new Exception("Cannot delete tasks!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to make new Employee with new Tasks.");
        }
    }
    
    @Test
    void CrudTaskWithBonus() {
        try {
            String desc = "testTask" + getRandomNumberString();
            String desc2 = "testTask" + getRandomNumberString();
            Task task = new Task(desc, new Bonus(Bonus.BonusType.POINTS, 10.0f));
            Task task2 = new Task(desc2, new Bonus(Bonus.BonusType.POINTS, 100.0f));

            if (!taskService.create(task)) {
                throw new Exception("Cannot create task " + desc + " !");
            }
            
            Bonus bonus = new Bonus(Bonus.BonusType.MONEY, 100.0f);
            bonus.setTask(task);
    
            System.out.println(task);
            System.out.println(task2);
            
            if (!bonusService.create(bonus)) {
                throw new Exception("Cannot create bonus!");
            }
            bonus = bonusService.read(bonus.getId());
            bonus.setTask(task2);
            if (!bonusService.update(bonus)) {
                throw new Exception("Cannot update bonus!");
            }
            
            System.out.println(taskService.read(task.getId()));
            System.out.println(taskService.read(task2.getId()));
            
            if (!taskService.delete(task) || !taskService.delete(task2)) {
                throw new Exception("Cannot delete tasks!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to make new Employee with new Tasks.");
        }
    }
}
