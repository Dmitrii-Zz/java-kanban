package logic;

import org.junit.jupiter.api.Test;
import task.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CSVTaskFormatterTest {

    private final HistoryManager historyManagers = Managers.getDefaultHistory();

    @Test
    public void TaskToStringTaskTest() {
        Task task = new Task();
        task.setNameTask("Задача 1.");
        task.setDescriptionTask("Описание задачи 1.");
        task.setStatusTask(StatusTask.NEW);
        task.setIdTask(1);
        task.setTypeTask(Type.TASK);
        task.setTaskDuration(30);
        task.setStartTime(LocalDateTime.of(2025, 1, 1, 12, 30));
        task.setIsCrossTask(false);
        String taskContent = CSVTaskFormatter.toString(task);

        String expectedTaskContent =
                "1,TASK,Задача 1.,NEW,Описание задачи 1.,30,2025-01-01T12:30,2025-01-01T13:00,false,\n";
        assertEquals(expectedTaskContent, taskContent, "Контент не совпадает.");
    }

    @Test
    public void TaskToStringEpicTest() {
        Epic epic = new Epic();
        epic.setNameTask("Эпик 1.");
        epic.setDescriptionTask("Описание эпика 1.");
        epic.setStatusTask(StatusTask.DONE);
        epic.setIdTask(3);
        epic.setTypeTask(Type.EPIC);
        epic.setIdSubTasks(List.of(4));
        epic.setIsCrossTask(false);
        epic.setTaskDuration(30);
        String epicContent = CSVTaskFormatter.toString(epic);

        String expectedEpicContent = "3,EPIC,Эпик 1.,DONE,Описание эпика 1.,30,null,null,false,4-\n";
        assertEquals(expectedEpicContent, epicContent, "Контент различается");
    }

    @Test
    public void TaskToStringSubtaskTest() {
        Subtask subtask = new Subtask();
        subtask.setNameTask("Подзадача 1");
        subtask.setStatusTask(StatusTask.DONE);
        subtask.setDescriptionTask("Описание подзадачи 1");
        subtask.setIdEpic(3);
        subtask.setIdTask(4);
        subtask.setTypeTask(Type.SUBTASK);
        subtask.setStartTime(LocalDateTime.of(2025, 1, 2, 12, 45));
        subtask.setTaskDuration(30);
        String subTaskContent = CSVTaskFormatter.toString(subtask);

        String expectedSubtaskContent =
                "4,SUBTASK,Подзадача 1,DONE,Описание подзадачи 1,30,2025-01-02T12:45,2025-01-02T13:15,false,3\n";
        assertEquals(expectedSubtaskContent, subTaskContent, "Строки не равны.");
    }

    @Test
    public void historyToStringTest() {
        Task task = new Task();
        task.setNameTask("Новая задача.");
        task.setDescriptionTask("Описание новой задачи.");
        task.setStatusTask(StatusTask.NEW);
        task.setIdTask(1);
        task.setTypeTask(Type.TASK);
        historyManagers.addTask(task);

        Task task1 = new Task();
        task1.setNameTask("Новая задача.");
        task1.setDescriptionTask("Описание новой задачи.");
        task1.setStatusTask(StatusTask.NEW);
        task1.setIdTask(2);
        task1.setTypeTask(Type.TASK);
        historyManagers.addTask(task1);

        String contentHistory = CSVTaskFormatter.historyToString(historyManagers);
        String expectedContentHistory = "\n2,1";

        assertEquals(expectedContentHistory, contentHistory, "Истории не равны");
    }

    @Test
    public void TaskFromStringTaskTest() {
        String taskContent = "1,TASK,Задача 1.,NEW,Описание задачи 1.,30,2025-01-01T12:30,2025-01-01T13:00,false,";
        Task task = CSVTaskFormatter.fromString(taskContent);

        assertAll("сравнение полей задачи,",
                () -> assertEquals(1, task.getIdTask(), "ИД не равны"),
                () -> assertEquals(Type.TASK, task.getTypeTask(), "Типы не равны"),
                () -> assertEquals("Задача 1.", task.getNameTask(), "Имена не равны"),
                () -> assertEquals(StatusTask.NEW, task.getStatusTask(), "Статусы не равны"),
                () -> assertEquals("Описание задачи 1.",
                        task.getDescriptionTask(),
                        "описание неверно"));
    }

    @Test
    public void TaskFromStringEpicTest() {
        String epicContent = "2,EPIC,Эпик 1.,DONE,Описание эпика 1.,30,2025-01-02T12:45,2025-01-02T13:15,3-";
        Task epic = CSVTaskFormatter.fromString(epicContent);

        assertAll("сравнение полей задачи,",
                () -> assertEquals(2, epic.getIdTask(), "ИД не равны"),
                () -> assertEquals(Type.EPIC, epic.getTypeTask(), "Типы не равны"),
                () -> assertEquals("Эпик 1.", epic.getNameTask(), "Имена не равны"),
                () -> assertEquals(StatusTask.DONE, epic.getStatusTask(), "Статусы не равны"),
                () -> assertEquals("Описание эпика 1.",
                        epic.getDescriptionTask(),
                        "описание неверно"));
    }

    @Test
    public void TaskFromStringSubtaskTest() {
        String subTaskContent =
                "4,SUBTASK,Подзадача 1,DONE,Описание подзадачи 1,30,2025-01-02T12:45,2025-01-02T13:15,false,3";
        Subtask subTask = (Subtask) CSVTaskFormatter.fromString(subTaskContent);

        assertAll("сравнение полей задачи,",
                () -> assertEquals(4, subTask.getIdTask(), "ИД не равны"),
                () -> assertEquals(Type.SUBTASK, subTask.getTypeTask(), "Типы не равны"),
                () -> assertEquals("Подзадача 1", subTask.getNameTask(), "Имена не равны"),
                () -> assertEquals(StatusTask.DONE, subTask.getStatusTask(), "Статусы не равны"),
                () -> assertEquals("Описание подзадачи 1",
                        subTask.getDescriptionTask(),
                        "описание неверно"),
                () -> assertEquals(3, subTask.getIdEpic(), "Неверный эпик"));
    }


    @Test
    public void historyFromStringTest() {

        String historyContent = "2,1";
        List<Integer> history = CSVTaskFormatter.historyFromString(historyContent);

        assertEquals(2, history.get(0), "Неверно");
        assertEquals(1, history.get(1), "Неверно");
    }
}
