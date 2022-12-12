package by.jcompany.bonus_system.boot.server.function;

import by.jcompany.bonus_system.boot.server.ClientHandler;
import by.jcompany.bonus_system.entity.Employee;
import by.jcompany.bonus_system.entity.User;
import com.mysql.cj.conf.ConnectionUrlParser;

import java.util.Arrays;
import java.util.List;

public class UserFunctions extends Functions {
    public static boolean createUser(User user) {
        user.setRole(roleService.read(user.getRole().getName()));
        return userService.create(user);
    }
    
    public static boolean updateUser(User user) {
        user.setRole(roleService.read(user.getRole().getName()));
        if (user.getPasswordHash() == null) {
            user.setPasswordHash(userService.read(user.getId()).getPasswordHash());
        }
        if (user.getEmployee() != null) {
            user.setEmployee(employeeService.read(user.getEmployee().getId()));
        }
        return userService.update(user);
    }
    
    public static User updateUserLoginInfo(User user, byte[] oldPasswordHash, ClientHandler client) {
        user.setRole(userService.read(user.getId()).getRole());
        user.setEmployee(userService.read(user.getId()).getEmployee());
        
        if (user.getLogin() == null) {
            user.setLogin(userService.read(user.getId()).getLogin());
        }

        if (user.getPasswordHash() == null) {
            user.setPasswordHash(userService.read(user.getId()).getPasswordHash());
        } else if (!Arrays.equals(userService.read(user.getId()).getPasswordHash(), oldPasswordHash)) {
            return null;
        }
        if (userService.update(user)) {
            User newUser = userService.read(user.getId());
            client.setClientUser(newUser);
            return newUser;
        }
        return null;
    }
    
    public static List<User> readAllUsers() {
        return userService.readAll();
    }
    
    public static boolean deleteUser(Integer userId) {
        User user = userService.read(userId);
        if (user == null) {
            return false;
        }
        return userService.delete(user);
    }
    
    public static boolean changeUserEmployee(Integer userId, Integer employeeId) {
        User user = userService.read(userId);
        Employee employee = employeeService.read(employeeId);
        if (user != null && employee != null) {
            user.setEmployee(employee);
        }
        return userService.update(user);
    }
}
