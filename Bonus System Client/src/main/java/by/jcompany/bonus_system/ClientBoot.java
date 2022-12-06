package by.jcompany.bonus_system;

import by.jcompany.bonus_system.controller.stage.StageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class ClientBoot extends Application {
    private Point2D point;
    
    public static void main(String[] args) {
        launch();
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Stage stage = StageManager.getStage("login");
        stage.show();
    }
}