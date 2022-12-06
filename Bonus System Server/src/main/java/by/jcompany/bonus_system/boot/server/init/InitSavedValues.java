package by.jcompany.bonus_system.boot.server.init;

import by.jcompany.bonus_system.entity.Task;
import by.jcompany.bonus_system.util.PropertyManager;
import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class InitSavedValues {
    @Getter
    private static final String taskPointCostProperty = "task.pointCost";
    
    public static void tryInitTaskPointCost() {
        Task.setPointCost(Float.valueOf(
            Objects.requireNonNull(
                PropertyManager.getPropertyValue(taskPointCostProperty))));
    }
    
    public static void trySaveTaskPointCost() {
        PropertyManager.saveTaskPointCost(
            taskPointCostProperty, Task.getPointCost().toString());
    }
}
