package by.jcompany.bonus_system.controller.stage;

public class InitStages {
    public static void create() {
        StageManager.addStage("login", "fxml/login.fxml");
        StageManager.addStage("adminHome", "fxml/adminHome.fxml");
        StageManager.addStage("addUser", "fxml/addUser.fxml");
        StageManager.addStage("addEmployee", "fxml/addEmployee.fxml");
        StageManager.addStage("addTask", "fxml/addTask.fxml");
        StageManager.addStage("addRole", "fxml/addRole.fxml");
        StageManager.addStage("changeUser", "fxml/changeUser.fxml");
        StageManager.addStage("changeEmployee", "fxml/changeEmployee.fxml");
        StageManager.addStage("changeTask", "fxml/changeTask.fxml");
        StageManager.addStage("changeRole", "fxml/changeRole.fxml");
        StageManager.addStage("commonHome", "fxml/commonHome.fxml");
    }
}
