package by.jcompany.bonus_system.controller;

import by.jcompany.bonus_system.function.TaskFunctions;
import by.jcompany.bonus_system.model.dto.TaskDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class ShowInfoController implements Initializable {
    
    @FXML
    private BarChart<String, Float> barChart;
    
    @FXML
    private Button closeButton;
    
    @FXML
    private PieChart pieChart;
    
    @FXML
    private CategoryAxis yearAxis;
    
    @FXML
    void closeButtonAction() {
        ((Stage) closeButton.getScene().getWindow()).close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initBarChart();
        initPieChart();
    }
    
    void initPieChart() {
        List<TaskDto> tasks = TaskFunctions.readAllTasks();
        assert tasks != null;
        
        int notCompleted = 0;
        int completed = 0;
        
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        Calendar taskDate = Calendar.getInstance();
        
        PieChart.Data slice;
        for (TaskDto task : tasks) {
            taskDate.setTime(Date.from(task.getCreationTime()));
            int taskYear = taskDate.get(Calendar.YEAR);
            
            if (task.isPaid() || taskYear != currentYear) {
                continue;
            }
            
            if (task.isCompleted()) {
                completed++;
            } else if (task.getEmployee() == null) {
                notCompleted++;
            } else {
                slice = new PieChart.Data(task.getEmployee().getFirstName() + " " +
                    task.getEmployee().getLastName(), 1);
                pieChart.getData().add(slice);
            }
        }
        slice = new PieChart.Data("Выполнена", completed);
        pieChart.getData().add(slice);
        slice = new PieChart.Data("Не взята", notCompleted);
        pieChart.getData().add(slice);
    }
    
    void initBarChart() {
        int columnsCount = 5;
        
        Float taskPointCost = TaskFunctions.getPointCost();
        
        ObservableList<String> years = FXCollections.observableArrayList();
        
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int year = currentYear - columnsCount - 1; year <= currentYear; year++) {
            years.add(Integer.toString(year));
        }
        
        Float[] yearsBonuses = new Float[years.size()];
        Arrays.fill(yearsBonuses, 0.0f);
        
        yearAxis.setCategories(years);
        
        XYChart.Series<String, Float> series = new XYChart.Series<>();
        
        List<TaskDto> tasks = TaskFunctions.readAllTasks();
        assert tasks != null;
        
        for (TaskDto task : tasks) {
            Calendar taskDate = Calendar.getInstance();
            taskDate.setTime(Date.from(task.getCreationTime()));
            int taskYear = taskDate.get(Calendar.YEAR);
            if (taskYear < currentYear - columnsCount - 1 || taskYear > currentYear ||
                !task.isPaid() || task.getEmployee() == null) {
                continue;
            }
            
            yearsBonuses[taskYear - currentYear + columnsCount + 1] +=
                task.getBonus().getAmount(task.getEmployee(), taskPointCost);
        }
        for (String year : years) {
            series.getData().add(new XYChart.Data<>(year,
                yearsBonuses[Integer.parseInt(year) - currentYear + columnsCount + 1]));
        }
        
        barChart.getData().add(series);
        barChart.setLegendVisible(false);
    }
}
