package logic;

import task.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileBackedTasksManager extends InMemoryTaskManager {

    @Override
    public void createTask(Task task) throws ManagerSaveException {
        super.createTask(task);
        save();
    }

    @Override
    public void updateTask(Task task) throws ManagerSaveException {
        super.updateTask(task);
        save();
    }

    @Override
    public void deleteTask() throws ManagerSaveException {
        super.deleteTask();
        save();
    }

    @Override
    public void deleteTaskId(int idTask) throws ManagerSaveException {
        super.deleteTaskId(idTask);
        save();
    }

    @Override
    public Task getTask(int idTask) throws ManagerSaveException {
        Task task = super.getTask(idTask);
        save();
        return task;
    }

    @Override
    public List<Task> getAllTask() throws ManagerSaveException {
        List<Task> tasks = super.getAllTask();
        save();
        return tasks;
    }

    @Override
    public void createEpic(Epic epic) throws ManagerSaveException {
        super.createEpic(epic);
        save();
    }

    @Override
    public void updateEpic(Epic epic) throws ManagerSaveException {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteEpic() throws ManagerSaveException {
        super.deleteEpic();
        save();
    }

    @Override
    public void deleteEpicId(int idEpic) throws ManagerSaveException {
        super.deleteEpicId(idEpic);
        save();
    }

    @Override
    public Epic getEpicId(int idEpic) throws ManagerSaveException {
        Epic epic = super.getEpicId(idEpic);
        save();
        return epic;
    }

    @Override
    public List<Epic> getAllEpic() throws ManagerSaveException {
        List<Epic> epics = super.getAllEpic();
        save();
        return epics;
    }

    @Override
    public void createSubTask(Subtask subtask) throws ManagerSaveException {
        super.createSubTask(subtask);
        save();
    }

    @Override
    public void updateSubTask(Subtask subtask) throws ManagerSaveException {
        super.updateSubTask(subtask);
        save();
    }

    @Override
    public void deleteSubTask() throws ManagerSaveException {
        super.deleteSubTask();
        save();
    }

    @Override
    public void deleteSubTaskId(int idSubTask) throws ManagerSaveException {
        super.deleteSubTaskId(idSubTask);
        save();
    }

    @Override
    public Subtask getSubTaskId(int idSubTask) throws ManagerSaveException {
        Subtask subtask = super.getSubTaskId(idSubTask);
        save();
        return subtask;
    }

    @Override
    public List<Subtask> getAllSubTask() throws ManagerSaveException {
        List<Subtask> subtasks = super.getAllSubTask();
        save();
        return subtasks;
    }

    @Override
    public List<Subtask> getAllSubTaskEpicId(int idEpic) throws ManagerSaveException {
        List<Subtask> subtasks = super.getAllSubTaskEpicId(idEpic);
        save();
        return subtasks;
    }

    private void save() throws ManagerSaveException {
        File file = new File("./src/resources/Resources.csv");
        try (Writer writer = new FileWriter(file)) {
            writer.write(serialize());
            writer.write(historyToString(historyManager));
        } catch (Exception e) {
            throw new ManagerSaveException("Ошибка чтения файла!");
        }
    }

    private Task fromString(String value) {
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

    private static String historyToString(HistoryManager manager) {
        List<Task> history = manager.getHistory();
        StringBuilder content = new StringBuilder("\n");

        for (Task task : history) {
            content.append(task.getIdTask());
            content.append(",");
        }

        return content.deleteCharAt(content.length() - 1).toString();
    }

    private static List<Integer> historyFromString(String value) {

        String[] idTask = value.split(",");
        List<Integer> idTaskHistory = new ArrayList<>();

        for (String id : idTask) {
            idTaskHistory.add(Integer.parseInt(id));
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
                        backedTasksManager.tasks.put(Integer.parseInt(valueLine[0]),
                                backedTasksManager.fromString(taskContent[i]));
                        break;
                    case EPIC:
                        backedTasksManager.epics.put(Integer.parseInt(valueLine[0]),
                                (Epic) backedTasksManager.fromString(taskContent[i]));
                        break;
                    case SUBTASK:
                        backedTasksManager.subTasks.put(Integer.parseInt(valueLine[0]),
                                (Subtask) backedTasksManager.fromString(taskContent[i]));
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

    private static final String HEADER = "id,type,name,status,description,epic\n";

    public String serialize() {

        StringBuilder content = new StringBuilder(HEADER);

        for (Task task : tasks.values()) {
            content.append(task.toString());
        }

        for (Epic epic : epics.values()) {
            content.append(epic.toString());
        }

        for (Subtask subtask : subTasks.values()) {
            content.append(subtask.toString());
        }
        return content.toString();
    }

    public static void main(String[] args) throws ManagerSaveException {

        File file = new File("./src/resources/Resources.csv");
        FileBackedTasksManager backedTask = FileBackedTasksManager.loadFromFile(file);

        for (int i = 1; i <= 2; i++) {
            Task task = new Task();
            task.setNameTask("Задача " + i + ".");
            task.setDescriptionTask("Описание задачи " + i + ".");
            task.setStatusTask(StatusTask.NEW);
            backedTask.createTask(task);
        }

        for (int i = 1; i <= 2; i++) {
            Epic epic = new Epic();
            epic.setNameTask("Эпик " + i + ".");
            epic.setDescriptionTask("Описание эпика " + i + ".");
            backedTask.createEpic(epic);
        }

        for (int i = 3; i <= 4; i++) {
            Subtask subtask = new Subtask();
            subtask.setNameTask("Подзадача " + i);
            subtask.setDescriptionTask("Описание подзадачи " + i);
            subtask.setStatusTask(StatusTask.DONE);
            subtask.setIdEpic(i);
            backedTask.createSubTask(subtask);
        }

        System.out.println("Кол-во созданных задач: " + backedTask.getAllTask().size());
        System.out.println("Кол-во созданных эпиков: " + backedTask.getAllEpic().size());
        System.out.println("Кол-во созданных подзадач: " + backedTask.getAllSubTask().size());

        Task task = backedTask.getTask(1);
        Task task3 = backedTask.getTask(2);
        Epic epic = backedTask.getEpicId(3);
        Subtask subtask = backedTask.getSubTaskId(5);
        Subtask subtask1 = backedTask.getSubTaskId(6);

    }
}