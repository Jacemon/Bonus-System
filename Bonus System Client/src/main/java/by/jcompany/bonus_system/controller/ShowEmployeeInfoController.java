package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.function.EmployeeFunctions;
import by.jcompany.bonus_system.function.RoleFunctions;
import by.jcompany.bonus_system.function.UserFunctions;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import by.jcompany.bonus_system.model.dto.RoleDto;
import by.jcompany.bonus_system.model.dto.UserDto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowEmployeeInfoController implements Initializable {
    public EmployeeDto employee = null;
    
    @FXML
    private Button closeButton;
    
    @FXML
    public void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        employee = AdminHomeController.getSelectedEmployee();
    }
}
