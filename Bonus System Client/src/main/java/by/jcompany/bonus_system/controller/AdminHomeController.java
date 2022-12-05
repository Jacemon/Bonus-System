package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.Boot;
import by.jcompany.bonus_system.function.GeneralFunctions;
import by.jcompany.bonus_system.function.UserFunctions;
import by.jcompany.bonus_system.model.dto.EmployeeDto;
import by.jcompany.bonus_system.model.dto.RoleDto;
import by.jcompany.bonus_system.model.dto.UserDto;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
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
    private Button closeButton;
    
    @FXML
    private TableColumn<UserDto, String> userLogin;
    
    @FXML
    private TableColumn<UserDto, RoleDto> userRole;
    
    @FXML
    private TableColumn<UserDto, EmployeeDto> userEmployee;
    
    @FXML
    private TableView<UserDto> userTable;
    private Point2D userAddPoint;
    
    @FXML
    void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
        GeneralFunctions.quit();
    }
    
    // todo перенести
    void loginButtonAction() {
        GeneralFunctions.login("admin", "newPass");
    }
    
    void logoutButtonAction() {
        GeneralFunctions.logout();
    }
    
    @FXML
    void addUserAction() throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(
            Boot.class.getResource("fxml/addNewUser.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        parent.setOnMousePressed(mouseEvent -> {
            userAddPoint = new Point2D(mouseEvent.getSceneX(), mouseEvent.getSceneY());
        });
        parent.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - userAddPoint.getX());
            stage.setY(mouseEvent.getScreenY() - userAddPoint.getY());
        });
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.showAndWait();
        reloadUsersAction();
    }
    
    @FXML
    void changeUserAction() throws IOException {
        selectedUser = userTable.getSelectionModel().getSelectedItem();
        
        Parent parent = FXMLLoader.load(Objects.requireNonNull(
            Boot.class.getResource("fxml/changeUser.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(parent);
        parent.setOnMousePressed(mouseEvent -> {
            userAddPoint = new Point2D(mouseEvent.getSceneX(), mouseEvent.getSceneY());
        });
        parent.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - userAddPoint.getX());
            stage.setY(mouseEvent.getScreenY() - userAddPoint.getY());
        });
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
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
        if (users != null) {
            userTable.setItems(FXCollections.observableArrayList(users));
        } else {
            userTable.setItems(FXCollections.observableArrayList());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginButtonAction();
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
