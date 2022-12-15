package by.jcompany.bonus_system.dao;

import by.jcompany.bonus_system.entity.User;
import by.jcompany.bonus_system.util.HibernateSessionFactory;
import org.hibernate.Session;

public class UserDao extends Dao<User, Integer> {
    public User read(String login) {
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            //return session.bySimpleNaturalId(User.class).load(login);
            return session.createQuery("from User where login = '" + login + '\'', User.class)
                .getResultList().get(0);
        } catch (RuntimeException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
