package logic;

import task.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileBackedTasksManager extends InMemoryTaskManager {

    private final HistoryManager historyManager = super.getHistoryManager();

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void deleteTask() {
        super.deleteTask();
        save();
    }

    @Override
    public void deleteTaskId(int idTask) {
        super.deleteTaskId(idTask);
        save();
    }

    @Override
    public Task getTask(int idTask) {
        Task task = super.getTask(idTask);
        save();
        return task;
    }

    @Override
    public List<Task> getAllTask() {
        List<Task> tasks = super.getAllTask();
        save();
        return tasks;
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteEpic() {
        super.deleteEpic();
        save();
    }

    @Override
    public void deleteEpicId(int idEpic) {
        super.deleteEpicId(idEpic);
        save();
    }

    @Override
    public Epic getEpicId(int idEpic) {
        Epic epic = super.getEpicId(idEpic);
        save();
        return epic;
    }

    @Override
    public List<Epic> getAllEpic() {
        List<Epic> epics = super.getAllEpic();
        save();
        return epics;
    }

    @Override
    public void createSubTask(Subtask subtask) {
        super.createSubTask(subtask);
        save();
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        super.updateSubTask(subtask);
        save();
    }

    @Override
    public void deleteSubTask() {
        super.deleteSubTask();
        save();
    }

    @Override
    public void deleteSubTaskId(int idSubTask) {
        super.deleteSubTaskId(idSubTask);
        save();
    }

    @Override
    public Subtask getSubTaskId(int idSubTask) {
        Subtask subtask = super.getSubTaskId(idSubTask);
        save();
        return subtask;
    }

    @Override
    public List<Subtask> getAllSubTask() {
        List<Subtask> subtasks = super.getAllSubTask();
        save();
        return subtasks;
    }

    @Override
    public List<Subtask> getAllSubTaskEpicId(int idEpic) {
        List<Subtask> subtasks = super.getAllSubTaskEpicId(idEpic);
        save();
        return subtasks;
    }

    public void save() {
        File file = new File("./src/resources/Resources.csv");
        try (Writer writer = new FileWriter(file)) {
            writer.write(serialize());
            writer.write(historyToString(historyManager));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public String serialize() {
        return super.serialize();
    }

    public Task fromString(String value) {
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
                task.setNameTask(nameTask);
                task.setStatusTask(status);
                task.setDescriptionTask(descriptionTask);
                return task;
            case EPIC:
                Epic epic = new Epic();
                epic.setIdTask(idTask);
                epic.setNameTask(nameTask);
                epic.setStatusTask(status);
                epic.setDescriptionTask(descriptionTask);
                return epic;
            case SUBTASK:
                Subtask subtask = new Subtask();
                subtask.setIdTask(idTask);
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
        for (int i = idTask.length - 1; i >= 0; i--) {
            idTaskHistory.add(Integer.parseInt(idTask[i]));
        }
        return idTaskHistory;
    }

    public static FileBackedTasksManager loadFromFile(File file) {

        FileBackedTasksManager backedTasksManager = new FileBackedTasksManager();
        int idMax = 0;
        StringBuilder content = new StringBuilder();

        try {
            Reader fileReader = new FileReader(file);

            int data = fileReader.read();

            while (data != -1) {
                content.append((char) data);
                data = fileReader.read();
            }

            String[] contentSplit = content.toString().split("\n\n");
            String[] taskContent = contentSplit[0].split("\n");

            for (int i = 1; i < taskContent.length; i++) {
                String[] valueLine = taskContent[i].split(",");
                Type typeTask = Type.valueOf(valueLine[1]);
                switch (typeTask) {
                    case TASK:
                        backedTasksManager.createTask(backedTasksManager.fromString(taskContent[i]));
                        break;
                    case EPIC:
                        backedTasksManager.createEpic((Epic) backedTasksManager.fromString(taskContent[i]));
                        break;
                    case SUBTASK:
                        backedTasksManager.createSubTask((Subtask) backedTasksManager.fromString(taskContent[i]));
                        break;
                }

                if (Integer.parseInt(valueLine[0]) > idMax) {
                    idMax = Integer.parseInt(valueLine[0]);
                }
            }

            backedTasksManager.setTaskId(idMax);

            List<Integer> idTaskHistory = historyFromString(contentSplit[1]);

            for (int id : idTaskHistory) {
                backedTasksManager.getTask(id);
                backedTasksManager.getEpicId(id);
                backedTasksManager.getSubTaskId(id);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return backedTasksManager;
    }

    public static void main(String[] args) {

        File file = new File("./src/resources/Resources.csv");
        FileBackedTasksManager backedTask = FileBackedTasksManager.loadFromFile(file);

        for (int i = 1; i <= 1; i++) {
            Task task = new Task();
            task.setNameTask("Задача " + i + ".");
            task.setDescriptionTask("Описание задачи " + i + ".");
            task.setStatusTask(StatusTask.NEW);
            backedTask.createTask(task);
        }

        for (int i = 3; i <= 3; i++) {
            Epic epic = new Epic();
            epic.setNameTask("Эпик " + i + ".");
            epic.setDescriptionTask("Описание эпика " + i + ".");
            backedTask.createEpic(epic);
        }

        for (int i = 6; i <= 6; i++) {
            Subtask subtask = new Subtask();
            subtask.setNameTask("Подзадача " + i);
            subtask.setDescriptionTask("Описание подзадачи " + i);
            subtask.setStatusTask(StatusTask.NEW);
            subtask.setIdEpic(2);
            backedTask.createSubTask(subtask);
        }

        System.out.println("Кол-во созданных задач: " + backedTask.getAllTask().size());
        System.out.println("Кол-во созданных эпиков: " + backedTask.getAllEpic().size());
        System.out.println("Кол-во созданных подзадач: " + backedTask.getAllSubTask().size());

        Task task = backedTask.getTask(1);

        System.out.println(backedTask.getHistory());

        Task task2 = backedTask.getTask(1);
        Task task3 = backedTask.getTask(2);

        System.out.println(backedTask.getHistory());

        Epic epic = backedTask.getEpicId(4);
        Task task4 = backedTask.getTask(1);

        System.out.println(backedTask.getHistory());

        Epic epic1 = backedTask.getEpicId(3);
        Task task5 = backedTask.getTask(2);

        System.out.println(backedTask.getHistory());

        Subtask subtask = backedTask.getSubTaskId(5);
        Subtask subtask1 = backedTask.getSubTaskId(6);
        Subtask subtask2 = backedTask.getSubTaskId(7);

    }
}