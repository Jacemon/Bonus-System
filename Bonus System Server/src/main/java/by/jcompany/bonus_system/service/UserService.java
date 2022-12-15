package by.jcompany.bonus_system.service;

import by.jcompany.bonus_system.dao.UserDao;
import by.jcompany.bonus_system.entity.User;

public class UserService extends Service<User, Integer> {
    public User read(String login) {
        return ((UserDao) entityDao).read(login);
    }
}
