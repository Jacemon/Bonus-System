package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.function.TaskFunctions;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangePointCostController implements Initializable {
    @FXML
    private Button closeButton;
    
    @FXML
    private TextField taskNewPointCost;
    
    @FXML
    private Label taskPointCost;
    
    @FXML
    private Label labelStatus;
    
    @FXML
    public void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
    
    @FXML
    void changeTaskAction() {
        String status = TaskFunctions.setPointCost(Float.valueOf(taskNewPointCost.getText()));
        labelStatus.setText(status);
        System.out.println(status);
        reloadPointCost();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reloadPointCost();
    }
    
    private void reloadPointCost() {
        taskPointCost.setText("error");
        Float pointCost = TaskFunctions.getPointCost();
        if (pointCost != null) {
            taskPointCost.setText(pointCost.toString());
        }
    }
}
