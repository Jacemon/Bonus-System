package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.function.EmployeeFunctions;
import by.jcompany.bonus_system.function.RoleFunctions;
import by.jcompany.bonus_system.function.UserFunctions;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import by.jcompany.bonus_system.model.dto.RoleDto;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddNewUserController implements Initializable {
    @FXML
    private Button closeButton;
    
    @FXML
    private TextField userLogin;
    
    @FXML
    private TextField userPassword;
    
    @FXML
    private ComboBox<String> comboBoxRole;
    
    @FXML
    private ComboBox<EmployeeDto> comboBoxEmployee;
    
    @FXML
    private Button buttonAdd;
    
    @FXML
    private Label labelStatus;
    
    @FXML
    public void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
    
    @FXML
    void addUserAction() {
        String status = UserFunctions.createUser(userLogin.getText(), userPassword.getText(),
            comboBoxRole.getValue(), comboBoxEmployee.getValue());
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
            comboBoxRole.setItems(FXCollections.observableArrayList(roles));
        } else {
            comboBoxRole.setItems(FXCollections.observableArrayList());
        }
        
        List<EmployeeDto> employeesDto = EmployeeFunctions.readAllEmployees();
        if (employeesDto != null) {
            for (EmployeeDto employeeDto : employeesDto) {
                employeeDto.setTasks(null);
            }
            employeesDto.add(0, null);
            comboBoxEmployee.setItems(FXCollections.observableArrayList(employeesDto));
        } else {
            comboBoxEmployee.setItems(FXCollections.observableArrayList());
        }
        comboBoxEmployee.setConverter(new StringConverter<>() {
            @Override
            public String toString(EmployeeDto employeeDto) {
                return "[" + employeeDto.getId() + "] " + employeeDto.getFirstName() + " " + employeeDto.getLastName();
            }
        
            @Override
            public EmployeeDto fromString(String string) {
                return null;
            }
        });
    }
}
