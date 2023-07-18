package logic;

import org.junit.jupiter.api.Test;
import task.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTasksManagerTest {

    private final File file = new File("./test/resources/Resources.csv");
    private final FileBackedTasksManager backedTask = FileBackedTasksManager.loadFromFile(file);

    @Test
    public void loadTaskFromFileTest() {
        List<Task> allTask = backedTask.getAllTask();
        assertEquals(2, allTask.size(), "Неверное кол-во");

        Task task = new Task();
        task.setIdTask(1);
        task.setTypeTask(Type.TASK);
        task.setNameTask("Задача 1.");
        task.setDescriptionTask("Описание задачи 1.");
        task.setStartTime(LocalDateTime.of(2025, 1, 1, 12, 30));
        task.setTaskDuration(30);
        task.setStatusTask(StatusTask.NEW);

        assertEquals(task, allTask.get(0), "Задачи не равны");
    }

    @Test
    public void loadEpicFromFileTest() {
        List<Epic> allEpic = backedTask.getAllEpic();
        assertEquals(1, allEpic.size(), "Неверное кол-во");

        Epic epic = new Epic();
        epic.setIdTask(2);
        epic.setTypeTask(Type.EPIC);
        epic.setNameTask("Эпик 1.");
        epic.setDescriptionTask("Описание эпика 1.");
        epic.setStatusTask(StatusTask.DONE);
        epic.setIdSubTasks(List.of(3));
        epic.setStartTime(LocalDateTime.of(2025, 1, 2, 12, 45));
        epic.setTaskDuration(30);
    }

    @Test
    public void loadSubtaskFromFileTest() {
        List<Subtask> allSubtask = backedTask.getAllSubTask();
        assertEquals(1, allSubtask.size(), "Неверное кол-во");

        Subtask subtask = new Subtask();
        subtask.setIdTask(4);
        subtask.setTypeTask(Type.SUBTASK);
        subtask.setNameTask("Подзадача 1");
        subtask.setDescriptionTask("Описание подзадачи 1");
        subtask.setStatusTask(StatusTask.DONE);
        subtask.setStartTime(LocalDateTime.of(2025, 1, 2, 12, 45));
        subtask.setTaskDuration(30);
        subtask.setIdEpic(3);

        assertEquals(subtask, allSubtask.get(0), "Задачи не равны");
    }
}
