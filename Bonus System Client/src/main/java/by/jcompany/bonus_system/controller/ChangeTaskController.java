package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.function.EmployeeFunctions;
import by.jcompany.bonus_system.function.TaskFunctions;
import by.jcompany.bonus_system.model.dto.BonusDto;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import by.jcompany.bonus_system.model.dto.TaskDto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChangeTaskController implements Initializable {
    public TaskDto editTask = null;
    
    @FXML
    private Button closeButton;
    
    @FXML
    private ComboBox<BonusDto.BonusType> comboBoxNewBonusType;
    
    @FXML
    private TextArea taskNewDescription;
    
    @FXML
    private TextField bonusNewAmount;
    
    @FXML
    private ComboBox<EmployeeDto> comboBoxNewEmployee;
    
    @FXML
    private CheckBox taskIsCompleted;
    
    @FXML
    private Button buttonChange;
    
    @FXML
    private Label labelStatus;
    
    @FXML
    public void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
    
    @FXML
    void changeTaskAction() {
        String status = TaskFunctions.updateTask(editTask.getId(), taskNewDescription.getText(),
            Float.parseFloat(bonusNewAmount.getText()), taskIsCompleted.isSelected(), editTask.isPaid(),
            comboBoxNewEmployee.getValue(), comboBoxNewBonusType.getValue());
        labelStatus.setText(status);
        System.out.println(status);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
    
        comboBoxNewEmployee.setConverter(new StringConverter<>() {
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
        
        editTask = AdminHomeController.getSelectedTask();
        taskNewDescription.setText(editTask.getDescription());
        bonusNewAmount.setText(editTask.getBonus().getAmount().toString());
        comboBoxNewBonusType.setValue(editTask.getBonus().getType());
        comboBoxNewEmployee.setValue(editTask.getEmployee());
        taskIsCompleted.setSelected(editTask.isCompleted());
        
        comboBoxNewBonusType.setItems(FXCollections.observableArrayList(BonusDto.BonusType.values()));
    }
}
