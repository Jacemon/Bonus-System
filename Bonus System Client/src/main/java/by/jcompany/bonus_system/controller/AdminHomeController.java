package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.controller.stage.StageManager;
import by.jcompany.bonus_system.function.GeneralFunctions;
import by.jcompany.bonus_system.function.UserFunctions;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import by.jcompany.bonus_system.model.dto.RoleDto;
import by.jcompany.bonus_system.model.dto.UserDto;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminHomeController implements Initializable {
    @Getter
    private static UserDto selectedUser = null;
    
    @FXML
    private Button buttonAddUser;
    
    @FXML
    private Button buttonChangeUser;
    
    @FXML
    private Button buttonDeleteUser;
    
    @FXML
    private Button buttonReloadUsers;
    
    @FXML
    private Button buttonLogout;
    
    @FXML
    private Button closeButton;
    
    @FXML
    private TableColumn<UserDto, String> userLogin;
    
    @FXML
    private TableColumn<UserDto, RoleDto> userRole;
    
    @FXML
    private TableColumn<UserDto, EmployeeDto> userEmployee;
    
    @FXML
    private TableView<UserDto> userTable;
    
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
    void addUserAction() throws IOException {
        Stage stage = StageManager.reloadAndGetStage("addUser");
        stage.showAndWait();
        reloadUsersAction();
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
    void deleteUserAction() {
        UserDto user = userTable.getSelectionModel().getSelectedItem();
        if (user == null) {
            return;
        }
        System.out.println(UserFunctions.deleteUser(user.getId()));
        reloadUsersAction();
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
        userLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        userRole.setCellValueFactory(RoleDto -> {
            SimpleObjectProperty property = new SimpleObjectProperty();
            property.setValue(RoleDto.getValue().getRole().getName());
            return property;
        });
        userEmployee.setCellValueFactory(EmployeeDto -> {
            SimpleObjectProperty property = new SimpleObjectProperty();
            if (EmployeeDto.getValue().getEmployee() != null) {
                property.setValue(EmployeeDto.getValue().getEmployee().getFirstName() + " " +
                    EmployeeDto.getValue().getEmployee().getLastName());
            } else {
                property.setValue("Не назначен");
            }
            return property;
        });
        reloadUsersAction();
    }
}
