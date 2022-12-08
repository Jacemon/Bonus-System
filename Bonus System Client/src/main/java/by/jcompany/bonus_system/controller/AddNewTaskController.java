package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.function.EmployeeFunctions;
import by.jcompany.bonus_system.function.RoleFunctions;
import by.jcompany.bonus_system.function.TaskFunctions;
import by.jcompany.bonus_system.function.UserFunctions;
import by.jcompany.bonus_system.model.dto.BonusDto;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import by.jcompany.bonus_system.model.dto.RoleDto;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddNewTaskController implements Initializable {
    @FXML
    private Button closeButton;
    
    @FXML
    private ComboBox<BonusDto.BonusType> comboBoxBonusType;
    
    @FXML
    private TextArea taskDescription;
    
    @FXML
    private TextField bonusAmount;
    
    @FXML
    private Button buttonAdd;
    
    @FXML
    private Label labelStatus;
    
    @FXML
    public void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
    
    @FXML
    void addTaskAction() {
        String status = TaskFunctions.createTask(taskDescription.getText(),
            Float.parseFloat(bonusAmount.getText()), comboBoxBonusType.getValue());
        labelStatus.setText(status);
        System.out.println(status);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxBonusType.setItems(FXCollections.observableArrayList(BonusDto.BonusType.values()));
    }
}
