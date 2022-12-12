package by.jcompany;

import by.jcompany.bonus_system.entity.*;
import by.jcompany.bonus_system.service.*;
import by.jcompany.bonus_system.util.HashManager;
import by.jcompany.bonus_system.util.HibernateSessionFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.fail;

public class CrudTest {
    static {
        HibernateSessionFactory.getSessionFactory();
    }
    
    UserService userService = new UserService();
    RoleService roleService = new RoleService();
    Service<Employee, Integer> employeeService = new EmployeeService();
    Service<Task, Integer> taskService = new TaskService();
    
    String getRandomNumberString() {
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();
        
        for (int i = 0; i < 6; i++) {
            randomString.append(random.nextInt(9));
        }
        return randomString.toString();
    }
    
    //-- Correct CRUD operations
    
    // Common CRUD
    @Test
    void ReadUserByLogin() {
        System.out.println("------------ReadUserByLogin------------");
        try {
            String login = "testUser" + getRandomNumberString();
            User user = new User(login, HashManager.getHash(login));
            if (!userService.create(user)) {
                throw new Exception("Cannot create user " + login + " !");
            }
            System.out.println(user);
            
            user = userService.read(login);
            System.out.println(user);
            
            if (!userService.delete(user)) {
                throw new Exception("Cannot delete user " + login + " !");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to make CRUD employee operations.");
        }
    }
    
    @Test
    void CrudUser() {
        System.out.println("------------CrudUser------------");
        try {
            String login = "testUser" + getRandomNumberString();
            User user = new User(login, HashManager.getHash(login));
            if (!userService.create(user)) {
                throw new Exception("Cannot create user " + login + " !");
            }
            System.out.println(user);
            
            user = userService.read(user.getId());
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
    void UpdateUserRole() {
        System.out.println("------------UpdateUserRole------------");
        try {
            String login = "testUser" + getRandomNumberString();
            User user = new User(login, HashManager.getHash(login));
            if (!userService.create(user)) {
                throw new Exception("Cannot create user " + login + " !");
            }
            System.out.println(user);
            
            user.setRole(new Role("ADMIN", 2));
            System.out.println(user);
            
            if (!userService.update(user)) {
                throw new Exception("Cannot update user!");
            }
            
            user = userService.read(user.getLogin());
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
            System.out.println("------------CrudEmployee------------");
            String name = "testEmpl" + getRandomNumberString();
            Employee employee = new Employee(name, name, 1000.0f);
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
    void CreateAndDeleteTask() {
        try {
            System.out.println("------------CreateAndDeleteTask------------");
            String desc = "testTask" + getRandomNumberString();
            Task task = new Task(desc, new Bonus(Bonus.BonusType.MONEY, 1.0f));
            
            if (!taskService.create(task)) {
                throw new Exception("Cannot create task " + desc + " !");
            }
            
            System.out.println(task);
            
            String newDesc = "testTask" + getRandomNumberString();
            task.setDescription(newDesc);
            task.setCompleted(true);
            task.setCreationTime(new Date(0).toInstant());
            
            if (!taskService.update(task)) {
                throw new Exception("Cannot update task to true!");
            }
            System.out.println(task);
            
            if (!task.isCompleted()) {
                throw new Exception("Incorrect completed false!");
            }
            if (!task.getCreationTime().equals(new Date(0).toInstant())) {
                throw new Exception("Incorrect completed false!");
            }
            
            task.setCompleted(false);
            if (!taskService.update(task)) {
                throw new Exception("Cannot update task to false!");
            }
            System.out.println(task);
            
            if (!taskService.delete(task)) {
                throw new Exception("Cannot delete task!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to make CD task operations.");
        }
    }
    
    @Test
    void UpdateTaskWithDescriptionAndDefaultCreationTimeAndIsCompleted() {
        try {
            System.out.println("------------UpdateTaskWithDescriptionAndDefaultCreationTimeAndIsCompleted------------");
            String desc = "testTask" + getRandomNumberString();
            Task task = new Task(desc, new Bonus(Bonus.BonusType.MONEY, 1.0f));
            
            if (!taskService.create(task)) {
                throw new Exception("Cannot create task " + desc + " !");
            }
            System.out.println(task);
            
            task.setCreationTime(new Date(0).toInstant());
            task.setCompleted(true);
            String newDesc = "testTask" + getRandomNumberString();
            task.setDescription(newDesc);
            if (!taskService.update(task)) {
                throw new Exception("Cannot create task " + desc + " !");
            }
            
            if (!task.getDescription().equals(newDesc)) {
                throw new Exception("Creation time is not true!");
            }
            if (!task.getCreationTime().equals(new Date(0).toInstant())) {
                throw new Exception("Creation time is not true!");
            }
            if (!task.isCompleted()) {
                throw new Exception("Completed is not true!");
            }
            
            if (!taskService.delete(task)) {
                throw new Exception("Cannot delete task!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to make composite update task operations.");
        }
    }
    
    /**
     * CRUD for bonus task don't exist and all operations with bonuses
     * can be done using bonuses CRUD
     */
    
    @Disabled
    @Test
    void CreateTaskWithDefaultCreationTimeAndIsCompleted() {
        try {
            System.out.println("------------CreateTaskWithDefaultCreationTimeAndIsCompleted------------");
            String desc = "testTask" + getRandomNumberString();
            Task task = new Task(desc, new Bonus(Bonus.BonusType.MONEY, 1.0f));
            task.setCreationTime(new Date(0).toInstant());
            task.setCompleted(true);
            
            if (!taskService.create(task)) {
                throw new Exception("Cannot create task " + desc + " !");
            }
            System.out.println(task);
            if (!task.getCreationTime().equals(new Date(0).toInstant())) {
                throw new Exception("Creation time is not true!");
            }
            if (!task.isCompleted()) {
                throw new Exception("Completed is not true!");
            }
            
            if (!taskService.delete(task)) {
                throw new Exception("Cannot delete task!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to make composite create task operation.");
        }
    }
    
    // Composite CRUD
    
    
    @Test
    void CrudEmployeeWithUser() {
        try {
            System.out.println("------------CrudEmployeeWithUser------------");
            String login = "testUser" + getRandomNumberString();
            String name = "testEmpl" + getRandomNumberString();
            Employee employee = new Employee(name, name, 1000.0f);
            User user = new User(login, HashManager.getHash(login));
            employee.setUser(user);
            
            if (!employeeService.create(employee)) {
                throw new Exception("Cannot create employee " + name + " with new User!");
            }
            if (!userService.create(user)) {
                throw new Exception("Not correct to create employee " + name + " with new User!");
            }
            employee.setUser(user); // it's persisted here
            if (!employeeService.update(employee)) {
                throw new Exception("Cannot create employee " + name + " with new User!");
            }
            if (employeeService.read(employee.getId()).getUser() != null) {
                throw new Exception("Not correct to update employee " + name + " with new User!");
            }
            
            if (!userService.delete(user)) {
                throw new Exception("Cannot delete user!");
            }
            if (!employeeService.delete(employee)) {
                throw new Exception("Cannot delete employee!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to make new User with new Employee.");
        }
    }
    
    /**
     * Проверяет создание Пользователя и добавление ему Работника.
     * 4 типа:
     * - совместное создание юзера и работника через userService
     * - раздельное создание и установка юзеру работника
     * - создание юзера с уже созданным работником
     * - создание работника через обновление созданного юзера
     */
    @Test
    void CrudUserWithEmployee() {
        try {
            System.out.println("------------CreateUserWithEmployee------------");
            String login = "testUser" + getRandomNumberString();
            String login2 = "testUserS" + getRandomNumberString();
            String name = "testEmpl" + getRandomNumberString();
            String name2 = "testEmplS" + getRandomNumberString();
            
            User user = new User(login, HashManager.getHash(login));
            User user2 = new User(login2, HashManager.getHash(login2));
            Employee employee = new Employee(name, name, 1000.0f);
            Employee employee2 = new Employee(name2, name2, 1000.0f);
            user.setEmployee(employee);
            
            if (!userService.create(user)) {
                throw new Exception("Cannot create user " + login + " with new Employee!");
            }
            System.out.println(user);
            System.out.println(user.getEmployee());
            
            user.setEmployee(employee2);
            if (!userService.update(user)) {
                throw new Exception("Cannot update user " + login + " with new Employee!");
            }
            System.out.println(user);
            System.out.println(user.getEmployee());
            
            Employee employee3 = new Employee(name, name, 1000.0f);
            Employee employee4 = new Employee(name2, name2, 1000.0f);
            if (!employeeService.create(employee3)) {
                throw new Exception("Cannot create employee!");
            }
            if (!employeeService.create(employee4)) {
                throw new Exception("Cannot create employee!");
            }
            
            user2.setEmployee(employee3);
            if (!userService.create(user2)) {
                throw new Exception("Cannot create user " + login + " !");
            }
            System.out.println(user2);
            System.out.println(user2.getEmployee());
            if (user2.getEmployee() == null) {
                throw new Exception("Cannot create user " + login + " with old Employee!");
            }
            
            user2.setEmployee(employee4);
            if (!userService.update(user2)) {
                throw new Exception("Cannot update user " + login + " !");
            }
            System.out.println(user2);
            System.out.println(user2.getEmployee());
            if (user2.getEmployee() == null) {
                throw new Exception("Cannot update user " + login + " with old Employee!");
            }
            
            if (userService.read(user.getId()).getEmployee() == null) {
                throw new Exception("Incorrect user without employee!");
            }
            if (userService.read(user2.getId()).getEmployee() == null) {
                throw new Exception("Incorrect user without employee 2!");
            }
            
            if (!employeeService.delete(employee)) {
                throw new Exception("Cannot delete employee!");
            }
            if (!userService.delete(user)) {
                throw new Exception("Cannot delete user!");
            }
            if (!employeeService.delete(employee2)) {
                throw new Exception("Cannot delete employee 2!");
            }
            if (!employeeService.delete(employee3)) {
                throw new Exception("Cannot delete employee 3!");
            }
            if (!userService.delete(user2)) {
                throw new Exception("Cannot delete user 2!");
            }
            if (!employeeService.delete(employee4)) {
                throw new Exception("Cannot delete employee 4!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to make new User with new Employee.");
        }
    }
    
    @Test
    void DeleteEmployeeWithTasks() {
        try {
            System.out.println("------------DeleteEmployeeWithUser------------");
            String desc = "testTask" + getRandomNumberString();
            String name = "testEmpl" + getRandomNumberString();
            
            Employee employee = new Employee(name, name, 1000.0f);
            Task task = new Task(desc, new Bonus(Bonus.BonusType.POINTS, 100.0f));
            Task task2 = new Task(desc, new Bonus(Bonus.BonusType.POINTS, 100.0f));
            
            if (!employeeService.create(employee)) {
                throw new Exception("Cannot create employee " + name + " !");
            }
            
            task.setEmployee(employee);
            task2.setEmployee(employee);
            
            if (!taskService.create(task)) {
                throw new Exception("Cannot create task " + desc + " !");
            }
            if (!taskService.create(task2)) {
                throw new Exception("Cannot create task " + desc + " !");
            }
            System.out.println(task);
            System.out.println(task2);
            
            if (!taskService.delete(task)) {
                throw new Exception("Cannot delete task!");
            }
            if (!(employeeService.read(employee.getId()).getTasks().size() == 1)) {
                throw new Exception("Incorrect deleting task!");
            }
            if (!employeeService.delete(employee)) {
                throw new Exception("Cannot delete employee!");
            }
            if (!taskService.delete(task2)) {
                throw new Exception("Cannot delete task!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to make new Employee with new Tasks.");
        }
    }
    
    @Test
    void CrudEmployeeWithTaskWithBonus() {
        try {
            System.out.println("------------CrudEmployeeWithTaskWithBonus------------");
            String desc = "testTask" + getRandomNumberString();
            String name = "testEmpl" + getRandomNumberString();
            
            Employee employee = new Employee(name, name, 1000.0f);
            Task task = new Task(desc, new Bonus(Bonus.BonusType.POINTS, 100.0f));
            
            if (!employeeService.create(employee)) {
                throw new Exception("Cannot create employee " + name + " !");
            }
            
            task.setEmployee(employee);
            
            if (!taskService.create(task)) {
                throw new Exception("Cannot create task " + desc + " !");
            }
            System.out.println(task);
            
            task.setEmployee(null);
            
            if (!taskService.update(task)) {
                throw new Exception("Cannot update task " + desc + " !");
            }
            System.out.println(task);
            
            task.setBonus(new Bonus(Bonus.BonusType.MONEY, 10.0f));
            task.setEmployee(employee);
            if (!taskService.update(task)) {
                throw new Exception("Cannot update task " + desc + " !");
            }
            System.out.println(task);
            
            if (!taskService.delete(task)) {
                throw new Exception("Cannot delete task!");
            }
            if (!employeeService.delete(employee)) {
                throw new Exception("Cannot delete employee!");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail(exception.getMessage());
        }
    }
    
    //-- Errors
    
    @Test
    void ReadNotExistedRole() {
        System.out.println("------------ReadNotExistedRole------------");
        try {
            if (roleService.read("null") == null) {
                throw new CorrectException("Read null Role");
            }
        } catch (CorrectException ignored) {
        } catch (Exception exception) {
            exception.printStackTrace();
            fail(exception.getMessage());
        }
    }
    
    @Test
    void CreateDuplicateUser() {
        System.out.println("------------CreateDuplicateUser------------");
        String login = "testUser" + getRandomNumberString();
        User user = new User(login, HashManager.getHash(login));
        User user2 = new User(login, HashManager.getHash(login));
        try {
            
            if (!userService.create(user)) {
                throw new Exception("Cannot create new user " + login + "!");
            }
            if (!userService.create(user2)) {
                throw new CorrectException("Cannot create duplicate user " + login + "!");
            }
            throw new Exception("Duplicate user was created!");
        } catch (CorrectException exception) {
            userService.delete(user);
        } catch (Exception exception) {
            exception.printStackTrace();
            fail(exception.getMessage());
        }
    }
    
    @Test
    void CreateUserWithId() {
        System.out.println("------------CreateUserWithId------------");
        try {
            String login = "testUser" + getRandomNumberString();
            User user = new User(login, HashManager.getHash(login));
            user.setId(999);
            
            if (!userService.create(user)) {
                throw new CorrectException("Cannot create user with id!");
            }
            throw new Exception("User with id was created!");
        } catch (CorrectException ignored) {
        } catch (Exception exception) {
            exception.printStackTrace();
            fail(exception.getMessage());
        }
    }
    
    @Test
    void CreateUserWithNotExistingRole() {
        System.out.println("------------CreateUserWithNotExistingRole------------");
        try {
            String login = "testUser" + getRandomNumberString();
            User user = new User(login, HashManager.getHash(login), new Role("SUPER_ROLE", 0));
            
            if (!userService.create(user)) {
                throw new CorrectException("Cannot create user with not existed role!");
            }
            throw new Exception("User with not existing was created!");
        } catch (CorrectException ignored) {
        } catch (Exception exception) {
            exception.printStackTrace();
            fail(exception.getMessage());
        }
    }
    
    @Test
    void UpdateNotExistingTask() {
        System.out.println("------------UpdateNotExistingTask------------");
        String desk = "testTask" + getRandomNumberString();
        Task task = new Task(desk, new Bonus(Bonus.BonusType.MONEY, 12.0f));
        try {
            taskService.create(task);
            task.setId(task.getId() + 1);
            if (!taskService.update(task)) {
                throw new CorrectException("Cannot update not existing task!");
            }
            throw new Exception("Not existing task was update!");
        } catch (CorrectException ignored) {
            task.setId(task.getId() - 1);
            taskService.delete(task);
        } catch (Exception exception) {
            exception.printStackTrace();
            fail(exception.getMessage());
        }
    }
    
    @Test
    void SetNegativeTaskPointCost() {
        System.out.println("------------SetNegativeTaskPointCost------------");
        try {
            System.out.println(Task.getPointCost());
            Task.setPointCost(-100.0f);
            System.out.println(Task.getPointCost());
            if (Task.getPointCost() != null) {
                throw new Exception("Set negative point cost!");
            }
            Task.setPointCost(100.0f);
            System.out.println(Task.getPointCost());
            Task.setPointCost(null);
            System.out.println(Task.getPointCost());
        } catch (Exception exception) {
            exception.printStackTrace();
            fail(exception.getMessage());
        }
    }
}
