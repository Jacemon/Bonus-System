package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.controller.stage.StageManager;
import by.jcompany.bonus_system.function.*;
import by.jcompany.bonus_system.model.dto.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminHomeController implements Initializable {
    @Getter
    private static UserDto selectedUser = null;
    @Getter
    private static EmployeeDto selectedEmployee = null;
    @Getter
    private static TaskDto selectedTask = null;
    @Getter
    private static RoleDto selectedRole = null;
    
    @Getter
    private static final String path = System.getProperty("user.dir") +
        File.separator + "src" +
        File.separator + "main" +
        File.separator + "resources" +
        File.separator + "by" +
        File.separator + "jcompany" +
        File.separator + "bonus_system" +
        File.separator + "ico" +
        File.separator;
    
    @FXML
    private Button buttonPayBonuses;
    
    @FXML
    private Button buttonShowInfo;
    
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
    private Button buttonEmployeeInfo;
    
    @FXML
    private Button buttonChangeTaskPointCost;
    
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
    private TableColumn<EmployeeDto, String> employeeId;
    
    @FXML
    private TableColumn<EmployeeDto, String> employeeFirstName;
    
    @FXML
    private TableColumn<EmployeeDto, String> employeeLastName;
    
    @FXML
    private TableColumn<EmployeeDto, String> employeeSalary;
    
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
    void payBonusesAction() throws IOException, URISyntaxException {
        Stage stage = StageManager.reloadAndGetStage("payBonuses");
        stage.showAndWait();
        reloadTasksAction();
        reloadEmployeesAction();
    }
    
    @FXML
    void showInfoAction() throws IOException, URISyntaxException {
        Stage stage = StageManager.reloadAndGetStage("showInfo");
        stage.showAndWait();
    }
    
    @FXML
    void logoutAction() throws IOException, URISyntaxException {
        Stage stage = StageManager.reloadAndGetStage("login");
        stage.show();
        ((Stage) closeButton.getScene().getWindow()).close();
        GeneralFunctions.logout();
    }
    
    @FXML
    void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
        GeneralFunctions.quit();
    }
    
    @FXML
    void addEmployeeAction() throws IOException, URISyntaxException {
        Stage stage = StageManager.reloadAndGetStage("addEmployee");
        stage.showAndWait();
        reloadEmployeesAction();
    }
    
    @FXML
    void addRoleAction() throws IOException, URISyntaxException {
        Stage stage = StageManager.reloadAndGetStage("addRole");
        stage.showAndWait();
        reloadRolesAction();
    }
    
    @FXML
    void addTaskAction() throws IOException, URISyntaxException {
        Stage stage = StageManager.reloadAndGetStage("addTask");
        stage.showAndWait();
        reloadTasksAction();
    }
    
    @FXML
    void addUserAction() throws IOException, URISyntaxException {
        Stage stage = StageManager.reloadAndGetStage("addUser");
        stage.showAndWait();
        reloadUsersAction();
    }
    
    @FXML
    void changeEmployeeAction() throws IOException, URISyntaxException {
        selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            return;
        }
    
        Stage stage = StageManager.reloadAndGetStage("changeEmployee");
        stage.showAndWait();
    
        reloadEmployeesAction();
        reloadUsersAction();
        selectedEmployee = null;
    }
    
    @FXML
    void changeRoleAction() throws IOException, URISyntaxException {
        selectedRole = roleTable.getSelectionModel().getSelectedItem();
        if (selectedRole == null) {
            return;
        }
    
        Stage stage = StageManager.reloadAndGetStage("changeRole");
        stage.showAndWait();
    
        reloadRolesAction();
        selectedRole = null;
    }
    
    @FXML
    void changeTaskAction() throws IOException, URISyntaxException {
        selectedTask = taskTable.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            return;
        }
    
        Stage stage = StageManager.reloadAndGetStage("changeTask");
        stage.showAndWait();
    
        reloadTasksAction();
        selectedTask = null;
        reloadEmployeesAction();
    }
    
    @FXML
    void changeUserAction() throws IOException, URISyntaxException {
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
        EmployeeDto employee = employeeTable.getSelectionModel().getSelectedItem();
        if (employee == null) {
            return;
        }
        System.out.println(EmployeeFunctions.deleteEmployee(employee.getId()));
        reloadEmployeesAction();
        reloadUsersAction();
    }
    
    @FXML
    void deleteRoleAction() {
        RoleDto role = roleTable.getSelectionModel().getSelectedItem();
        if (role == null) {
            return;
        }
        System.out.println(RoleFunctions.deleteRole(role.getName()));
        reloadRolesAction();
    }
    
    @FXML
    void deleteTaskAction() {
        TaskDto task = taskTable.getSelectionModel().getSelectedItem();
        if (task == null) {
            return;
        }
        System.out.println(TaskFunctions.deleteTask(task.getId()));
        reloadTasksAction();
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
    void showEmployeeInfoAction() throws IOException, URISyntaxException {
        selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        reloadEmployeesAction();
        if (selectedEmployee == null) {
            return;
        }
    
        Stage stage = StageManager.reloadAndGetStage("showEmployeeInfo");
        stage.showAndWait();
        
        selectedEmployee = null;
    }
    
    @FXML
    void changeTaskPointCostAction() throws IOException, URISyntaxException {
        Stage stage = StageManager.reloadAndGetStage("changePointCost");
        stage.showAndWait();
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
    
    @FXML
    void reloadEmployeesAction() {
        List<EmployeeDto> employees = EmployeeFunctions.readAllEmployees();
        System.out.println(employees);
        if (employees != null) {
            employeeTable.setItems(FXCollections.observableArrayList(employees));
        } else {
            employeeTable.setItems(FXCollections.observableArrayList());
        }
    }
    
    @FXML
    void reloadRolesAction() {
        List<RoleDto> roles = RoleFunctions.readAllRoles();
        System.out.println(roles);
        if (roles != null) {
            roleTable.setItems(FXCollections.observableArrayList(roles));
        } else {
            roleTable.setItems(FXCollections.observableArrayList());
        }
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
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initUserTable();
        reloadUsersAction();
        initEmployeeTable();
        reloadEmployeesAction();
        initRoleTable();
        reloadRolesAction();
        initTaskTable();
        reloadTasksAction();
    }
    
    private void initUserTable() {
        userLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        userRole.setCellValueFactory(roleDto -> new SimpleObjectProperty<>(roleDto.getValue().getRole().getName()));
        userEmployee.setCellValueFactory(employeeDto -> new SimpleObjectProperty<>(
            employeeDto.getValue().getEmployee() != null ?
                employeeDto.getValue().getEmployee().getFirstName() + " " +
                    employeeDto.getValue().getEmployee().getLastName()
                : "Не назначен"
        ));
    }
    
    private void initEmployeeTable() {
        employeeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        employeeFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        employeeLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        employeeSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }
    
    private void initRoleTable() {
        roleName.setCellValueFactory(new PropertyValueFactory<>("name"));
        roleAccessLevel.setCellValueFactory(new PropertyValueFactory<>("accessLevel"));
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
        
        String PATTERN_FORMAT = "dd.MM.yyyy - hh:mm";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
            .withZone(ZoneId.systemDefault());
        taskCreationTime.setCellValueFactory(taskDto -> new SimpleObjectProperty<>(
            formatter.format(taskDto.getValue().getCreationTime())
        ));
        taskEmployee.setCellValueFactory(taskDto -> new SimpleObjectProperty<>(
            taskDto.getValue().getEmployee() != null ?
                taskDto.getValue().getEmployee().getFirstName() + " " +
                    taskDto.getValue().getEmployee().getLastName()
                : "Не назначен"
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
        taskPaid.setCellValueFactory(taskDto -> {
                try {
                    return new SimpleObjectProperty<>(
                        taskDto.getValue().isPaid() ? new ImageView(new Image(
                            new FileInputStream(path + "yes.png"),
                            20, 20, false, false))
                            : new ImageView(new Image(
                            new FileInputStream(path + "no.png"),
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
    }
    
    static String getBonusSign(BonusDto bonus) {
        String sign = "";
        switch (bonus.getType()) {
            case MONEY -> sign = "$";
            case POINTS -> sign = "p";
            case PERCENT -> sign = "%";
        }
        return sign;
    }
}
