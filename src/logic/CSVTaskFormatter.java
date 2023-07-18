package logic;

import task.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CSVTaskFormatter {

    private static final int ID_TASK = 0;
    private static final int TYPE_TASK = 1;
    private static final int CLEAR_END_CHAR = 1;
    private static final int NAME_TASK = 2;
    private static final int STATUS_TASK = 3;
    private static final int DESCRIPTION_TASK = 4;
    private static final int DURATION_TASK = 5;
    private static final int START_TIME_TASK = 6;
    private static final int END_TIME_TASK = 7;
    private static final int CROSS_TASK = 8;
    private static final int ID_SUB_TASK = 9;

    public static Task fromString(String value) {
        String[] valueLine = value.split(",");
        int idTask = Integer.parseInt(valueLine[ID_TASK]);
        Type typeTask = Type.valueOf(valueLine[TYPE_TASK]);
        String nameTask = valueLine[NAME_TASK];
        StatusTask status = StatusTask.valueOf(valueLine[STATUS_TASK]);
        String descriptionTask = valueLine[DESCRIPTION_TASK];
        int taskDuration = Integer.parseInt(valueLine[DURATION_TASK]);
        boolean crossTask = Boolean.parseBoolean(valueLine[CROSS_TASK]);

        switch (typeTask) {
            case TASK:
                Task task = new Task();
                task.setIdTask(idTask);
                task.setTypeTask(typeTask);
                task.setNameTask(nameTask);
                task.setStatusTask(status);
                task.setDescriptionTask(descriptionTask);
                task.setTaskDuration(taskDuration);
                task.setIsCrossTask(crossTask);

                if (!valueLine[START_TIME_TASK].equals("null")) {
                    task.setStartTime(LocalDateTime.parse(valueLine[START_TIME_TASK]));
                }
                return task;
            case EPIC:
                Epic epic = new Epic();
                epic.setIdTask(idTask);
                epic.setTypeTask(typeTask);
                epic.setNameTask(nameTask);
                epic.setStatusTask(status);
                epic.setDescriptionTask(descriptionTask);
                epic.setTaskDuration(taskDuration);
                epic.setIsCrossTask(crossTask);

                if (!valueLine[START_TIME_TASK].equals("null")) {
                    epic.setStartTime(LocalDateTime.parse(valueLine[START_TIME_TASK]));
                }

                if (!valueLine[END_TIME_TASK].equals("null")) {
                    epic.setEndTime(LocalDateTime.parse(valueLine[END_TIME_TASK]));
                }


                if (valueLine.length > ID_SUB_TASK) {
                    String[] idEpics = valueLine[ID_SUB_TASK].split("-");

                    for (String id : idEpics) {
                        List<Integer> idSubTasks = epic.getIdSubTasks();
                        idSubTasks.add(Integer.parseInt(id));
                        epic.setIdSubTasks(idSubTasks);
                    }
                }

                return epic;
            case SUBTASK:
                Subtask subtask = new Subtask();
                subtask.setIdTask(idTask);
                subtask.setTypeTask(typeTask);
                subtask.setNameTask(nameTask);
                subtask.setStatusTask(status);
                subtask.setDescriptionTask(descriptionTask);
                int idEpic = Integer.parseInt(valueLine[ID_SUB_TASK]);
                subtask.setIdEpic(idEpic);
                subtask.setTaskDuration(taskDuration);
                subtask.setIsCrossTask(crossTask);

                if (!valueLine[START_TIME_TASK].equals("null")) {
                    subtask.setStartTime(LocalDateTime.parse(valueLine[START_TIME_TASK]));
                }

                return subtask;
        }

        return null;
    }

    public static String historyToString(HistoryManager manager) {
        List<Task> history = manager.getHistory();
        StringBuilder content = new StringBuilder("\n");

        for (Task task : history) {
            content.append(task.getIdTask());
            content.append(",");
        }

        return content.deleteCharAt(content.length() - CLEAR_END_CHAR).toString();
    }

    public static List<Integer> historyFromString(String value) {

        String[] idTask = value.split(",");
        List<Integer> idTaskHistory = new ArrayList<>();

        for (String id : idTask) {
            idTaskHistory.add(Integer.parseInt(id));
        }

        return idTaskHistory;
    }

    public static String toString(Task task) {

        Type typeTask = task.getTypeTask();

        StringBuilder taskToString = new StringBuilder();
        taskToString.append(task.getIdTask());
        taskToString.append(",");
        taskToString.append(task.getTypeTask());
        taskToString.append(",");
        taskToString.append(task.getNameTask());
        taskToString.append(",");
        taskToString.append(task.getStatusTask());
        taskToString.append(",");
        taskToString.append(task.getDescriptionTask());
        taskToString.append(",");
        taskToString.append(task.getTaskDuration());
        taskToString.append(",");

        Optional<LocalDateTime> dataTime = task.getStartTime();

        if (dataTime.isPresent()) {
            taskToString.append(dataTime.get());
        } else {
            taskToString.append("null");
        }

        taskToString.append(",");
        Optional<LocalDateTime> endTime = task.getEndTime();

        if (endTime.isPresent()) {
            taskToString.append(endTime.get());
        } else {
            taskToString.append("null");
        }

        taskToString.append(",");
        taskToString.append(task.getIsCrossTask());
        taskToString.append(",");
        switch (typeTask) {
            case TASK:
                taskToString.append("\n");
                return taskToString.toString();
            case EPIC:
                Epic newEpic = (Epic) task;
                List<Integer> idSubTask = newEpic.getIdSubTasks();

                for (int id : idSubTask) {
                    taskToString.append(id);
                    taskToString.append("-");
                }

                taskToString.append("\n");
                return taskToString.toString();
            case SUBTASK:
                Subtask newSubtask = (Subtask) task;
                taskToString.append(newSubtask.getIdEpic());
                taskToString.append("\n");
                return taskToString.toString();
        }

        return null;
    }
}