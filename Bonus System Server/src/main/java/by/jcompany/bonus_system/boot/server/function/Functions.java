package by.jcompany.bonus_system.boot.server.function;

import by.jcompany.bonus_system.service.EmployeeService;
import by.jcompany.bonus_system.service.RoleService;
import by.jcompany.bonus_system.service.TaskService;
import by.jcompany.bonus_system.service.UserService;

public abstract class Functions {
    static final UserService userService = new UserService();
    static final RoleService roleService = new RoleService();
    static final EmployeeService employeeService = new EmployeeService();
    static final TaskService taskService = new TaskService();
}
