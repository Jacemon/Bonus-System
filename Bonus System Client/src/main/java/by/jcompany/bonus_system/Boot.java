package by.jcompany.bonus_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class Boot extends Application {
    private Point2D homePoint;
    
    public static void main(String[] args) {
        launch();
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Boot.class.getResource("fxml/adminHome.fxml")));
        Scene scene = new Scene(root);
        root.setOnMousePressed(mouseEvent -> {
            homePoint = new Point2D(mouseEvent.getSceneX(), mouseEvent.getSceneY());
        });
        root.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - homePoint.getX());
            stage.setY(mouseEvent.getScreenY() - homePoint.getY());
        });
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
}