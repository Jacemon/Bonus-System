package by.jcompany.bonus_system.boot.server.init;

import by.jcompany.bonus_system.boot.server.function.*;
import by.jcompany.bonus_system.entity.Employee;
import by.jcompany.bonus_system.entity.Role;
import by.jcompany.bonus_system.entity.Task;
import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import by.jcompany.bonus_system.model.dto.RoleDto;
import by.jcompany.bonus_system.model.dto.TaskDto;
import by.jcompany.bonus_system.model.dto.UserDto;
import by.jcompany.bonus_system.util.CommandManager;
import by.jcompany.bonus_system.util.CommandManager.ClientRequestString;
import by.jcompany.bonus_system.util.json.GsonManager;
import com.google.gson.Gson;

public class InitCommands {
    private static final Gson gson = GsonManager.getGson();
    
    public static void create() {
        // General
        CommandManager.addCommand("LOGIN", new CommandManager.ServerCommand(
            null,
            (ClientRequestString clientRequestString) -> {
                User user = gson.fromJson(clientRequestString.requestString, User.class);
                user = GeneralFunctions.login(user, clientRequestString.client);
                if (user != null) {
                    return new UserDto(user);
                }
                throw new IllegalArgumentException("Login or password is incorrect");
            }
        ));
        CommandManager.addCommand("LOGOUT", new CommandManager.ServerCommand(
            null,
            (ClientRequestString clientRequestString) -> GeneralFunctions.logout(clientRequestString.client)
        ));
        CommandManager.addCommand("QUIT", new CommandManager.ServerCommand(
            null,
            (ClientRequestString clientRequestString) -> {
                GeneralFunctions.quit(clientRequestString.client);
                return null;
            }
        ));
        // User
        CommandManager.addCommand("CREATE_USER", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                User user = gson.fromJson(clientRequestString.requestString, User.class);
                if (UserFunctions.createUser(user)) {
                    return "User created";
                }
                throw new RuntimeException("User not created");
            }
        ));
        CommandManager.addCommand("READ_ALL_USERS", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> UserFunctions.readAllUsers().stream()
                .map(UserDto::new).toList()
        ));
        CommandManager.addCommand("UPDATE_USER", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                User user = gson.fromJson(clientRequestString.requestString, User.class);
                if (UserFunctions.updateUser(user)) {
                    return "User updated";
                }
                throw new RuntimeException("User not updated");
            }
        ));
        CommandManager.addCommand("UPDATE_USER_BY_EMPLOYEE", new CommandManager.ServerCommand(
            new Role("COMMON"),
            (ClientRequestString clientRequestString) -> {
                UserDto.UserPair userPair = gson.fromJson(clientRequestString.requestString, UserDto.UserPair.class);
                User user = UserFunctions.updateUserLoginInfo(userPair.getUser(), userPair.getPasswordHash(),
                    clientRequestString.client);
                if (user != null) {
                    return new UserDto(user);
                }
                throw new IllegalArgumentException("Profile not updated");
            }
        ));
        CommandManager.addCommand("DELETE_USER", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                Integer userId = gson.fromJson(clientRequestString.requestString, Integer.class);
                if (UserFunctions.deleteUser(userId)) {
                    return "User deleted";
                }
                throw new RuntimeException("User not deleted");
            }
        ));
        CommandManager.addCommand("SET_USER_EMPLOYEE", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                Integer[] userAndEmployeeId = gson.fromJson(clientRequestString.requestString, Integer[].class);
                if (UserFunctions.changeUserEmployee(userAndEmployeeId[0], userAndEmployeeId[1])) {
                    return "Employee was set to user";
                }
                throw new RuntimeException("Employee was not set to user");
            }
        ));
        // Role
        CommandManager.addCommand("CREATE_ROLE", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                Role role = gson.fromJson(clientRequestString.requestString, Role.class);
                if (RoleFunctions.createRole(role)) {
                    return "Role created";
                }
                throw new RuntimeException("Role not created");
            }
        ));
        CommandManager.addCommand("READ_ALL_ROLES", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> RoleFunctions.readAllRoles().stream()
                .map(RoleDto::new).toList()
        ));
        CommandManager.addCommand("UPDATE_ROLE", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                Role role = gson.fromJson(clientRequestString.requestString, Role.class);
                if (RoleFunctions.updateRole(role)) {
                    return "Role updated";
                }
                throw new RuntimeException("Role not updated");
            }
        ));
        CommandManager.addCommand("DELETE_ROLE", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                String roleName = gson.fromJson(clientRequestString.requestString, String.class);
                if (RoleFunctions.deleteRole(roleName)) {
                    return "Role deleted";
                }
                throw new RuntimeException("Role not deleted");
            }
        ));
        // Employee
        CommandManager.addCommand("CREATE_EMPLOYEE", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                Employee employee = gson.fromJson(clientRequestString.requestString, Employee.class);
                if (EmployeeFunctions.createEmployee(employee)) {
                    return "Employee created";
                }
                throw new RuntimeException("Employee not created");
            }
        ));
        CommandManager.addCommand("READ_ALL_EMPLOYEES", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> EmployeeFunctions.readAllEmployees().stream()
                .map(EmployeeDto::new).toList()
        ));
        CommandManager.addCommand("UPDATE_EMPLOYEE", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                Employee employee = gson.fromJson(clientRequestString.requestString, Employee.class);
                if (EmployeeFunctions.updateEmployee(employee)) {
                    return "Employee updated";
                }
                throw new RuntimeException("Employee not updated");
            }
        ));
        CommandManager.addCommand("DELETE_EMPLOYEE", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                Integer employeeId = gson.fromJson(clientRequestString.requestString, Integer.class);
                if (EmployeeFunctions.deleteEmployee(employeeId)) {
                    return "Employee deleted";
                }
                throw new RuntimeException("Employee not deleted");
            }
        ));
        CommandManager.addCommand("CALCULATE_BONUSES", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                Integer employeeId = gson.fromJson(clientRequestString.requestString, Integer.class);
                if (employeeId != null) {
                    return EmployeeFunctions.calculateEmployeeBonuses(employeeId, true);
                } else {
                    return EmployeeFunctions.calculateEmployeeBonusesForAll(true);
                }
            }
        ));
        CommandManager.addCommand("PAY_BONUSES", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                Integer employeeId = gson.fromJson(clientRequestString.requestString, Integer.class);
                if (employeeId != null) {
                    return EmployeeFunctions.payEmployeeBonuses(employeeId, true);
                } else {
                    return EmployeeFunctions.payEmployeeBonusesForAll(true);
                }
            }
        ));
        CommandManager.addCommand("CALCULATE_BONUSES_BY_EMPLOYEE", new CommandManager.ServerCommand(
            new Role("COMMON"),
            (ClientRequestString clientRequestString) -> EmployeeFunctions.calculateEmployeeBonuses(
                clientRequestString.client.getClientUser().getEmployee().getId(), true)
        ));
        // Task
        CommandManager.addCommand("CREATE_TASK", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                Task task = gson.fromJson(clientRequestString.requestString, Task.class);
                if (TaskFunctions.createTask(task)) {
                    return "Task created";
                }
                throw new RuntimeException("Task not created");
            }
        ));
        CommandManager.addCommand("READ_ALL_TASKS", new CommandManager.ServerCommand(
            new Role("COMMON"),
            (ClientRequestString clientRequestString) -> TaskFunctions.readAllTasks().stream()
                .map(TaskDto::new).toList()
        ));
        CommandManager.addCommand("UPDATE_TASK", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                Task task = gson.fromJson(clientRequestString.requestString, Task.class);
                if (TaskFunctions.updateTask(task)) {
                    return "Task updated";
                }
                throw new RuntimeException("Task not updated");
            }
        ));
        CommandManager.addCommand("DELETE_TASK", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                Integer taskId = gson.fromJson(clientRequestString.requestString, Integer.class);
                if (TaskFunctions.deleteTask(taskId)) {
                    return "Task deleted";
                }
                throw new RuntimeException("Task not deleted");
            }
        ));
        CommandManager.addCommand("SET_POINT_COST", new CommandManager.ServerCommand(
            new Role("ADMIN"),
            (ClientRequestString clientRequestString) -> {
                Float pointCost = gson.fromJson(clientRequestString.requestString, Float.class);
                if (TaskFunctions.setPointCost(pointCost)) {
                    return "Point was set";
                }
                throw new RuntimeException("Point was not set");
            }
        ));
        CommandManager.addCommand("GET_POINT_COST", new CommandManager.ServerCommand(
            new Role("COMMON"),
            (ClientRequestString clientRequestString) -> TaskFunctions.getPointCost()
        ));
        CommandManager.addCommand("SET_TASK_COMPLETED", new CommandManager.ServerCommand(
            new Role("COMMON"),
            (ClientRequestString clientRequestString) -> {
                Integer taskId = gson.fromJson(clientRequestString.requestString, Integer.class);
                if (clientRequestString.client.getClientUser().getEmployee() != null &&
                    TaskFunctions.setTaskCompleted(taskId,
                        clientRequestString.client.getClientUser().getEmployee().getId())) {
                    return "Task was set to completed";
                }
                throw new RuntimeException("Task was not set to completed");
            }
        ));
        CommandManager.addCommand("SET_TASK_TO_EMPLOYEE", new CommandManager.ServerCommand(
            new Role("COMMON"),
            (ClientRequestString clientRequestString) -> {
                Integer taskId = gson.fromJson(clientRequestString.requestString, Integer.class);
                if (TaskFunctions.setTaskToEmployee(taskId,
                    clientRequestString.client.getClientUser().getEmployee().getId())) {
                    return "Task was set to employee";
                }
                throw new RuntimeException("Task was not set to employee");
            }
        ));
        CommandManager.addCommand("UNSET_TASK_FROM_EMPLOYEE", new CommandManager.ServerCommand(
            new Role("COMMON"),
            (ClientRequestString clientRequestString) -> {
                Integer taskId = gson.fromJson(clientRequestString.requestString, Integer.class);
                if (TaskFunctions.unsetTaskFromEmployee(taskId,
                    clientRequestString.client.getClientUser().getEmployee().getId())) {
                    return "Task was unset to employee";
                }
                throw new RuntimeException("Task was not unset to employee");
            }
        ));
    }
}
