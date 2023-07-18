package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryTaskManagerFillEpicStatusTest {

    private final TaskManager taskManager = Managers.getDefault();
    private final Epic epic = new Epic();
    private final Subtask subtask1 = new Subtask();
    private final Subtask subtask2 = new Subtask();

    @BeforeEach
    public void createEpicAndSubtask() {
        epic.setNameTask("Эпик!");
        epic.setDescriptionTask("Описание Эпика!");
        taskManager.createEpic(epic);

        subtask1.setNameTask("Подзадача 1!");
        subtask1.setDescriptionTask("Описание подзадачи 1!");
        subtask1.setIdEpic(1);
        subtask1.setStartTime(LocalDateTime.of(2025, 1, 1, 12, 40));
        subtask1.setTaskDuration(30);
        subtask2.setNameTask("Подзадача 2!");
        subtask2.setDescriptionTask("Описание подзадачи 2!");
        subtask2.setIdEpic(1);
        subtask2.setStartTime(LocalDateTime.of(2025, 1, 1, 12, 40));
        subtask2.setTaskDuration(30);
    }

    @Test
    public void fillEpicStatusVoidListSubTuskTest() {
        StatusTask expectedStatusTask = StatusTask.NEW;
        assertEquals(expectedStatusTask, epic.getStatusTask());
    }

    @Test
    public void fillEpicStatusAllSubTaskHasStatusTaskNewTest()  {
        StatusTask expectedStatusTask = StatusTask.NEW;
        subtask1.setStatusTask(StatusTask.NEW);
        subtask2.setStatusTask(StatusTask.NEW);
        taskManager.createSubTask(subtask1);
        taskManager.createSubTask(subtask2);
        assertEquals(expectedStatusTask, epic.getStatusTask());
    }

    @Test
    public void fillEpicStatusAllSubTaskHasStatusTaskDoneTest()  {
        StatusTask expectedStatusTask = StatusTask.DONE;
        subtask1.setStatusTask(StatusTask.DONE);
        subtask2.setStatusTask(StatusTask.DONE);
        taskManager.createSubTask(subtask1);
        taskManager.createSubTask(subtask2);
        assertEquals(expectedStatusTask, epic.getStatusTask());
    }

    @Test
    public void fillEpicStatusAllSubTaskHasStatusTaskNewAndDoneTest()  {
        StatusTask expectedStatusTask = StatusTask.IN_PROGRESS;
        subtask1.setStatusTask(StatusTask.NEW);
        subtask2.setStatusTask(StatusTask.DONE);
        taskManager.createSubTask(subtask1);
        taskManager.createSubTask(subtask2);
        assertEquals(expectedStatusTask, epic.getStatusTask());
    }

    @Test
    public void fillEpicStatusAllSubTaskHasStatusTaskInProgressTest()  {
        StatusTask expectedStatusTask = StatusTask.IN_PROGRESS;
        subtask1.setStatusTask(StatusTask.IN_PROGRESS);
        subtask2.setStatusTask(StatusTask.IN_PROGRESS);
        taskManager.createSubTask(subtask1);
        taskManager.createSubTask(subtask2);
        assertEquals(expectedStatusTask, epic.getStatusTask());
    }
}