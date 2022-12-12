package by.jcompany.bonus_system.controller.stage;

public class InitStages {
    public static void create() {
        addStageWithSameName("login");
        addStageWithSameName("adminHome");
        addStageWithSameName("addUser");
        addStageWithSameName("addEmployee");
        addStageWithSameName("addTask");
        addStageWithSameName("addRole");
        addStageWithSameName("changeUser");
        addStageWithSameName("changeEmployee");
        addStageWithSameName("changeTask");
        addStageWithSameName("changeRole");
        addStageWithSameName("changePointCost");
        addStageWithSameName("showEmployeeInfo");
        addStageWithSameName("showInfo");
        addStageWithSameName("showProfileInfo");
        addStageWithSameName("payBonuses");
        addStageWithSameName("commonHome");
    }
    
    private static void addStageWithSameName(String stageName) {
        StageManager.addStage(stageName, "fxml/" + stageName + ".fxml");
        
    }
}
