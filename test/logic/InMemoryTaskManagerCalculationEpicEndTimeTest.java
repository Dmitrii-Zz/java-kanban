package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryTaskManagerCalculationEpicEndTimeTest {

    TaskManager taskManager = new InMemoryTaskManager();

    @Test
    public void calculationEpicEndTimeTest() {
        Epic epic = new Epic();
        epic.setNameTask("Эпик");
        epic.setDescriptionTask("Описание");
        taskManager.createEpic(epic);

        Subtask subtask1 = new Subtask();
        subtask1.setIdEpic(1);
        subtask1.setNameTask("Подзадача 1");
        subtask1.setDescriptionTask("Описание подзадачи 1");
        subtask1.setStatusTask(StatusTask.NEW);
        subtask1.setStartTime(LocalDateTime.of(2024, 5, 1, 12, 0));
        subtask1.setTaskDuration(20);
        taskManager.createSubTask(subtask1);

        Subtask subtask2 = new Subtask();
        subtask2.setIdEpic(1);
        subtask2.setNameTask("Подзадача 1");
        subtask2.setDescriptionTask("Описание подзадачи 1");
        subtask2.setStatusTask(StatusTask.NEW);
        subtask2.setStartTime(LocalDateTime.of(2024, 1, 1, 12, 0));
        subtask2.setTaskDuration(20);
        taskManager.createSubTask(subtask2);

        Subtask subtask3 = new Subtask();
        subtask3.setIdEpic(1);
        subtask3.setNameTask("Подзадача 1");
        subtask3.setDescriptionTask("Описание подзадачи 1");
        subtask3.setStatusTask(StatusTask.NEW);
        subtask3.setStartTime(LocalDateTime.of(2024, 3, 1, 12, 0));
        subtask3.setTaskDuration(20);
        taskManager.createSubTask(subtask3);

        LocalDateTime expectedStartDataTime = LocalDateTime.of(2024, 1, 1,12,0);
        LocalDateTime expectedEndDataTime = LocalDateTime.of(2024, 5, 1, 12, 20);

        Optional<LocalDateTime> dataTime = epic.getStartTime();
        Optional<LocalDateTime> dataEndTime = epic.getEndTime();

        if (dataTime.isPresent() && dataEndTime.isPresent()) {
            assertEquals(expectedStartDataTime, dataTime.get(), "Даты не равны");
            assertEquals(expectedEndDataTime, dataEndTime.get(), "endTime не равны");
            assertEquals(60, epic.getTaskDuration());
        }
    }
}
