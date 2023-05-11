import logic.Manager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");
        Manager manager = new Manager();
        manager.createTask("Задача 1.", "Выполнить задачу 1.", "NEW");
        manager.createTask("Задача 2.", "Выполнить задачу 2.", "NEW");
        manager.createEpic("Эпик 1.", "Эпик 1 выполняется.");
        manager.createEpic("Эпик 2.", "Выполнить эпик 2.");
        manager.createSubTask("Подзадача 1.1.", "Выполняется подзадача 1.1.",
                "IN_PROGRESS", 3);
        manager.createSubTask("Подзадача 1.2.", "Выполнить подзадачу 1.2.",
                "NEW", 3);
        manager.createSubTask("Подзадача 2.1.", "Выполнить подзадачу 2.1.",
                "NEW", 4);
        System.out.println(manager.getAllTask());
        System.out.println(manager.getAllEpic());
        System.out.println(manager.getAllSubTask());
        manager.updateTask("Задача 1.", "Задача 1 выполнена.",
                "DONE", 1);
        manager.updateTask("Задача 2.", "Задача 2 выполняется.",
                "IN_PROGRESS", 2);
        manager.updateEpic("Эпик 1.",
                "Эпик 1 выполнен, так как все его подзадачи выполнены.", 3);
        manager.updateEpic("Эпик 2.",
                "Эпик 2 выполняется, так как его подзадача выполняется.", 4);
        manager.updateSubTask("Подзадача 1.1.", "Подзадача 1.1 выполнена.",
                "DONE", 5);
        manager.updateSubTask("Подзадача 1.2.", "Подзадача 1.2 выполнена.",
                "DONE", 6);
        manager.updateSubTask("Подзадача 2.1.", "Подзадача 2.1 выполняется.",
                "IN_PROGRESS", 7);
        System.out.println(manager.getAllTask());
        System.out.println(manager.getAllEpic());
        System.out.println(manager.getAllSubTask());
        manager.deleteTaskId(2);
        manager.deleteEpicId(3);
        manager.deleteSubTaskId(7);
        System.out.println(manager.getAllTask());
        System.out.println(manager.getAllEpic());
        System.out.println(manager.getAllSubTask());
    }
}