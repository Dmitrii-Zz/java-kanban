package logic;

import task.*;

import java.util.ArrayList;
import java.util.List;

public class CSVTaskFormatter {

    public static Task fromString(String value) {
        String[] valueLine = value.split(",");
        int idTask = Integer.parseInt(valueLine[0]);
        Type typeTask = Type.valueOf(valueLine[1]);
        String nameTask = valueLine[2];
        StatusTask status = StatusTask.valueOf(valueLine[3]);
        String descriptionTask = valueLine[4];
        switch (typeTask) {
            case TASK:
                Task task = new Task();
                task.setIdTask(idTask);
                task.setTypeTask(typeTask);
                task.setNameTask(nameTask);
                task.setStatusTask(status);
                task.setDescriptionTask(descriptionTask);
                return task;
            case EPIC:
                Epic epic = new Epic();
                epic.setIdTask(idTask);
                epic.setTypeTask(typeTask);
                epic.setNameTask(nameTask);
                epic.setStatusTask(status);
                epic.setDescriptionTask(descriptionTask);
                return epic;
            case SUBTASK:
                Subtask subtask = new Subtask();
                subtask.setIdTask(idTask);
                subtask.setTypeTask(typeTask);
                subtask.setNameTask(nameTask);
                subtask.setStatusTask(status);
                subtask.setDescriptionTask(descriptionTask);
                int idEpic = Integer.parseInt(valueLine[5]);
                subtask.setIdEpic(idEpic);
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

        return content.deleteCharAt(content.length() - 1).toString();
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
        switch (typeTask) {
            case EPIC:
            case TASK:
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