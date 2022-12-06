package by.jcompany.bonus_system.controller.stage;

import by.jcompany.bonus_system.ClientBoot;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StageManager {
    private static final Map<String, StageWithUrl> stageMap = new HashMap<>();
    
    @Getter
    @Setter
    @AllArgsConstructor
    public static class StageWithUrl {
        private Stage stage;
        private String url;
    }
    
    static {
        InitStages.create();
    }
    
    private static Point2D point;
    
    public static void addStage(String stageName, String url) {
        stageMap.put(stageName, new StageWithUrl(null, url));
    }
    
    public static void wasteStage(String stageName) {
        stageMap.get(stageName).setStage(null);
    }
    
    public static void removeStage(String stageName) {
        stageMap.remove(stageName);
    }
    
    public static Stage getStage(String stageName) throws IOException {
        if (stageMap.get(stageName).getStage() == null) {
            return reloadAndGetStage(stageName);
        }
        return stageMap.get(stageName).getStage();
    }
    
    public static Stage reloadAndGetStage(String stageName) throws IOException {
        String url = stageMap.get(stageName).getUrl();
        
        Stage stage = new Stage();
        
        Scene scene = new Scene(FXMLLoader.load(
            Objects.requireNonNull(
                ClientBoot.class.getResource(url))));
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        
        Parent parent = scene.getRoot();
        parent.setOnMousePressed(mouseEvent -> {
            point = new Point2D(mouseEvent.getSceneX(), mouseEvent.getSceneY());
        });
        parent.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - point.getX());
            stage.setY(mouseEvent.getScreenY() - point.getY());
        });
        
        stageMap.get(stageName).setStage(stage);
        
        return stageMap.get(stageName).getStage();
    }
}
