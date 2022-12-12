package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.controller.stage.StageManager;
import by.jcompany.bonus_system.function.GeneralFunctions;
import by.jcompany.bonus_system.function.TaskFunctions;
import by.jcompany.bonus_system.model.dto.BonusDto;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import by.jcompany.bonus_system.model.dto.TaskDto;
import by.jcompany.bonus_system.model.dto.UserDto;
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
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CommonHomeController implements Initializable {
    @Setter
    @Getter
    private static UserDto user;
    @Setter
    private static String password;
    
    @FXML
    private Button buttonTakeTask;
    
    @FXML
    private Button buttonCompleteTask;
    
    @FXML
    private Button buttonLogout;
    
    @FXML
    private Button buttonReload;
    
    @FXML
    private Button buttonShowInfo;
    
    @FXML
    private Button buttonShowProfile;
    
    @FXML
    private Button buttonCancelTask;
    
    @FXML
    private Button closeButton;
    
    @FXML
    private TableColumn<TaskDto, String> taskBonus;
    
    @FXML
    private TableColumn<TaskDto, ImageView> taskCompleted;
    
    @FXML
    private TableColumn<TaskDto, String> taskCreationTime;
    
    @FXML
    private TableColumn<TaskDto, String> taskDescription;
    
    @FXML
    private TableColumn<TaskDto, ImageView> taskTaken;
    
    @FXML
    private TableView<TaskDto> taskTable;
    
    static String getBonusSign(BonusDto bonus) {
        String sign = "";
        switch (bonus.getType()) {
            case MONEY -> sign = "$";
            case POINTS -> sign = "p";
            case PERCENT -> sign = "%";
        }
        return sign;
    }
    
    @FXML
    void takeTaskAction() {
        System.out.println(TaskFunctions.setTaskToEmployee(taskTable.getSelectionModel().getSelectedItem().getId()));
        reloadTasksAction();
    }
    
    @FXML
    void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
        GeneralFunctions.quit();
    }
    
    @FXML
    void completeTaskAction() {
        System.out.println(TaskFunctions.setTaskCompleted(taskTable.getSelectionModel().getSelectedItem().getId()));
        reloadTasksAction();
    }
    
    @FXML
    void logoutAction() throws IOException, URISyntaxException {
        Stage stage = StageManager.reloadAndGetStage("login");
        stage.show();
        ((Stage) closeButton.getScene().getWindow()).close();
        GeneralFunctions.logout();
        user = null;
        password = null;
    }
    
    @FXML
    void reloadTasksAction() {
        List<TaskDto> tasksDto = TaskFunctions.readAllTasks();
        System.out.println(tasksDto);
        
        if (tasksDto != null) {
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            Calendar taskDate = Calendar.getInstance();
            
            Set<TaskDto> tasks = new LinkedHashSet<>();
            for (TaskDto task : tasksDto) {
                taskDate.setTime(Date.from(task.getCreationTime()));
                int taskYear = taskDate.get(Calendar.YEAR);
                
                if (taskYear == currentYear && !task.isPaid()) {
                    EmployeeDto taskEmployee = task.getEmployee();
                    if (taskEmployee == null) {
                        if (!task.isCompleted()) {
                            tasks.add(task);
                        }
                    } else {
                        if (user != null) {
                            EmployeeDto userEmployee = user.getEmployee();
                            if (userEmployee != null &&
                                taskEmployee.getId().equals(userEmployee.getId())) {
                                tasks.add(task);
                            }
                        }
                    }
                }
            }
            taskTable.setItems(FXCollections.observableArrayList(tasks));
        } else {
            taskTable.setItems(FXCollections.observableArrayList());
        }
    }
    
    @FXML
    void showProfile() throws IOException, URISyntaxException {
        Stage stage = StageManager.reloadAndGetStage("showProfileInfo");
        stage.showAndWait();
    }
    
    @FXML
    void cancelAction() {
        System.out.println(TaskFunctions.unsetTaskFromEmployee(taskTable.getSelectionModel().getSelectedItem().getId()));
        reloadTasksAction();
    }
    
    @FXML
    void reloadAction() {
        user = GeneralFunctions.login(user.getLogin(), password);
        reloadTasksAction();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTaskTable();
        reloadTasksAction();
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
            return cell;
        });
        
        String PATTERN_FORMAT = "dd.MM.yyyy - hh:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
            .withZone(ZoneId.systemDefault());
        taskCreationTime.setCellValueFactory(taskDto -> new SimpleObjectProperty<>(
            formatter.format(taskDto.getValue().getCreationTime())
        ));
        taskCompleted.setCellValueFactory(taskDto -> {
                try {
                    return new SimpleObjectProperty<>(
                        taskDto.getValue().isCompleted() ? new ImageView(new Image(
                            Objects.requireNonNull(getClass()
                                .getResource("/by/jcompany/bonus_system/ico/yes.png")).toURI().toString(),
                            20, 20, false, false))
                            : new ImageView(new Image(
                            Objects.requireNonNull(getClass()
                                .getResource("/by/jcompany/bonus_system/ico/no.png")).toURI().toString(),
                            20, 20, false, false))
                    );
                } catch (URISyntaxException exception) {
                    throw new RuntimeException(exception);
                }
            }
        );
        taskTaken.setCellValueFactory(taskDto -> {
                try {
                    return new SimpleObjectProperty<>(
                        taskDto.getValue().getEmployee() != null ? new ImageView(new Image(
                            Objects.requireNonNull(getClass()
                                .getResource("/by/jcompany/bonus_system/ico/yes.png")).toURI().toString(),
                            20, 20, false, false))
                            : new ImageView(new Image(
                            Objects.requireNonNull(getClass()
                                .getResource("/by/jcompany/bonus_system/ico/no.png")).toURI().toString(),
                            20, 20, false, false))
                    );
                } catch (URISyntaxException exception) {
                    throw new RuntimeException(exception);
                }
            }
        );
        taskBonus.setCellValueFactory(taskDto -> new SimpleObjectProperty<>(
            taskDto.getValue().getBonus().getAmount().toString() +
                getBonusSign(taskDto.getValue().getBonus())
        ));
    }
}
