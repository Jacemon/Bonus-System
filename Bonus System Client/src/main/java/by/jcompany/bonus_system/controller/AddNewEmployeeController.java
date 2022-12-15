package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.function.EmployeeFunctions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddNewEmployeeController implements Initializable {
    @FXML
    private Button closeButton;
    
    @FXML
    private TextField employeeFirstName;
    
    @FXML
    private TextField employeeLastName;
    
    @FXML
    private TextField employeeSalary;
    
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
        String status = EmployeeFunctions.createEmployee(employeeFirstName.getText(),
            employeeLastName.getText(), Float.parseFloat(employeeSalary.getText()));
        labelStatus.setText(status);
        System.out.println(status);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
}
