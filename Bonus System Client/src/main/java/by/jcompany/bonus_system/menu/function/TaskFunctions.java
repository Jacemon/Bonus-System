package by.jcompany.bonus_system.menu.function;

import by.jcompany.bonus_system.dto.TaskDto;
import by.jcompany.bonus_system.model.Request;
import by.jcompany.bonus_system.model.Response;

import java.io.IOException;

public class TaskFunctions extends Functions {
    public static void createTask(String taskDescription) throws IOException, ClassNotFoundException {
        connection.makeRequest(new Request("CREATE_TASK",
            new TaskDto(taskDescription))
        );
        Response response = connection.getResponse();
        System.out.println(response);
    }
}
