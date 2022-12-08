package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.function.EmployeeFunctions;
import by.jcompany.bonus_system.function.RoleFunctions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddNewRoleController implements Initializable {
    @FXML
    private Button closeButton;
    
    @FXML
    private TextField roleName;
    
    @FXML
    private TextField roleAccessLevel;
    
    @FXML
    private Button buttonAdd;
    
    @FXML
    private Label labelStatus;
    
    @FXML
    public void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
    
    @FXML
    void addRoleAction() {
        String status = RoleFunctions.createRole(roleName.getText(), Integer.parseInt(roleAccessLevel.getText()));
        labelStatus.setText(status);
        System.out.println(status);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) { }
}
