package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.function.EmployeeFunctions;
import by.jcompany.bonus_system.function.TaskFunctions;
import by.jcompany.bonus_system.function.UserFunctions;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import by.jcompany.bonus_system.model.dto.TaskDto;
import by.jcompany.bonus_system.model.dto.UserDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class ShowProfileInfoController implements Initializable {
    @FXML
    private Label bonusesForCompletedTasks;
    
    @FXML
    private Label bonusesForNotCompletedTasks;
    
    @FXML
    private Button buttonChangeLoginOrPassword;
    
    @FXML
    private Button closeButton;
    
    @FXML
    private Label firstName;
    
    @FXML
    private Label lastName;
    
    @FXML
    private TextField newLogin;
    
    @FXML
    private PasswordField newPassword;
    
    @FXML
    private PasswordField oldPassword;
    
    @FXML
    private PasswordField repeatNewPassword;
    
    @FXML
    private Label salary;
    
    @FXML
    private Label login;
    
    @FXML
    private Label labelStatus;
    
    @FXML
    private AnchorPane employeeInfo;
    
    @FXML
    void changeLoginOrPasswordAction() {
        String login = newLogin.getText();
        String currentPassword = oldPassword.getText();
        String password = newPassword.getText();
        String repeatPassword = repeatNewPassword.getText();
        
        if (!repeatPassword.equals("") && !currentPassword.equals("") &&
            !password.equals(repeatPassword)) {
            labelStatus.setText("Пароли не совпадают");
            return;
        }
        
        UserDto user = UserFunctions.updateUserByEmployee(CommonHomeController.getUser().getId(),
            login, currentPassword, password);
        
        String status;
        if (user == null) {
            status = "Profile not updated";
        } else {
            status = "Profile updated";
            if (!password.equals("")) {
                CommonHomeController.setPassword(password);
            }
            if (!login.equals("")) {
                CommonHomeController.setUser(user);
            }
        }
        labelStatus.setText(status);
        System.out.println(status);
    }
    
    @FXML
    void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        login.setText(CommonHomeController.getUser().getLogin());
        
        EmployeeDto employee = CommonHomeController.getUser().getEmployee();
        
        if (employee == null) {
            employeeInfo.setVisible(false);
        } else {
            firstName.setText(employee.getFirstName());
            lastName.setText(employee.getLastName());
            salary.setText(employee.getSalary().toString() + "$");
            
            bonusesForCompletedTasks.setText("");
            bonusesForNotCompletedTasks.setText("");
            
            Float bonusesForCompleted = EmployeeFunctions.calculateBonuses(null, false);
            
            if (bonusesForCompleted != null) {
                bonusesForCompletedTasks.setText(bonusesForCompleted.toString());
            }
            
            Float pointCost = TaskFunctions.getPointCost();
            Float bonusesForNotCompleted = 0.0f;
            
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            Calendar taskDate = Calendar.getInstance();
            
            for (TaskDto task : employee.getTasks()) {
                taskDate.setTime(Date.from(task.getCreationTime()));
                int taskYear = taskDate.get(Calendar.YEAR);
                if (!task.isPaid() && !task.isCompleted() && taskYear == currentYear) {
                    bonusesForNotCompleted += task.getBonus().getAmount(employee, pointCost);
                }
            }
            bonusesForNotCompletedTasks.setText(bonusesForNotCompleted.toString());
        }
    }
}
