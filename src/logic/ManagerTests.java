package logic;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTests {
    Manager manager = new Manager();
//    @org.junit.jupiter.api.Test
//    void createTaskTest() {
//        Task task = new Task();
//        task.setNameTask("Задача 1.");
//        task.setDescriptionTask("Описание задачи 1.");
//        task.setStatusTask("NEW");
//        manager.createTask(task);
//        Task task1 = manager.tasks.get(1);
//        assertEquals("NEW", task1.getStatusTask());
//        assertEquals("Задача 1.", task1.getNameTask());
//        manager.deleteTaskId(2);
//        assertEquals(1,manager.tasks.size());
//    }
//
//    @org.junit.jupiter.api.Test
//    void createSubTaskTest() {
//        Epic epic = new Epic();
//        epic.setNameTask("Эпик 1.");
//        epic.setDescriptionTask("Выполнить эпик 1.");
//        manager.createEpic(epic);
//        Subtask subtask = new Subtask();
//        subtask.setNameTask("Подзадача 1.");
//        subtask.setDescriptionTask("Выполнить подзадачу 1.");
//        subtask.setStatusTask("DONE");
//        subtask.setIdEpic(1);
//        manager.createSubTask(subtask);
//        Subtask subtask2 = new Subtask();
//        subtask2.setNameTask("Подзадача 2.");
//        subtask2.setDescriptionTask("Выполнить подзадачу 2.");
//        subtask2.setStatusTask("NEW");
//        subtask2.setIdEpic(1);
//        manager.createSubTask(subtask2);
//        Epic epic1 = manager.epics.get(1);
//        Subtask subtask1 = manager.subTasks.get(2);
//        assertEquals("Эпик 1.", epic1.getNameTask());
//        assertEquals("Подзадача 1.", subtask1.getNameTask());
//        assertEquals("Выполнить эпик 1.", epic1.getDescriptionTask());
//        assertEquals("Выполнить подзадачу 1.", subtask1.getDescriptionTask());
//        assertEquals("IN_PROGRESS", epic1.getStatusTask());
//        assertEquals("DONE", subtask1.getStatusTask());
//        assertEquals(1, subtask1.getIdEpic());
//        assertEquals(2, epic1.getIdSubTasks().size());
//    }
//    @org.junit.jupiter.api.Test
//    void updateTaskTest() {
//        Task task = new Task();
//        task.setNameTask("Задача 1.");
//        task.setDescriptionTask("Описание задачи 1.");
//        task.setStatusTask("NEW");
//        manager.createTask(task);
//        Task task1 = new Task();
//        task1.setNameTask("Задача 2.");
//        task1.setDescriptionTask("Описание задачи 2.");
//        task1.setStatusTask("IN_PROGRESS");
//        manager.createTask(task1);
//        Task task3 = new Task();
//        task3.setNameTask("Задача 3.");
//        task3.setDescriptionTask("Описание задачи 3.");
//        task3.setStatusTask("NEW");
//        manager.createTask(task3);
//        Task task333 = manager.tasks.get(3);
//        assertEquals("NEW", task333.getStatusTask());
//        Task task33 = manager.tasks.get(3);
//        task33.setNameTask("Задача 3.0.");
//        task33.setDescriptionTask("Задача выполнена.");
//        task33.setStatusTask("DONE");
//        manager.updateTask(task33);
//        assertEquals("DONE", task33.getStatusTask());
//    }
//    @org.junit.jupiter.api.Test
//    void deleteSubtask() {
//        Epic epic = new Epic();
//        epic.setNameTask("Эпик 1.");
//        epic.setDescriptionTask("Выполнить эпик 1.");
//        manager.createEpic(epic);
//
//        Subtask subtask = new Subtask();
//        subtask.setNameTask("Подзадача 1.");
//        subtask.setDescriptionTask("Выполнить подзадачу 1.");
//        subtask.setStatusTask("DONE");
//        subtask.setIdEpic(1);
//        manager.createSubTask(subtask);
//        Subtask subtask2 = new Subtask();
//        subtask2.setNameTask("Подзадача 2.");
//        subtask2.setDescriptionTask("Выполнить подзадачу 2.");
//        subtask2.setStatusTask("NEW");
//        subtask2.setIdEpic(1);
//        manager.createSubTask(subtask2);
//
//        Epic epic1 = manager.epics.get(1);
//        Subtask subtask1 = manager.subTasks.get(2);
//        assertEquals("IN_PROGRESS", epic1.getStatusTask());
//        assertEquals("DONE", subtask1.getStatusTask());
//        manager.deleteSubTaskId(3);
//        assertEquals("DONE", epic1.getStatusTask());
//    }
//
//    @org.junit.jupiter.api.Test
//    void deleteSubtaskTest() {
//        Epic epic = new Epic();
//        epic.setNameTask("Эпик 1.");
//        epic.setDescriptionTask("Выполнить эпик 1.");
//        manager.createEpic(epic);
//        Epic epic2 = new Epic();
//        epic2.setNameTask("Эпик 2.");
//        epic2.setDescriptionTask("Выполнить эпик 2.");
//        manager.createEpic(epic2);
//        Epic epic3 = new Epic();
//        epic3.setNameTask("Эпик 3.");
//        epic3.setDescriptionTask("Выполнить эпик 3.");
//        manager.createEpic(epic3);
//
//        Subtask subtask = new Subtask();
//        subtask.setNameTask("Подзадача 1.1.");
//        subtask.setDescriptionTask("Выполнить подзадачу 1.1.");
//        subtask.setStatusTask("NEW");
//        subtask.setIdEpic(1);
//        manager.createSubTask(subtask);
//        Subtask subtask1 = new Subtask();
//        subtask1.setNameTask("Подзадача 2.1.");
//        subtask1.setDescriptionTask("Выполнить подзадачу 2.1.");
//        subtask1.setStatusTask("DONE");
//        subtask1.setIdEpic(2);
//        manager.createSubTask(subtask1);
//        Subtask subtask2 = new Subtask();
//        subtask2.setNameTask("Подзадача 3.1.");
//        subtask2.setDescriptionTask("Выполнить подзадачу 3.1.");
//        subtask2.setStatusTask("IN_PROGRESS");
//        subtask2.setIdEpic(3);
//        manager.createSubTask(subtask2);
//
//        Epic epic11 = manager.epics.get(2);
//        assertEquals(1, epic11.getIdSubTasks().size());
//        assertEquals("DONE", epic11.getStatusTask());
//
//        manager.deleteSubTask();
//        assertEquals(0, epic11.getIdSubTasks().size());
//        assertEquals("NEW", epic11.getStatusTask());
//    }
//
//    @org.junit.jupiter.api.Test
//    void deleteSubtaskIdTest() {
//        Epic epic = new Epic();
//        epic.setNameTask("Эпик 1.");
//        epic.setDescriptionTask("Выполнить эпик 1.");
//        manager.createEpic(epic);
//        Epic epic2 = new Epic();
//        epic2.setNameTask("Эпик 2.");
//        epic2.setDescriptionTask("Выполнить эпик 2.");
//        manager.createEpic(epic2);
//        Epic epic3 = new Epic();
//        epic3.setNameTask("Эпик 3.");
//        epic3.setDescriptionTask("Выполнить эпик 3.");
//        manager.createEpic(epic3);
//
//        Subtask subtask = new Subtask();
//        subtask.setNameTask("Подзадача 1.1.");
//        subtask.setDescriptionTask("Выполнить подзадачу 1.1.");
//        subtask.setStatusTask("NEW");
//        subtask.setIdEpic(1);
//        manager.createSubTask(subtask);
//        Subtask subtask1 = new Subtask();
//        subtask1.setNameTask("Подзадача 2.1.");
//        subtask1.setDescriptionTask("Выполнить подзадачу 2.1.");
//        subtask1.setStatusTask("DONE");
//        subtask1.setIdEpic(2);
//        manager.createSubTask(subtask1);
//        Subtask subtask3 = new Subtask();
//        subtask3.setNameTask("Подзадача 2.2.");
//        subtask3.setDescriptionTask("Выполнить подзадачу 2.2.");
//        subtask3.setStatusTask("NEW");
//        subtask3.setIdEpic(2);
//        manager.createSubTask(subtask3);
//        Subtask subtask2 = new Subtask();
//        subtask2.setNameTask("Подзадача 3.1.");
//        subtask2.setDescriptionTask("Выполнить подзадачу 3.1.");
//        subtask2.setStatusTask("IN_PROGRESS");
//        subtask2.setIdEpic(3);
//        manager.createSubTask(subtask2);
//
//        Epic epic11 = manager.epics.get(2);
//        assertEquals(2, epic11.getIdSubTasks().size());
//        assertEquals("IN_PROGRESS", epic11.getStatusTask());
//
//        manager.deleteSubTaskId(6);
//        manager.deleteSubTaskId(5);
//        assertEquals(0, epic11.getIdSubTasks().size());
//        assertEquals("NEW", epic11.getStatusTask());
//    }
//
//    @org.junit.jupiter.api.Test
//    void deleteEpicIdTest() {
//        Epic epic = new Epic();
//        epic.setNameTask("Эпик 1.");
//        epic.setDescriptionTask("Выполнить эпик 1.");
//        manager.createEpic(epic);
//
//        Subtask subtask = new Subtask();
//        subtask.setNameTask("Подзадача 1.1.");
//        subtask.setDescriptionTask("Выполнить подзадачу 1.1.");
//        subtask.setStatusTask("NEW");
//        subtask.setIdEpic(1);
//        manager.createSubTask(subtask);
//        Subtask subtask1 = new Subtask();
//        subtask1.setNameTask("Подзадача 2.1.");
//        subtask1.setDescriptionTask("Выполнить подзадачу 2.1.");
//        subtask1.setStatusTask("DONE");
//        subtask1.setIdEpic(1);
//        manager.createSubTask(subtask1);
//        Subtask subtask3 = new Subtask();
//        subtask3.setNameTask("Подзадача 2.2.");
//        subtask3.setDescriptionTask("Выполнить подзадачу 2.2.");
//        subtask3.setStatusTask("NEW");
//        subtask3.setIdEpic(1);
//        manager.createSubTask(subtask3);
//        Subtask subtask2 = new Subtask();
//        subtask2.setNameTask("Подзадача 3.1.");
//        subtask2.setDescriptionTask("Выполнить подзадачу 3.1.");
//        subtask2.setStatusTask("IN_PROGRESS");
//        subtask2.setIdEpic(1);
//        manager.createSubTask(subtask2);
//
//        assertEquals(1, manager.epics.size());
//        assertEquals(4, manager.subTasks.size());
//        manager.deleteEpicId(2);
//        assertEquals(1, manager.epics.size());
//        assertEquals(4, manager.subTasks.size());
//    }
//
//    @org.junit.jupiter.api.Test
//    void getTaskTest() {
//        Task task = new Task();
//        task.setNameTask("Задача 1.");
//        task.setDescriptionTask("Описание задачи 1.");
//        task.setStatusTask("NEW");
//        manager.createTask(task);
//        Task task1 = manager.getTask(1);
//        assertEquals("NEW", task1.getStatusTask());
//        assertEquals("Задача 1.", task1.getNameTask());
//    }
//    @org.junit.jupiter.api.Test
//    void updateEpicTest() {
//        Epic epic = new Epic();
//        epic.setNameTask("Эпик 1.");
//        epic.setDescriptionTask("Выполнить эпик 1.");
//        manager.createEpic(epic);
//
//        Subtask subtask = new Subtask();
//        subtask.setNameTask("Подзадача 1.1.");
//        subtask.setDescriptionTask("Выполнить подзадачу 1.1.");
//        subtask.setStatusTask("NEW");
//        subtask.setIdEpic(1);
//        manager.createSubTask(subtask);
//
//        Epic epicTest = manager.epics.get(1);
//
//        assertEquals(1, epicTest.getIdSubTasks().size());
//        assertEquals("Эпик 1.", epicTest.getNameTask());
//        assertEquals("Выполнить эпик 1.", epicTest.getDescriptionTask());
//
//        Epic updateEpic = new Epic();
//        epic.setNameTask("Эпик 1 обновленный.");
//        epic.setDescriptionTask("Выполнить эпик 1 обновленный.");
//        epic.setIdTask(1);
//        ArrayList<Integer> idSubTusk = new ArrayList<>();
//        idSubTusk.add(1);
//        idSubTusk.add(2);
//        idSubTusk.add(3);
//        updateEpic.setIdSubTasks(idSubTusk);
//
//        manager.updateEpic(updateEpic);
//
//        assertEquals(1, epicTest.getIdSubTasks().size());
//        assertEquals("Эпик 1 обновленный.", epicTest.getNameTask());
//        assertEquals("Выполнить эпик 1 обновленный.", epicTest.getDescriptionTask());
//    }
}