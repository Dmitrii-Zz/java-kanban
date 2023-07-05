package logic;

import task.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileBackedTasksManager extends InMemoryTaskManager {

    private static final String HEADER = "id,type,name,status,description,epic\n";
    private final String path;

    public FileBackedTasksManager(String path) {
        this.path = path;
    }

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

    private void save() {

        File file = new File(path);

        try (Writer writer = new FileWriter(file)) {
            writer.write(serialize());
            writer.write(CSVTaskFormatter.historyToString(historyManager));
        } catch (Exception e) {
            throw new ManagerSaveException("Ошибка чтения файла!");
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) {

        int idMax = 0;
        FileBackedTasksManager backedTasksManager = new FileBackedTasksManager("./src/resources/Resources.csv");
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
                                CSVTaskFormatter.fromString(taskContent[i]));
                        break;
                    case EPIC:
                        backedTasksManager.epics.put(Integer.parseInt(valueLine[0]),
                                (Epic) CSVTaskFormatter.fromString(taskContent[i]));
                        break;
                    case SUBTASK:
                        backedTasksManager.subTasks.put(Integer.parseInt(valueLine[0]),
                                (Subtask) CSVTaskFormatter.fromString(taskContent[i]));
                        break;
                }

                if (Integer.parseInt(valueLine[0]) > idMax) {
                    idMax = Integer.parseInt(valueLine[0]);
                }
            }

            backedTasksManager.setTaskId(idMax);

            List<Integer> idTaskHistory = new ArrayList<>();

            if (contentSplit.length > 1) {
                idTaskHistory = CSVTaskFormatter.historyFromString(contentSplit[1]);
            }

            for (int id : idTaskHistory) {
                backedTasksManager.getTask(id);
                backedTasksManager.getEpicId(id);
                backedTasksManager.getSubTaskId(id);
            }

        } catch (Exception e) {
            throw new ManagerSaveException("Ошибка чтения файла!");
        }

        return backedTasksManager;
    }

    private String serialize() {

        StringBuilder content = new StringBuilder(HEADER);

        for (Task task : tasks.values()) {
            content.append(CSVTaskFormatter.toString(task));
        }

        for (Epic epic : epics.values()) {
            content.append(CSVTaskFormatter.toString(epic));
        }

        for (Subtask subtask : subTasks.values()) {
            content.append(CSVTaskFormatter.toString(subtask));
        }

        return content.toString();
    }

    public static void main(String[] args) {

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

        System.out.println(task3);
        System.out.println(subtask1);
    }
}