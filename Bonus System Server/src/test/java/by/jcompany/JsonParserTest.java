package by.jcompany;

import by.jcompany.bonus_system.entity.*;
import by.jcompany.bonus_system.service.EmployeeService;
import by.jcompany.bonus_system.service.Service;
import by.jcompany.bonus_system.service.TaskService;
import by.jcompany.bonus_system.service.UserService;
import by.jcompany.bonus_system.util.HashManager;
import by.jcompany.bonus_system.util.json.AnnotationExclusionStrategy;
import by.jcompany.bonus_system.util.json.GsonManager;
import com.google.gson.*;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.fail;

public class JsonParserTest {
    Gson gson = GsonManager.getGson();
    
    /*public static class InstantTypeAdapter extends TypeAdapter<Instant> {
        public InstantTypeAdapter() {
        }
        
        @Override
        public void write(JsonWriter out, Instant date) throws IOException {
            if (date == null) {
                out.nullValue();
            } else {
                out.value(String.format(String.valueOf(date)));
            }
        }
        
        @Override
        public Instant read(JsonReader in) throws IOException {
            try {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return null;
                }
                String date = in.nextString();
                if (date == null) {
                    return null;
                }
                return ZonedDateTime.parse(date).toInstant();
            } catch (IllegalArgumentException e) {
                throw new JsonParseException(e);
            }
        }
    }*/
    
    
    /*Gson gson = new GsonBuilder()
        .registerTypeAdapter(Instant.class, new InstantTypeAdapter())
        .create();*/
    
    UserService userService = new UserService();
    Service<Employee, Integer> employeeService = new EmployeeService();
    Service<Task, Integer> taskService = new TaskService();
    
    // TODO не забудь
    @Test
    void UserToJson() {
        try {
            User user = new User("login", HashManager.getHash("password"),
                new Role("ADMIN"));
            Employee employee = new Employee("First Name", "Second Name");
            user.setEmployee(employee);
            userService.create(user);
            
            Task task1 = new Task("Try this to json!", new Bonus(Bonus.BonusType.MONEY, 100.0f));
            Task task2 = new Task("And the other one!", new Bonus(Bonus.BonusType.POINTS, 15.0f));
            task1.setEmployee(employee);
            task2.setEmployee(employee);
            taskService.create(task1);
            taskService.create(task2);
            
            user = userService.read(user.getLogin());
            
            taskService.delete(task1);
            taskService.delete(task2);
            userService.delete(user);
            employeeService.delete(employee);
            
            System.out.println(user);
            System.out.println(gson.toJson(user));
    
            /*String str = gson.toJson(new Date().toInstant());
            System.out.println(str);
            Instant date = gson.fromJson(str, Instant.class);
            System.out.println(date);*/
        } catch (Exception exception) {
            exception.printStackTrace();
            fail();
        }
    }
}
