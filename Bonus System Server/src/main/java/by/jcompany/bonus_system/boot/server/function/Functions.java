package by.jcompany.bonus_system.boot.server.function;

import by.jcompany.bonus_system.service.EmployeeService;
import by.jcompany.bonus_system.service.RoleService;
import by.jcompany.bonus_system.service.TaskService;
import by.jcompany.bonus_system.service.UserService;
import by.jcompany.bonus_system.util.CommandManager;

import java.util.HashMap;
import java.util.Map;

public abstract class Functions {
    static final Map<String, CommandManager.ServerCommand> functions = new HashMap<>();
    
    static final UserService userService = new UserService();
    static final RoleService roleService = new RoleService();
    static final EmployeeService employeeService = new EmployeeService();
    static final TaskService taskService = new TaskService();
}
