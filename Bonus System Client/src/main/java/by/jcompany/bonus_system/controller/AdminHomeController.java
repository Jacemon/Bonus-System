package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.controller.stage.StageManager;
import by.jcompany.bonus_system.function.GeneralFunctions;
import by.jcompany.bonus_system.function.TaskFunctions;
import by.jcompany.bonus_system.function.UserFunctions;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import by.jcompany.bonus_system.model.dto.RoleDto;
import by.jcompany.bonus_system.model.dto.TaskDto;
import by.jcompany.bonus_system.model.dto.UserDto;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminHomeController implements Initializable {
    @Getter
    private static UserDto selectedUser = null;
    
    @FXML
    private Button buttonLogout;
    
    @FXML
    private Button closeButton;
    
    @FXML
    private Button buttonAddEmployee;
    
    @FXML
    private Button buttonAddRole;
    
    @FXML
    private Button buttonAddTask;
    
    @FXML
    private Button buttonAddUser;
    
    @FXML
    private Button buttonChangeEmployee;
    
    @FXML
    private Button buttonChangeRole;
    
    @FXML
    private Button buttonChangeTask;
    
    @FXML
    private Button buttonChangeUser;
    
    @FXML
    private Button buttonDeleteEmployee;
    
    @FXML
    private Button buttonDeleteRole;
    
    @FXML
    private Button buttonDeleteTask;
    
    @FXML
    private Button buttonDeleteUser;
    
    @FXML
    private Button buttonEmployeeTask;
    
    @FXML
    private Button buttonReloadUsers;
    
    @FXML
    private Button buttonReloadEmployees;
    
    @FXML
    private Button buttonReloadRoles;
    
    @FXML
    private Button buttonReloadTasks;
    
    @FXML
    private TableColumn<UserDto, String> userLogin;
    
    @FXML
    private TableColumn<UserDto, String> userRole;
    
    @FXML
    private TableColumn<UserDto, String> userEmployee;
    
    @FXML
    private TableView<UserDto> userTable;
    
    @FXML
    private TableColumn<EmployeeDto, String> employeeFirstName;
    
    @FXML
    private TableColumn<EmployeeDto, String> employeeLastName;
    
    @FXML
    private TableView<EmployeeDto> employeeTable;
    
    @FXML
    private TableColumn<RoleDto, Integer> roleAccessLevel;
    
    @FXML
    private TableColumn<RoleDto, String> roleName;
    
    @FXML
    private TableView<RoleDto> roleTable;
    
    @FXML
    private TableColumn<TaskDto, String> taskBonus;
    
    @FXML
    private TableColumn<TaskDto, ImageView> taskCompleted;
    
    @FXML
    private TableColumn<TaskDto, String> taskCreationTime;
    
    @FXML
    private TableColumn<TaskDto, String> taskDescription;
    
    @FXML
    private TableColumn<TaskDto, String> taskEmployee;
    
    @FXML
    private TableColumn<TaskDto, ImageView> taskPaid;
    
    @FXML
    private TableView<TaskDto> taskTable;
    
    @FXML
    void logoutAction() throws IOException {
        Stage stage = StageManager.reloadAndGetStage("login");
        stage.show();
        ((Stage) closeButton.getScene().getWindow()).close();
    }
    
    @FXML
    void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
        GeneralFunctions.quit();
    }
    
    @FXML
    void addEmployeeAction() {
    
    }
    
    @FXML
    void addRoleAction() {
    
    }
    
    @FXML
    void addTaskAction() {
    
    }
    
    @FXML
    void addUserAction() throws IOException {
        Stage stage = StageManager.reloadAndGetStage("addUser");
        stage.showAndWait();
        reloadUsersAction();
    }
    
    @FXML
    void changeEmployeeAction() {
    
    }
    
    @FXML
    void changeRoleAction() {
    
    }
    
    @FXML
    void changeTaskAction() {
    
    }
    
    @FXML
    void changeUserAction() throws IOException {
        selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            return;
        }
        
        Stage stage = StageManager.reloadAndGetStage("changeUser");
        stage.showAndWait();
        
        reloadUsersAction();
        selectedUser = null;
    }
    
    
    @FXML
    void deleteEmployeeAction() {
    
    }
    
    @FXML
    void deleteRoleAction() {
    
    }
    
    @FXML
    void deleteTaskAction() {
    
    }
    
    @FXML
    void deleteUserAction() {
        UserDto user = userTable.getSelectionModel().getSelectedItem();
        if (user == null) {
            return;
        }
        System.out.println(UserFunctions.deleteUser(user.getId()));
        reloadUsersAction();
    }
    
    
    @FXML
    void showEmployeeTaskAction() {
    
    }
    
    
    @FXML
    void reloadEmployeesAction() {
    
    }
    
    @FXML
    void reloadRolesAction() {
    
    }
    
    @FXML
    void reloadTasksAction() {
        List<TaskDto> tasks = TaskFunctions.readAllTasks();
        System.out.println(tasks);
        if (tasks != null) {
            taskTable.setItems(FXCollections.observableArrayList(tasks));
        } else {
            taskTable.setItems(FXCollections.observableArrayList());
        }
    }
    
    @FXML
    void reloadUsersAction() {
        List<UserDto> users = UserFunctions.readAllUsers();
        System.out.println(users);
        if (users != null) {
            userTable.setItems(FXCollections.observableArrayList(users));
        } else {
            userTable.setItems(FXCollections.observableArrayList());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // user table
        userLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        userRole.setCellValueFactory(roleDto -> new SimpleObjectProperty<>(roleDto.getValue().getRole().getName()));
        userEmployee.setCellValueFactory(employeeDto -> new SimpleObjectProperty<>(
                employeeDto.getValue().getEmployee() != null ?
                    employeeDto.getValue().getEmployee().getFirstName() + " " +
                        employeeDto.getValue().getEmployee().getLastName()
                    : "Не назначен"
            )
        );
        reloadUsersAction();
        // employee table
        // task table
        String path = System.getProperty("user.dir") +
            File.separator + "src" +
            File.separator + "main" +
            File.separator + "resources" +
            File.separator + "by" +
            File.separator + "jcompany" +
            File.separator + "bonus_system" +
            File.separator + "ico" +
            File.separator;
        
        /*Image completedImage = new Image(Objects.requireNonNull(
            getClass().getResource("../ico/t_completed.png"))
            .toExternalForm());
        Image notCompletedImage = new Image(Objects.requireNonNull(
                getClass().getResource("../ico/not-complete.png"))
            .toExternalForm());*/
        
        taskCompleted.setCellValueFactory(taskDto -> {
                try {
                    return new SimpleObjectProperty<>(
                            taskDto.getValue().isCompleted() ? new ImageView(new Image(
                                new FileInputStream(path + "complete.png"),
                                20, 20, false, false))
                                : new ImageView(new Image(
                                new FileInputStream(path + "not-complete.png"),
                                20, 20, false, false))
                        );
                } catch (FileNotFoundException exception) {
                    exception.printStackTrace();
                }
                return null;
            }
        );
        reloadTasksAction();
        // role table
    }
}
