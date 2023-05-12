import logic.Manager;
import task.Epic;
import task.Subtask;
import task.Task;

public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");
        Manager manager = new Manager();

        Task task1 = new Task();
        task1.setNameTask("Задача 1.");
        task1.setDescriptionTask("Выполнить задачу 1.");
        task1.setStatusTask("NEW");
        manager.createTask(task1);
        Task task2 = new Task();
        task2.setNameTask("Задача 2.");
        task2.setDescriptionTask("Выполняется задача 2.");
        task2.setStatusTask("IN_PROGRESS");
        manager.createTask(task2);

        Epic epic1 = new Epic();
        epic1.setNameTask("Эпик 1.");
        epic1.setDescriptionTask("Выполнить эпик 1.");
        manager.createEpic(epic1);

        Subtask subtask1 = new Subtask();
        subtask1.setNameTask("Подзадача 1.1.");
        subtask1.setDescriptionTask("Выполнить подзадачу 1.1.");
        subtask1.setStatusTask("NEW");
        subtask1.setIdEpic(3);
        manager.createSubTask(subtask1);
        Subtask subtask2 = new Subtask();
        subtask2.setNameTask("Подзадача 1.2.");
        subtask2.setDescriptionTask("Выполнить подзадачу 1.2.");
        subtask2.setStatusTask("NEW");
        subtask2.setIdEpic(3);
        manager.createSubTask(subtask2);

        Epic epic2 = new Epic();
        epic2.setNameTask("Эпик 2.");
        epic2.setDescriptionTask("Выполняется эпик 2.");
        manager.createEpic(epic2);

        Subtask subtask3 = new Subtask();
        subtask3.setNameTask("Подзадача 2.1.");
        subtask3.setDescriptionTask("Выполняется подзадача 2.1.");
        subtask3.setStatusTask("IN_PROGRESS");
        subtask3.setIdEpic(6);
        manager.createSubTask(subtask3);

        System.out.println("Создаем задачи:");
        System.out.println(manager.getAllTask());
        System.out.println("Создаем эпики:");
        System.out.println(manager.getAllEpic());
        System.out.println("Создаем подзадачи:");
        System.out.println(manager.getAllSubTask());

        task1.setDescriptionTask("Выполняется задача 1.");
        task1.setStatusTask("IN_PROGRESS");
        manager.updateTask(task1);


        task2.setDescriptionTask("Задача 2 выполнена.");
        task2.setStatusTask("DONE");
        manager.updateTask(task2);

        subtask1.setDescriptionTask("Выполняется подзадача 1.1.");
        subtask1.setStatusTask("IN_PROGRESS");
        manager.updateSubTask(subtask1);

        epic1.setDescriptionTask("Выполняется эпик 1.");
        manager.updateEpic(epic1);

        System.out.println("Обновили задачи:");
        System.out.println(manager.getAllTask());
        System.out.println("Обновили эпики:");
        System.out.println(manager.getAllEpic());
        System.out.println("Обновили подзадачи:");
        System.out.println(manager.getAllSubTask());

        manager.deleteTaskId(1);
        manager.deleteEpicId(3);
        manager.deleteSubTaskId(7);

        System.out.println("Удалили задачу:");
        System.out.println(manager.getAllTask());
        System.out.println("Удалили эпики:");
        System.out.println(manager.getAllEpic());
        System.out.println("Удалили подзадачи:");
        System.out.println(manager.getAllSubTask());
    }
}