package org.example;

import by.jcompany.bonus_system.entity.User;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test for simple App.
 */
public class CRUDTest {
    
    static class TestService extends ServiceA<User, Integer> { }
    
    @Test
    void testDaoImpl() {
        ServiceA<User, Integer> testService = new TestService();
        try {
            Set<User> users = testService.readAll(User.class);
            for (User user : users) {
                System.out.println(user);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            fail("Failed to read users table.");
        }
    }
}
