package logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import task.*;
import task.StatusTask;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {
    TaskManager taskManager = new InMemoryTaskManager();
     private static final Task task = new Task();
     private static final Epic epic = new Epic();
     private static final Subtask subtask = new Subtask();

     @BeforeEach
     public void tasks() {
          task.setNameTask("Задача.");
          task.setDescriptionTask("Описание задачи.");
          task.setStatusTask(StatusTask.NEW);

          epic.setNameTask("Название эпика.");
          epic.setDescriptionTask("Описание эпика.");

          subtask.setNameTask("Название подзадачи.");
          subtask.setDescriptionTask("Описание подзадачи.");
          subtask.setIdEpic(1);
          subtask.setStatusTask(StatusTask.NEW);
          subtask.setStartTime(LocalDateTime.of(2025, 1, 1, 12, 30));
     }

     @Test
     public void createTaskTest() {
          taskManager.createTask(task);

          Task saveTask = taskManager.getTask(1);
          assertNotNull(saveTask, "Задача не найдена.");
          assertEquals(task, saveTask, "Задачи не совпадают.");

          List<Task> tasks = taskManager.getAllTask();
          assertNotNull(tasks, "Задачи не возвращаются.");
          assertEquals(1, tasks.size(), "Неверное кол-во задач.");
          assertEquals(task, tasks.get(0), "Задачи не совпадают.");
     }

     @Test
     public void updateTaskTest() {
          taskManager.createTask(task);

          task.setNameTask("Новое имя задачи.");
          task.setDescriptionTask("Новое описание задачи.");
          task.setStatusTask(StatusTask.IN_PROGRESS);
          taskManager.updateTask(task);

          Task saveTask = taskManager.getTask(1);
          assertEquals(task, saveTask, "Задачи не совпадают.");
     }

     @Test
     public void deleteTaskTest() {
          taskManager.createTask(task);
          taskManager.deleteTask();
          List<Task> tasks = taskManager.getAllTask();
          assertEquals(0, tasks.size(), "Неверное кол-во задач.");
     }

     @Test
     public void deleteTaskIdTest() {
          taskManager.createTask(task);

          taskManager.deleteTaskId(1);
          Task saveTask = taskManager.getTask(1);
          assertNull(saveTask, "Задача вернулась");
     }

     @Test
     public void createEpicTest() {
          taskManager.createEpic(epic);

          Task saveEpic = taskManager.getEpicId(1);
          assertNotNull(saveEpic, "Эпик не найден.");
          assertEquals(epic, saveEpic, "Эпики не совпадают.");

          List<Epic> epics = taskManager.getAllEpic();
          assertNotNull(epics, "Эпики не возвращаются.");
          assertEquals(1, epics.size(), "Неверное кол-во эпиков.");
          assertEquals(epic, epics.get(0), "Эпики не совпадают.");
          assertEquals(StatusTask.NEW, epic.getStatusTask(), "Неверный статус");
     }

     @Test
     public void updateEpicTest() {
          taskManager.createEpic(epic);

          epic.setNameTask("Обновленное название эпика");
          epic.setDescriptionTask("Обновленное название эпика");
          taskManager.updateEpic(epic);

          Epic saveEpic = taskManager.getEpicId(1);
          assertEquals(epic, saveEpic, "эпики не равны.");
     }

     @Test
     public void deleteEpicTest() {
          taskManager.createEpic(epic);

          List<Epic> epics = taskManager.getAllEpic();
          assertEquals(1, epics.size(), "Кол-во эпиков неверно");

          taskManager.deleteEpic();
          epics = taskManager.getAllEpic();
          assertEquals(0, epics.size());
     }

     @Test
     public void deleteEpicIdTest() {
          taskManager.createEpic(epic);

          Epic epic1 = taskManager.getEpicId(1);
          assertEquals(epic, epic1, "Эпики не равны");

          taskManager.deleteEpicId(0);
          epic1 = taskManager.getEpicId(0);
          assertNull(epic1, "Эпик вернулся.");
     }

     @Test
     public void createSubTaskTest() {
          taskManager.createEpic(epic);
          taskManager.createSubTask(subtask);

          Subtask saveSubtask = taskManager.getSubTaskId(2);
          assertEquals(subtask, saveSubtask, "Подзадачи не равны.");

          List<Subtask> subtasks = taskManager.getAllSubTask();
          assertNotNull(subtasks, "Подзадачи не вернулись.");
          assertEquals(1, subtasks.size(), "Список вернулся неверный.");

          int idEpic = saveSubtask.getIdEpic();
          assertEquals(1, idEpic, "Неверный ИД.");
     }

     @Test
     public void updateSubTask() {
          taskManager.createEpic(epic);
          taskManager.createSubTask(subtask);

          subtask.setNameTask("Новое название подзадачи.");
          subtask.setDescriptionTask("Новое описание подзадачи");
          subtask.setStatusTask(StatusTask.IN_PROGRESS);
          taskManager.updateSubTask(subtask);

          Subtask saveSubTask = taskManager.getSubTaskId(2);
          assertEquals(subtask, saveSubTask, "Подзадачи не равны.");
          assertEquals(StatusTask.IN_PROGRESS, epic.getStatusTask(), "Статусы не равны");
     }

     @Test
     public void getAllSubTaskEpicIdTest() {
          taskManager.createEpic(epic);
          taskManager.createSubTask(subtask);

          List<Integer> idSubTask = epic.getIdSubTasks();
          assertEquals(1, idSubTask.size(), "Список вернулся неверный.");
     }

     @Test
     public void deleteSubTaskTest() {
          taskManager.createEpic(epic);
          taskManager.createSubTask(subtask);

          Subtask saveSubTask = taskManager.getSubTaskId(2);
          assertEquals(subtask, saveSubTask, "Подзадачи не равны");

          taskManager.deleteSubTask();
          saveSubTask = taskManager.getSubTaskId(2);
          assertNull(saveSubTask, "Задача не удалена.");
     }

     @Test
     public void deleteSubTaskIdTest() {
          taskManager.createEpic(epic);
          taskManager.createSubTask(subtask);

          Subtask saveSubTask = taskManager.getSubTaskId(2);
          assertEquals(subtask, saveSubTask, "Подзадачи не равны");

          taskManager.deleteSubTaskId(2);
          saveSubTask = taskManager.getSubTaskId(2);
          assertNull(saveSubTask, "Задача не удалена.");
     }
}