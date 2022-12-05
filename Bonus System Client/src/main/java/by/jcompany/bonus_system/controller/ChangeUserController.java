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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChangeUserController implements Initializable {
    public UserDto editUser = null;
    @FXML
    private Button closeButton;
    @FXML
    private Button buttonAdd;
    
    @FXML
    private ComboBox<EmployeeDto> comboBoxNewEmployee;
    
    @FXML
    private ComboBox<String> comboBoxNewRole;
    
    @FXML
    private TextField userNewLogin;
    
    @FXML
    private TextField userNewPassword;
    
    @FXML
    private Label labelStatus;
    
    @FXML
    public void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
    
    @FXML
    void changeUserAction() {
        String status = UserFunctions.updateUser(editUser.getId(), userNewLogin.getText(), userNewPassword.getText(),
            comboBoxNewRole.getValue(), comboBoxNewEmployee.getValue());
        labelStatus.setText(status);
        System.out.println(status);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<RoleDto> rolesDto = RoleFunctions.readAllRoles();
        if (rolesDto != null) {
            List<String> roles = new ArrayList<>();
            for (RoleDto roleDto : rolesDto) {
                roles.add(roleDto.getName());
            }
            comboBoxNewRole.setItems(FXCollections.observableArrayList(roles));
        } else {
            comboBoxNewRole.setItems(FXCollections.observableArrayList());
        }
        
        List<EmployeeDto> employeesDto = EmployeeFunctions.readAllEmployees();
        
        if (employeesDto != null) {
            for (EmployeeDto employeeDto : employeesDto) {
                employeeDto.setTasks(null);
            }
            employeesDto.add(0, null);
            comboBoxNewEmployee.setItems(FXCollections.observableArrayList(employeesDto));
        } else {
            comboBoxNewEmployee.setItems(FXCollections.observableArrayList());
        }
        
        editUser = AdminHomeController.getSelectedUser();
        
        userNewLogin.setText(editUser.getLogin());
        comboBoxNewRole.setValue(editUser.getRole().getName());
        comboBoxNewEmployee.setValue(editUser.getEmployee());
    }
}
