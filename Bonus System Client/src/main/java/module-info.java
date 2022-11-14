module by.jcompany.bonus_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires lombok;
    
    opens by.jcompany.bonus_system to javafx.fxml;
    exports by.jcompany.bonus_system;
    exports by.jcompany.bonus_system.temp;
    opens by.jcompany.bonus_system.temp to javafx.fxml;
    
    exports by.jcompany.bonus_system.entity;
    opens by.jcompany.bonus_system.entity to com.google.gson;
    exports by.jcompany.bonus_system.model;
    opens by.jcompany.bonus_system.model to javafx.fxml;
}