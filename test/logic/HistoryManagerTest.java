package logic;

import org.junit.jupiter.api.Test;
import task.StatusTask;
import task.Task;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class HistoryManagerTest {

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    public void addTaskAndGetHistoryTest() {
        Task task = new Task();
        task.setNameTask("Новая задача.");
        task.setDescriptionTask("Описание новой задачи.");
        task.setStatusTask(StatusTask.NEW);
        task.setIdTask(1);

        List<Task> tasks = historyManager.getHistory();
        assertEquals(0, tasks.size(), "список не пуст");

        historyManager.addTask(task);
        tasks = historyManager.getHistory();
        assertEquals(1, tasks.size(), "Списки не равны.");
    }

    @Test
    public void removeTest() {
        Task task = new Task();
        task.setNameTask("Новая задача.");
        task.setDescriptionTask("Описание новой задачи.");
        task.setStatusTask(StatusTask.NEW);
        task.setIdTask(1);
        historyManager.addTask(task);

        Task task1 = new Task();
        task1.setNameTask("Новая задача.");
        task1.setDescriptionTask("Описание новой задачи.");
        task1.setStatusTask(StatusTask.NEW);
        task1.setIdTask(2);
        historyManager.addTask(task1);

        List<Task> tasks = historyManager.getHistory();
        assertEquals(2, tasks.size(), "Списки не равны.");

        historyManager.remove(1);
        tasks = historyManager.getHistory();
        assertEquals(1, tasks.size(), "Списки не равны.");
    }
}
