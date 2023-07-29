package logic;

import task.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private static final String HEADER = "id," +
            "type,name,status,description,taskDuration,startTime,endTime,idEpic/idSubTask\n";
    private static final int TYPE_TASK = 1;
    private static final int HISTORY_ID = 0;
    private static final int ID_TASK = 0;
    private static final int IS_HISTORY = 1;
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

    protected void save() {

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
            String[] taskContent = contentSplit[HISTORY_ID].split("\n");

            for (int i = 1; i < taskContent.length; i++) {
                String[] valueLine = taskContent[i].split(",");
                Type typeTask = Type.valueOf(valueLine[TYPE_TASK]);

                switch (typeTask) {
                    case TASK:
                        backedTasksManager.tasks.put(Integer.parseInt(valueLine[ID_TASK]),
                                CSVTaskFormatter.fromString(taskContent[i]));
                        backedTasksManager.taskSortPriority.add(CSVTaskFormatter.fromString(taskContent[i]));
                        break;
                    case EPIC:
                        backedTasksManager.epics.put(Integer.parseInt(valueLine[ID_TASK]),
                                (Epic) CSVTaskFormatter.fromString(taskContent[i]));
                        break;
                    case SUBTASK:
                        backedTasksManager.subTasks.put(Integer.parseInt(valueLine[ID_TASK]),
                                (Subtask) CSVTaskFormatter.fromString(taskContent[i]));
                        backedTasksManager.taskSortPriority.add((Subtask) CSVTaskFormatter.fromString(taskContent[i]));
                        break;
                }

                if (Integer.parseInt(valueLine[ID_TASK]) > idMax) {
                    idMax = Integer.parseInt(valueLine[ID_TASK]);
                }
            }

            backedTasksManager.setTaskId(idMax);

            List<Integer> idTaskHistory = new ArrayList<>();

            if (contentSplit.length > IS_HISTORY) {
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

        Task task = new Task();
        task.setNameTask("Задача 1.");
        task.setDescriptionTask("Описание задачи 1.");
        task.setStatusTask(StatusTask.NEW);
        task.setStartTime(LocalDateTime.of(2025, 1, 1, 12, 0));
        task.setTaskDuration(60);
        backedTask.createTask(task);

        Task task1 = new Task();
        task1.setNameTask("Задача 2.");
        task1.setDescriptionTask("Описание задачи 2.");
        task1.setStatusTask(StatusTask.NEW);
        task1.setStartTime(LocalDateTime.of(2025, 1, 2, 12, 15));
        task1.setTaskDuration(30);
        backedTask.createTask(task1);

        Epic epic = new Epic();
        epic.setNameTask("Эпик 1.");
        epic.setDescriptionTask("Описание эпика 1.");
        backedTask.createEpic(epic);

        Subtask subtask = new Subtask();
        subtask.setNameTask("Подзадача 1");
        subtask.setDescriptionTask("Описание подзадачи 1");
        subtask.setStatusTask(StatusTask.DONE);
        subtask.setIdEpic(2);
        subtask.setStartTime(LocalDateTime.of(2025, 1, 3, 12, 45));
        subtask.setTaskDuration(30);
        backedTask.createSubTask(subtask);

        Subtask subtask1 = new Subtask();
        subtask1.setNameTask("Подзадача 2");
        subtask1.setDescriptionTask("Описание подзадачи 2");
        subtask1.setStatusTask(StatusTask.NEW);
        subtask1.setIdEpic(2);
        subtask1.setStartTime(LocalDateTime.of(2025, 1, 4, 12, 40));
        subtask1.setTaskDuration(45);
        backedTask.createSubTask(subtask1);

        System.out.println("Кол-во созданных задач: " + backedTask.getAllTask().size());
        System.out.println("Кол-во созданных эпиков: " + backedTask.getAllEpic().size());
        System.out.println("Кол-во созданных подзадач: " + backedTask.getAllSubTask().size());

        Task task3 = backedTask.getTask(1);
        Epic epic2 = backedTask.getEpicId(3);
        Subtask subtask2 = backedTask.getSubTaskId(4);

        for (Task t : backedTask.taskSortPriority) {
            System.out.println(t);
        }
    }
}