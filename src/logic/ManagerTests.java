package logic;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTests {
    Manager manager = new Manager();
    @org.junit.jupiter.api.Test
    void createTask() {
        manager.createTask("Новая задача.", "Выполнить новую задачу.", "NEW");
        manager.createTask("Новая задача - 2.", "Выполнить новую задачу - 2.", "NEW");
        Task task = manager.tasks.get(1);
        assertEquals(2, manager.tasks.size());
        assertEquals(1, task.getIdTask());
        assertEquals("Новая задача.", task.getNameTask());
        assertEquals("Выполнить новую задачу.", task.getDescriptionTask());
        assertEquals("NEW", task.getStatusTask());
    }

    @org.junit.jupiter.api.Test
    void deleteTaskTest() {
        manager.createTask("Новая задача.", "Выполнить новую задачу.", "NEW");
        manager.createTask("Новая задача - 2.", "Выполнить новую задачу - 2.", "NEW");
        manager.deleteTask();
        assertEquals(0, manager.tasks.size());
    }

    @org.junit.jupiter.api.Test
    void getTask() {
        manager.createTask("Новая задача.", "Выполнить новую задачу.", "NEW");
        manager.createTask("Новая задача - 2.", "Выполнить новую задачу - 2.", "NEW");
        assertEquals(manager.tasks.get(2),manager.getTask(2));
    }

    @org.junit.jupiter.api.Test
    void createEpic() {
        manager.createEpic("Новый эпик.", "Выполнить новый эпик.");
        manager.createEpic("Новая эпик - 2.", "Выполнить новый эпик - 2.");
    }

    @org.junit.jupiter.api.Test
    void deleteTaskId() {
        manager.createTask("Новая задача.", "Выполнить новую задачу.", "NEW");
        manager.createTask("Новая задача - 2.", "Выполнить новую задачу - 2.", "NEW");
        assertEquals(2, manager.tasks.size());
        manager.deleteTaskId(2);
        assertEquals(1, manager.tasks.size());
    }

    @org.junit.jupiter.api.Test
    void totalTest() {
        manager.createTask("Новая задача.", "Выполнить новую задачу.", "NEW");
        manager.createTask("Новая задача - 2.", "Выполнить новую задачу - 2.", "NEW");
        Task task = manager.tasks.get(1);
        assertEquals(2, manager.tasks.size());
        assertEquals(1, task.getIdTask());
        assertEquals("Новая задача.", task.getNameTask());
        assertEquals("Выполнить новую задачу.", task.getDescriptionTask());
        assertEquals("NEW", task.getStatusTask());
        manager.createEpic("Новый эпик.", "Выполнить новый эпик.");
        manager.createEpic("Новый эпик - 2.", "Выполнить новый эпик - 2.");
        assertEquals(2, manager.epics.size());
        Epic epic = manager.epics.get(4);
        assertEquals("Новый эпик - 2.", epic.getNameTask());
    }
    @org.junit.jupiter.api.Test
    void updateTaskTest() {
        manager.createTask("Новая задача.", "Выполнить новую задачу.", "NEW");
        manager.createTask("Новая задача - 2.", "Выполнить новую задачу - 2.", "NEW");
        Task task = manager.tasks.get(1);
        assertEquals(2, manager.tasks.size());
        assertEquals(1, task.getIdTask());
        assertEquals("Новая задача.", task.getNameTask());
        assertEquals("Выполнить новую задачу.", task.getDescriptionTask());
        assertEquals("NEW", task.getStatusTask());
        manager.updateTask("Уже не новая задача.",
                "Выполнить уже не новую задачу.", "IN_PR", 1);
        assertEquals(2, manager.tasks.size());
        assertEquals(1, task.getIdTask());
        assertEquals("Уже не новая задача.", task.getNameTask());
        assertEquals("Выполнить уже не новую задачу.", task.getDescriptionTask());
        assertEquals("IN_PR", task.getStatusTask());
    }
    @org.junit.jupiter.api.Test
    void createSubTaskTest() {
        manager.createEpic("Новый эпик.", "Выполнить новый эпик.");
        manager.createEpic("Новая эпик - 2.", "Выполнить новый эпик - 2.");
        manager.createSubTask("Новая подзадача.", "Выполнить новую подзадачу",
                "NEW", 2);
        manager.createSubTask("Новая подзадача - 2.", "Выполнить новую подзадачу - 2",
                "NEW", 2);
        manager.createSubTask("Новая подзадача - 3.", "Выполнить новую подзадачу - 3",
                "NEW", 2);
        Epic epic = manager.epics.get(2);
        ArrayList<Integer> subTasks = epic.getIdSubTasks();
        assertEquals(3, subTasks.size());
    }
    @org.junit.jupiter.api.Test
    void createEpicTest() {
        manager.createEpic("Новый эпик.", "Выполнить новый эпик.");
        manager.createEpic("Новая эпик - 2.", "Выполнить новый эпик - 2.");
        Epic epic = manager.epics.get(2);
        assertEquals("NEW", epic.getStatusTask());
        manager.createSubTask("Новая подзадача.", "Выполнить новую подзадачу",
                "DONE", 2);
        manager.createSubTask("Новая подзадача - 2.", "Выполнить новую подзадачу - 2",
                "DONE", 2);
        manager.createSubTask("Новая подзадача - 3.", "Выполнить новую подзадачу - 3",
                "DONE", 2);
        assertEquals("DONE", epic.getStatusTask());
    }

    @org.junit.jupiter.api.Test
    void updateEpicTest() {
        manager.createEpic("Новый эпик.", "Выполнить новый эпик.");
        manager.createEpic("Новая эпик - 2.", "Выполнить новый эпик - 2.");
        Epic epic = manager.epics.get(2);
        assertEquals("NEW", epic.getStatusTask());
        assertEquals("Новая эпик - 2.", epic.getNameTask());
        manager.createSubTask("Новая подзадача.", "Выполнить новую подзадачу",
                "NEW", 2);
        manager.createSubTask("Новая подзадача.", "Выполнить новую подзадачу",
                "IN_PROGRESS", 2);
        manager.updateEpic("Новая эпик - 2.0.", "Выполнить новый эпик - 2.", 2);
        assertEquals("Новая эпик - 2.0.", epic.getNameTask());
        assertEquals("IN_PROGRESS", epic.getStatusTask());
    }
    @org.junit.jupiter.api.Test
    void deleteEpicTest() {
        manager.createEpic("Новый эпик.", "Выполнить новый эпик.");
        manager.createEpic("Новая эпик - 2.", "Выполнить новый эпик - 2.");
        assertEquals(2, manager.epics.size());
        manager.deleteEpic();
        assertEquals(0, manager.epics.size());
        manager.createEpic("Новый эпик.", "Выполнить новый эпик.");
        manager.createEpic("Новая эпик - 2.", "Выполнить новый эпик - 2.");
        assertEquals(2, manager.epics.size());
        manager.deleteEpicId(3);
        assertEquals(1, manager.epics.size());
    }

    @org.junit.jupiter.api.Test
    void getEpicIdTest() {
        manager.createEpic("Новый эпик.", "Выполнить новый эпик.");
        manager.createEpic("Новая эпик - 2.", "Выполнить новый эпик - 2.");
        assertEquals(manager.epics.get(2), manager.getEpicId(2));
    }

    @org.junit.jupiter.api.Test
    void updateSubTask() {
        manager.createEpic("Новый эпик.", "Выполнить новый эпик.");
        manager.createEpic("Новая эпик - 2.0.", "Выполнить новый эпик - 2.");
        Epic epic = manager.epics.get(2);
        manager.createSubTask("Новая подзадача.", "Выполнить новую подзадачу",
                "DONE", 2);
        manager.createSubTask("Новая подзадача - 2.", "Выполнить новую подзадачу - 2",
                "NEW", 2);
        Subtask subTask = manager.subTasks.get(4);
        assertEquals("Новая эпик - 2.0.", epic.getNameTask());
        assertEquals("IN_PROGRESS", epic.getStatusTask());
        assertEquals("Новая подзадача - 2.", subTask.getNameTask());
        manager.updateSubTask("Новая подзадача - 2.1", "Выполнить новую подзадачу - 2.1",
                "DONE", 4);
        assertEquals("Новая эпик - 2.0.", epic.getNameTask());
        assertEquals("DONE", epic.getStatusTask());
        assertEquals("Новая подзадача - 2.1", subTask.getNameTask());
    }

    @org.junit.jupiter.api.Test
    void deleteSubTaskTest() {
        manager.createEpic("Новый эпик.", "Выполнить новый эпик.");
        manager.createEpic("Новая эпик - 2.0.", "Выполнить новый эпик - 2.");
        Epic epic = manager.epics.get(2);
        manager.createSubTask("Новая подзадача.", "Выполнить новую подзадачу",
                "DONE", 2);
        manager.createSubTask("Новая подзадача - 2.", "Выполнить новую подзадачу - 2",
                "NEW", 2);
        assertEquals(2, manager.subTasks.size());
        manager.deleteSubTask();
        assertEquals(0, manager.subTasks.size());
    }

    @org.junit.jupiter.api.Test
    void deleteSubTaskIdTest() {
        manager.createEpic("Новый эпик.", "Выполнить новый эпик.");
        manager.createEpic("Новая эпик - 2.0.", "Выполнить новый эпик - 2.");
        Epic epic = manager.epics.get(2);
        manager.createSubTask("Новая подзадача.", "Выполнить новую подзадачу",
                "DONE", 2);
        manager.createSubTask("Новая подзадача - 2.", "Выполнить новую подзадачу - 2",
                "NEW", 2);
        assertEquals(2, manager.subTasks.size());
        manager.deleteSubTaskId(3);
        assertEquals(1, manager.subTasks.size());
    }
}