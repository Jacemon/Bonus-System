package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.function.EmployeeFunctions;
import by.jcompany.bonus_system.function.RoleFunctions;
import by.jcompany.bonus_system.function.UserFunctions;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import by.jcompany.bonus_system.model.dto.RoleDto;
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

public class AddNewEmployeeController implements Initializable {
    @FXML
    private Button closeButton;
    
    @FXML
    private TextField employeeFirstName;
    
    @FXML
    private TextField employeeLastName;
    
    @FXML
    private Button buttonAdd;
    
    @FXML
    private Label labelStatus;
    
    @FXML
    public void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
    
    @FXML
    void addEmployeeAction() {
        String status = EmployeeFunctions.createEmployee(employeeFirstName.getText(), employeeLastName.getText());
        labelStatus.setText(status);
        System.out.println(status);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
}
