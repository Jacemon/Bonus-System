package by.jcompany.bonus_system.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Properties;

public class PropertyManager {
    private static final String propertyFile = "server.properties";
    
    public static String getPropertyValue(String propertyName) {
        Properties property = new Properties();
        
        try (FileInputStream fileInputStream = new FileInputStream(new File(Objects.requireNonNull(
            PropertyManager.class.getResource("/" + propertyFile)).toURI()))) {
            property.load(fileInputStream);
            
            return property.getProperty(propertyName);
        } catch (IOException | URISyntaxException exception) {
            exception.printStackTrace();
        }
        return null;
    }
    
    public static void saveTaskPointCost(String propertyName, String value) {
        Properties property = new Properties();
        
        try (FileOutputStream fileOutputStream = new FileOutputStream(new File(Objects.requireNonNull(
            PropertyManager.class.getResource("/" + propertyFile)).toURI()))) {
            property.setProperty(propertyName, value);
            property.store(fileOutputStream, "Store server properties");
        } catch (IOException | URISyntaxException exception) {
            exception.printStackTrace();
        }
    }
}
