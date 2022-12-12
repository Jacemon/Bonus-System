package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.function.EmployeeFunctions;
import by.jcompany.bonus_system.function.TaskFunctions;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import by.jcompany.bonus_system.model.dto.TaskDto;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.time.Year;
import java.util.*;

import static by.jcompany.bonus_system.controller.AdminHomeController.getBonusSign;

public class ShowEmployeeInfoController implements Initializable {
    public EmployeeDto employee = null;
    
    @FXML
    private Button closeButton;
    
    @FXML
    private Label firstName;
    
    @FXML
    private Label lastName;
    
    @FXML
    private Label salary;
    
    @FXML
    private Label bonusesForCompletedTasks;
    
    @FXML
    private Label bonusesForNotCompletedTasks;
    
    @FXML
    private TableColumn<TaskDto, String> taskBonus;
    
    @FXML
    private TableColumn<TaskDto, ImageView> taskCompleted;
    
    @FXML
    private TableColumn<TaskDto, String> taskDescription;
    
    @FXML
    private TableView<TaskDto> taskTable;
    
    @FXML
    public void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        employee = AdminHomeController.getSelectedEmployee();
        
        lastName.setText(employee.getLastName());
        firstName.setText(employee.getFirstName());
        salary.setText(employee.getSalary().toString() + "$");
        bonusesForCompletedTasks.setText("error");
        bonusesForNotCompletedTasks.setText("error");
        
        Float bonusesForCompleted = EmployeeFunctions.calculateBonuses(employee.getId(), true);
        
        if (bonusesForCompleted != null) {
            bonusesForCompletedTasks.setText(bonusesForCompleted.toString());
        }

        Float pointCost = TaskFunctions.getPointCost();
        Float bonusesForNotCompleted = 0.0f;
        
        for (TaskDto task : employee.getTasks()) {
            if (!task.isPaid() && !task.isCompleted()) {
                bonusesForNotCompleted += task.getBonus().getAmount(employee, pointCost);
            }
        }
        bonusesForNotCompletedTasks.setText(bonusesForNotCompleted.toString());
        
        initTaskTable();
    }
    
    private void initTaskTable() {
        taskDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        taskDescription.setCellFactory(tableColumn -> {
            TableCell<TaskDto, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(taskDescription.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });
        taskCompleted.setCellValueFactory(taskDto -> {
                try {
                    return new SimpleObjectProperty<>(
                        taskDto.getValue().isCompleted() ? new ImageView(new Image(
                            new FileInputStream(AdminHomeController.getPath() + "yes.png"),
                            20, 20, false, false))
                            : new ImageView(new Image(
                            new FileInputStream(AdminHomeController.getPath() + "no.png"),
                            20, 20, false, false)
                        ));
                } catch (FileNotFoundException exception) {
                    exception.printStackTrace();
                }
                return null;
            }
        );
        taskBonus.setCellValueFactory(taskDto -> new SimpleObjectProperty<>(
            taskDto.getValue().getBonus().getAmount().toString() +
                getBonusSign(taskDto.getValue().getBonus())
        ));
        
        Set<TaskDto> tasksDto = employee.getTasks();
        Set<TaskDto> tasks = new LinkedHashSet<>();
        
        if (tasksDto != null) {
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            Calendar taskDate = Calendar.getInstance();
            
            for (TaskDto task : tasksDto) {
                
                taskDate.setTime(Date.from(task.getCreationTime()));
                int taskYear = taskDate.get(Calendar.YEAR);
                if (taskYear == currentYear && !task.isPaid()) {
                    tasks.add(task);
                }
            }
            taskTable.setItems(FXCollections.observableArrayList(tasks));
        } else {
            taskTable.setItems(FXCollections.observableArrayList());
        }
    }
}
