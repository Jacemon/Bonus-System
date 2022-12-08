package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.function.EmployeeFunctions;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeEmployeeController implements Initializable {
    private EmployeeDto editEmployee;
    
    @FXML
    private Button closeButton;
    
    @FXML
    private TextField employeeNewFirstName;
    
    @FXML
    private TextField employeeNewLastName;
    
    @FXML
    private Button buttonChange;
    
    @FXML
    private Label labelStatus;
    
    @FXML
    public void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
    
    @FXML
    void changeEmployeeAction() {
        String status = EmployeeFunctions.updateEmployee(editEmployee.getId(), employeeNewFirstName.getText(),
            employeeNewLastName.getText());
        labelStatus.setText(status);
        System.out.println(status);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editEmployee = AdminHomeController.getSelectedEmployee();
        
        employeeNewFirstName.setText(editEmployee.getFirstName());
        employeeNewLastName.setText(editEmployee.getLastName());
    }
}
