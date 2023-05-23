package logic;
import task.*;
import java.util.List;

public interface TaskManager {

    void createTask(Task task);
    void updateTask(Task task);
    List<Task> getAllTask();
    void deleteTask();
    void deleteTaskId(int idTask);
    Task getTask(int idTask);
    void createEpic(Epic epic);
    void updateEpic(Epic epic);
    List<Epic> getAllEpic();
    void deleteEpic();
    void deleteEpicId(int idEpic);
    Epic getEpicId(int idEpic);
    void createSubTask(Subtask subtask);
    void updateSubTask(Subtask subTask);
    List<Subtask> getAllSubTask();
    List<Subtask> getAllSubTaskEpicId(int idEpic);
    void deleteSubTask();
    void deleteSubTaskId(int idSubTask);
    Subtask getSubTaskId(int idSubTask);
    List<Task> getHistory();
}
