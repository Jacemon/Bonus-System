package by.jcompany.bonus_system.boot.server.function;

import by.jcompany.bonus_system.entity.Employee;
import by.jcompany.bonus_system.entity.Task;
import by.jcompany.bonus_system.service.RoleService;
import by.jcompany.bonus_system.service.Service;
import by.jcompany.bonus_system.service.UserService;

public abstract class Functions {
    static final UserService userService = new UserService();
    static final RoleService roleService = new RoleService();
    static final Service<Employee, Integer> employeeService = new Service<>();
    static final Service<Task, Integer> taskService = new Service<>();
}
