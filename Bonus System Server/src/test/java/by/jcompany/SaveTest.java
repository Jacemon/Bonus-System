package by.jcompany;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.fail;

public class SaveTest {
    @Test
    void getTaskPointCost() {
        String propFile = "server.properties";
        Properties property = new Properties();
        
        String path = System.getProperty("user.dir") +
            File.separator + "src" +
            File.separator + "main" +
            File.separator + "resources" +
            File.separator + propFile;
        
        try (FileInputStream fis = new FileInputStream(path)) {
            property.load(fis);
            
            Float pointCost = Float.valueOf(property.getProperty("task.pointCost"));
            System.out.println(pointCost);
        } catch (IOException exception) {
            exception.printStackTrace();
            fail();
        }
    }
    
    @Test
    void saveTaskPointCost() {
        String propFile = "server.properties";
        Properties property = new Properties();
        
        String path = System.getProperty("user.dir") +
            File.separator + "src" +
            File.separator + "main" +
            File.separator + "resources" +
            File.separator + propFile;
        
        try (FileOutputStream fos = new FileOutputStream(path)) {
            property.setProperty("task.pointCost", String.valueOf(10.0f));
            property.store(fos, "Store server properties");
        } catch (IOException exception) {
            exception.printStackTrace();
            fail();
        }
    }
}
