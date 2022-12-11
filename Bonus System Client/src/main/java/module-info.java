module by.jcompany.bonus_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires lombok;

    exports by.jcompany.bonus_system.controller;
    opens by.jcompany.bonus_system.controller to javafx.fxml;
    exports by.jcompany.bonus_system.model.dto;
    opens by.jcompany.bonus_system.model.dto to com.google.gson;
    exports by.jcompany.bonus_system.model;
    opens by.jcompany.bonus_system.model to javafx.fxml;
    exports by.jcompany.bonus_system.util;
    opens by.jcompany.bonus_system.util to javafx.fxml;
    exports by.jcompany.bonus_system.function;
    opens by.jcompany.bonus_system.function to javafx.fxml;
    exports by.jcompany.bonus_system;
    opens by.jcompany.bonus_system to javafx.fxml;
    exports by.jcompany.bonus_system.controller.stage;
    opens by.jcompany.bonus_system.controller.stage to javafx.fxml;
}