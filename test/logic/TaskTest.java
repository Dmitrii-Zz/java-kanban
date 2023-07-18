package logic;

import org.junit.jupiter.api.Test;
import task.*;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    public void getEndTime() {
        Task task = new Task();
        task.setNameTask("Новая задача.");
        task.setDescriptionTask("Описание новой задачи.");
        task.setStatusTask(StatusTask.NEW);
        task.setIdTask(1);
        task.setStartTime(LocalDateTime.of(2023, 7, 15, 12, 0,0));
        task.setTaskDuration(40);

        LocalDateTime expectedEndTime = LocalDateTime.of(2023, 7, 15, 12, 40);

        Optional<LocalDateTime> optionalEndTime = task.getEndTime();

        optionalEndTime.ifPresent(localDateTime ->
                assertEquals(expectedEndTime, localDateTime, "время не совпало"));
    }
}
