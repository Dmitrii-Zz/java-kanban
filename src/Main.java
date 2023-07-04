import logic.FileBackedTasksManager;
import logic.ManagerSaveException;
import logic.Managers;
import logic.TaskManager;
import task.StatusTask;
import task.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = Managers.getDefault();

        for (int i = 1; i <= 2; i++) {
            Task task = new Task();
            task.setNameTask("Задача " + i + ".");
            task.setDescriptionTask("Описание задачи " + i + ".");
            task.setStatusTask(StatusTask.NEW);
            taskManager.createTask(task);
        }

        for (int i = 3; i <= 4; i++) {
            Epic epic = new Epic();
            epic.setNameTask("Эпик " + i + ".");
            epic.setDescriptionTask("Описание эпика " + i + ".");
            taskManager.createEpic(epic);
        }

        for (int i = 6; i <= 8; i++) {
            Subtask subtask = new Subtask();
            subtask.setNameTask("Подзадача " + i);
            subtask.setDescriptionTask("Описание подзадачи " + i);
            subtask.setStatusTask(StatusTask.NEW);
            subtask.setIdEpic(3);
            taskManager.createSubTask(subtask);
        }

        System.out.println("Кол-во созданных задач: " + taskManager.getAllTask().size());
        System.out.println("Кол-во созданных эпиков: " + taskManager.getAllEpic().size());
        System.out.println("Кол-во созданных подзадач: " + taskManager.getAllSubTask().size());

        Task task = taskManager.getTask(2);
        Task task1 = taskManager.getTask(1);
        Subtask subtask = taskManager.getSubTaskId(5);

        FileBackedTasksManager fileBackedTasksManager =
                new FileBackedTasksManager("./src/resources/Resources.csv");

    }
}