package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.function.EmployeeFunctions;
import by.jcompany.bonus_system.function.TaskFunctions;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PayBonusesController implements Initializable {
    @FXML
    private Label bonuses;
    
    @FXML
    private Button buttonPayBonuses;
    
    @FXML
    private Button closeButton;
    
    @FXML
    private ComboBox<EmployeeDto> comboBoxEmployee;
    
    @FXML
    private Label labelStatus;
    
    @FXML
    public void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
    
    @FXML
    void payBonusesAction() {
        EmployeeDto employee = comboBoxEmployee.getValue();
        if (employee == null) {
            System.out.println(EmployeeFunctions.payBonuses(null));
        } else {
            System.out.println(EmployeeFunctions.payBonuses(employee.getId()));
        }
        reloadBonusesAction();
    }
    
    @FXML
    void reloadBonusesAction() {
        Float amount;
        EmployeeDto employee = comboBoxEmployee.getValue();
        if (employee == null) {
            amount = EmployeeFunctions.calculateBonuses(null);
        } else {
            amount = EmployeeFunctions.calculateBonuses(employee.getId());
        }
        if (amount != null) {
            bonuses.setText(amount + "$");
            labelStatus.setText("Bonuses were paid");
        } else {
            labelStatus.setText("error");
            bonuses.setText("");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxEmployee.setConverter(new StringConverter<>() {
            @Override
            public String toString(EmployeeDto employeeDto) {
                return "[" + employeeDto.getId() + "] " + employeeDto.getFirstName() + " " +
                    employeeDto.getLastName();
            }
        
            @Override
            public EmployeeDto fromString(String string) {
                return null;
            }
        });
        
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
        reloadBonusesAction();
    }
}
