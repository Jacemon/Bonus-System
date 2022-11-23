module by.jcompany.bonus_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires lombok;
    
    exports by.jcompany.bonus_system.temp;
    opens by.jcompany.bonus_system.temp to javafx.fxml;
    exports by.jcompany.bonus_system.dto;
    opens by.jcompany.bonus_system.dto to com.google.gson;
    exports by.jcompany.bonus_system.model;
    opens by.jcompany.bonus_system.model to javafx.fxml;
    exports by.jcompany.bonus_system.util;
    opens by.jcompany.bonus_system.util to javafx.fxml;
    exports by.jcompany.bonus_system.boot;
    opens by.jcompany.bonus_system.boot to javafx.fxml;
    exports by.jcompany.bonus_system.menu.function;
    opens by.jcompany.bonus_system.menu.function to javafx.fxml;
}