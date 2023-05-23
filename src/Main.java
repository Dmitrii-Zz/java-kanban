import logic.Managers;
import logic.TaskManager;
import task.StatusTask;
import task.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager taskManager = Managers.getDefault();

        for (int i = 1; i <= 15; i++) {
            Task task = new Task();
            task.setNameTask("Задача  " + i + ".");
            task.setDescriptionTask("Описание задачи " + i + ".");
            task.setStatusTask(StatusTask.NEW);
            taskManager.createTask(task);
        }

        for (int i = 1; i <= 10; i++) {
            Epic epic = new Epic();
            epic.setNameTask("Эпик " + i + ".");
            epic.setDescriptionTask("Описание эпика " + i + ".");
            taskManager.createEpic(epic);
        }

        for (int i = 1; i <= 15; i++) {
            Subtask subtask = new Subtask();
            subtask.setNameTask("Подзадача " + i);
            subtask.setDescriptionTask("Описание подзадачи " + i);
            subtask.setStatusTask(StatusTask.NEW);
            subtask.setIdEpic(15 + i);
            taskManager.createSubTask(subtask);
        }

        System.out.println("Кол-во созданных задач: " + taskManager.getAllTask().size());
        System.out.println("Кол-во созданных эпиков: " + taskManager.getAllEpic().size());
        System.out.println("Кол-во созданных подзадач: " + taskManager.getAllSubTask().size());

        for (int i = 13; i > 8; i--) {
            Task task = taskManager.getTask(i);
        }

        System.out.println("\nВызвали задачи.");
        System.out.println("Кол-во подзадач в истории: " + taskManager.getHistory().size());
        System.out.println("Состав истории задач:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }

        Subtask subtask = taskManager.getSubTaskId(29);

        for (int i = 20; i < 23; i++) {
            Epic epic = taskManager.getEpicId(i);
        }

        System.out.println("\nВызвали задачу и эпики.");
        System.out.println("Кол-во подзадач в истории: " + taskManager.getHistory().size());
        System.out.println("Состав истории задач:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }

        for (int i = 35; i > 33; i--) {
            Subtask subtask1 = taskManager.getSubTaskId(i);
        }

        Task task2 = taskManager.getTask(2);

        System.out.println("\nВызвали задачу и подзадачи.");
        System.out.println("Кол-во подзадач в истории: " + taskManager.getHistory().size());
        System.out.println("Состав истории задач:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
    }
}