package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.function.RoleFunctions;
import by.jcompany.bonus_system.model.dto.RoleDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangeRoleController implements Initializable {
    private RoleDto editRole;
    
    @FXML
    private Button closeButton;
    
    @FXML
    private TextField roleNewName;
    
    @FXML
    private TextField roleNewAccessLevel;
    
    @FXML
    private Button buttonChange;
    
    @FXML
    private Label labelStatus;
    
    @FXML
    public void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
    
    @FXML
    void changeRoleAction() {
        String status = RoleFunctions.updateRole(editRole.getName(), Integer.parseInt(roleNewAccessLevel.getText()));
        labelStatus.setText(status);
        System.out.println(status);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editRole = AdminHomeController.getSelectedRole();
        
        roleNewName.setText(editRole.getName());
        roleNewAccessLevel.setText(editRole.getAccessLevel().toString());
    }
}
