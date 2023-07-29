package logic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import task.StatusTask;
import task.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonTypeGetTest {
    @Test
    public void loadTaskServer() {
        List<Task> tasks = new ArrayList<>();

        Task task = new Task();
        task.setIdTask(1);
        task.setNameTask("Проверка");
        task.setDescriptionTask("Проверить");
        task.setTaskDuration(10);
        task.setStatusTask(StatusTask.IN_PROGRESS);
        task.setStartTime(LocalDateTime.of(2023, 7, 29, 20, 49));

        tasks.add(task);

        Epic epic = new Epic();
        epic.setIdTask(2);
        epic.setNameTask("Проверка");
        epic.setDescriptionTask("Проверить");
        epic.setTaskDuration(10);
        epic.setStatusTask(StatusTask.IN_PROGRESS);
        epic.setStartTime(LocalDateTime.of(2023, 7, 29, 20, 49));
        epic.setIdSubTasks(List.of(3));

        tasks.add(epic);

        Subtask subtask = new Subtask();
        subtask.setIdTask(3);
        subtask.setIdEpic(2);
        subtask.setNameTask("Проверка");
        subtask.setDescriptionTask("Проверить");
        subtask.setTaskDuration(10);
        subtask.setStatusTask(StatusTask.IN_PROGRESS);
        subtask.setStartTime(LocalDateTime.of(2023, 7, 29, 20, 49));

        tasks.add(subtask);

        Gson gson = new Gson();
        String json = gson.toJson(tasks);

        String jsonTasks = "[{\"nameTask\":\"Доделать ФП\",\"descriptionTask\":\"У меня все получится\",\"statusTask\":\"NEW\",\"idTask\":1,\"taskDuration\":30},{\"nameTask\":\"Доделать ФП\",\"descriptionTask\":\"У меня все получится\",\"statusTask\":\"NEW\",\"idTask\":2,\"taskDuration\":30},{\"nameTask\":\"Доделать ФП\",\"descriptionTask\":\"У меня все получится\",\"statusTask\":\"NEW\",\"idTask\":3,\"taskDuration\":30}]";
        Type type = TypeToken.getParameterized(ArrayList.class, Task.class, Epic.class, Subtask.class).getType();
        ArrayList<Task> allTask = gson.fromJson(json, type);
        System.out.println("готово");

    }
}
