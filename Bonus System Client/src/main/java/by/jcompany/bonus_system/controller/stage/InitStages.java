package by.jcompany.bonus_system.controller.stage;

public class InitStages {
    public static void create() {
        StageManager.addStage("login", "fxml/login.fxml");
        StageManager.addStage("adminHome", "fxml/adminHome.fxml");
        StageManager.addStage("addUser", "fxml/addUser.fxml");
        StageManager.addStage("changeUser", "fxml/changeUser.fxml");
        StageManager.addStage("commonHome", "fxml/commonHome.fxml");
    }
}
