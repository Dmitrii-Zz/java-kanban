package logic;

import org.junit.jupiter.api.Test;
import task.*;
import task.StatusTask;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {
     private final TaskManager taskManager = Managers.getDefault();

     @Test
     public void createTaskTest() {
          Task task = new Task();
          task.setNameTask("Новая задача.");
          task.setDescriptionTask("Описание новой задачи.");
          task.setStatusTask(StatusTask.NEW);
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
          Task task = new Task();
          task.setNameTask("Новая задача.");
          task.setDescriptionTask("Описание новой задачи.");
          task.setStatusTask(StatusTask.NEW);
          taskManager.createTask(task);

          task.setNameTask("Задача.");
          task.setDescriptionTask("Описание задачи.");
          task.setStatusTask(StatusTask.IN_PROGRESS);
          taskManager.updateTask(task);

          Task saveTask = taskManager.getTask(1);

          assertEquals(task, saveTask, "Задачи не совпадают.");
     }

     @Test
     public void deleteTaskTest() {
          Task task = new Task();
          task.setNameTask("Новая задача 1.");
          task.setDescriptionTask("Описание новой задачи 1.");
          task.setStatusTask(StatusTask.NEW);
          taskManager.createTask(task);

          Task task1 = new Task();
          task1.setNameTask("Новая задача 2.");
          task1.setDescriptionTask("Описание новой задачи 2.");
          task1.setStatusTask(StatusTask.NEW);
          taskManager.createTask(task1);

          taskManager.deleteTask();
          List<Task> tasks = taskManager.getAllTask();
          assertEquals(0, tasks.size(), "Неверное кол-во задач.");
     }

     @Test
     public void deleteTaskIdTest() {
          Task task = new Task();
          task.setNameTask("Новая задача 1.");
          task.setDescriptionTask("Описание новой задачи 1.");
          task.setStatusTask(StatusTask.NEW);
          taskManager.createTask(task);

          Task task1 = new Task();
          task1.setNameTask("Новая задача 2.");
          task1.setDescriptionTask("Описание новой задачи 2.");
          task1.setStatusTask(StatusTask.NEW);
          taskManager.createTask(task1);

          taskManager.deleteTaskId(1);
          Task saveTask = taskManager.getTask(1);
          assertNull(saveTask, "Задача вернулась");
     }

     @Test
     public void createEpicTest() {
          Epic epic = new Epic();
          epic.setNameTask("Название эпика.");
          epic.setDescriptionTask("Описание эпика.");
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
          Epic epic = new Epic();
          epic.setNameTask("Название эпика.");
          epic.setDescriptionTask("Описание эпика.");
          taskManager.createEpic(epic);

          epic.setNameTask("Обновленное название эпика");
          epic.setDescriptionTask("Обновленное название эпика");
          taskManager.updateEpic(epic);

          Epic saveEpic = taskManager.getEpicId(1);
          assertEquals(epic, saveEpic, "эпики не равны.");
     }

     @Test
     public void deleteEpicTest() {
          Epic epic = new Epic();
          epic.setNameTask("Название эпика.");
          epic.setDescriptionTask("Описание эпика.");
          taskManager.createEpic(epic);

          List<Epic> epics = taskManager.getAllEpic();
          assertEquals(1, epics.size());

          taskManager.deleteEpic();

          epics = taskManager.getAllEpic();
          assertEquals(0, epics.size());
     }

     @Test
     public void deleteEpicIdTest() {
          Epic epic = new Epic();
          epic.setNameTask("Название эпика 1.");
          epic.setDescriptionTask("Описание эпика 1.");
          taskManager.createEpic(epic);

          Epic epic1 = taskManager.getEpicId(1);
          assertEquals(epic, epic1, "Эпики не равны");

          taskManager.deleteEpicId(0);
          epic1 = taskManager.getEpicId(0);
          assertNull(epic1, "Эпик вернулся.");
     }

     @Test
     public void createSubTaskTest() {
          Epic epic = new Epic();
          epic.setNameTask("Название эпика 1.");
          epic.setDescriptionTask("Описание эпика 1.");
          taskManager.createEpic(epic);

          Subtask subtask = new Subtask();

          subtask.setNameTask("Название подзадачи.");
          subtask.setDescriptionTask("Описание подзадачи.");
          subtask.setIdEpic(1);
          subtask.setStatusTask(StatusTask.NEW);
          subtask.setStartTime(LocalDateTime.of(2025, 1, 1, 12, 30));
          taskManager.createSubTask(subtask);

          Subtask saveSubtask = taskManager.getSubTaskId(2);

          assertEquals(subtask, saveSubtask, "Подзадачи не равны.");
          assertEquals(StatusTask.NEW, epic.getStatusTask(), "статусы не равны.");

          List<Subtask> subtasks = taskManager.getAllSubTask();

          assertNotNull(subtasks, "Подзадачи не вернулись.");
          assertEquals(1, subtasks.size(), "Список вернулся неверный.");

          int idEpic = saveSubtask.getIdEpic();
          assertEquals(1, idEpic, "Неверный ИД.");

          List<Integer> subTaskId = epic.getIdSubTasks();
          assertEquals(1, subTaskId.size(), "Списки не равны");
     }

     @Test
     public void updateSubTask() {
          Epic epic = new Epic();
          epic.setNameTask("Название эпика 1.");
          epic.setDescriptionTask("Описание эпика 1.");
          taskManager.createEpic(epic);

          Subtask subtask = new Subtask();

          subtask.setNameTask("Название подзадачи.");
          subtask.setDescriptionTask("Описание подзадачи.");
          subtask.setIdEpic(1);
          subtask.setStatusTask(StatusTask.NEW);
          subtask.setStartTime(LocalDateTime.of(2025, 1, 1, 12, 30));
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
          Epic epic = new Epic();
          epic.setNameTask("Название эпика 1.");
          epic.setDescriptionTask("Описание эпика 1.");
          taskManager.createEpic(epic);

          Subtask subtask = new Subtask();

          subtask.setNameTask("Название подзадачи.");
          subtask.setDescriptionTask("Описание подзадачи.");
          subtask.setIdEpic(1);
          subtask.setStatusTask(StatusTask.NEW);
          subtask.setStartTime(LocalDateTime.of(2025, 1, 1, 12, 30));
          taskManager.createSubTask(subtask);

          Subtask subtask1 = new Subtask();

          subtask1.setNameTask("Название подзадачи.");
          subtask1.setDescriptionTask("Описание подзадачи.");
          subtask1.setIdEpic(1);
          subtask1.setStatusTask(StatusTask.NEW);
          subtask1.setStartTime(LocalDateTime.of(2025, 1, 1, 12, 30));
          taskManager.createSubTask(subtask1);

          List<Integer> idSubTask = epic.getIdSubTasks();
          assertEquals(2, idSubTask.size(), "Список вернулся неверный.");
     }

     @Test
     public void deleteSubTaskTest() {
          Epic epic = new Epic();
          epic.setNameTask("Название эпика 1.");
          epic.setDescriptionTask("Описание эпика 1.");
          taskManager.createEpic(epic);

          Subtask subtask = new Subtask();

          subtask.setNameTask("Название подзадачи.");
          subtask.setDescriptionTask("Описание подзадачи.");
          subtask.setIdEpic(1);
          subtask.setStatusTask(StatusTask.NEW);
          subtask.setStartTime(LocalDateTime.of(2025, 1, 1, 12, 30));
          taskManager.createSubTask(subtask);

          Subtask saveSubTask = taskManager.getSubTaskId(2);
          assertEquals(subtask, saveSubTask, "Подзадачи не равны");

          taskManager.deleteSubTask();
          saveSubTask = taskManager.getSubTaskId(2);
          assertNull(saveSubTask, "Задача не удалена.");
     }

     @Test
     public void deleteSubTaskIdTest() {
          Epic epic = new Epic();
          epic.setNameTask("Название эпика 1.");
          epic.setDescriptionTask("Описание эпика 1.");
          taskManager.createEpic(epic);

          Subtask subtask = new Subtask();

          subtask.setNameTask("Название подзадачи.");
          subtask.setDescriptionTask("Описание подзадачи.");
          subtask.setIdEpic(1);
          subtask.setStatusTask(StatusTask.NEW);
          subtask.setTaskDuration(30);
          subtask.setStartTime(LocalDateTime.of(2025, 1, 1, 12, 30));
          taskManager.createSubTask(subtask);

          Subtask saveSubTask = taskManager.getSubTaskId(2);
          assertEquals(subtask, saveSubTask, "Подзадачи не равны");

          taskManager.deleteSubTaskId(2);
          saveSubTask = taskManager.getSubTaskId(2);
          assertNull(saveSubTask, "Задача не удалена.");
     }
}