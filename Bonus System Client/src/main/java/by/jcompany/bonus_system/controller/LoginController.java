package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.ClientBoot;
import by.jcompany.bonus_system.controller.stage.StageManager;
import by.jcompany.bonus_system.function.GeneralFunctions;
import by.jcompany.bonus_system.model.dto.UserDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class LoginController {
    @FXML
    private Button closeButton;
    
    @FXML
    void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
        GeneralFunctions.quit();
    }
    
    @FXML
    private Button buttonLogin;
    
    @FXML
    private Label labelStatus;
    
    @FXML
    private TextField userLogin;
    
    @FXML
    private PasswordField userPassword;
    
    @FXML
    void loginAction() throws IOException {
        UserDto user = GeneralFunctions.login(userLogin.getText(), userPassword.getText());
        System.out.println(user);
        if (user == null) {
            labelStatus.setText("Неверный логин или пароль");
            return;
        }
    
        Stage stage = null;
        switch (user.getRole().getName()) {
            case "ADMIN" -> stage = StageManager.getStage("adminHome");
            case "COMMON" -> stage = StageManager.getStage("commonHome");
        }
        
        if (stage == null) {
            labelStatus.setText("Нет подходящего окна");
            return;
        }
        
        stage.show();
        Stage thisStage = StageManager.getStage("login");
        thisStage.hide();
    }
}
