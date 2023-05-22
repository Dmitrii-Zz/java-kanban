package logic;
import task.*;
import java.util.List;

public interface TaskManager {

    public void createTask(Task task);
    public void updateTask(Task task);
    public List<Task> getAllTask();
    public void deleteTask();
    public void deleteTaskId(int idTask);
    public Task getTask(int idTask);
    public void createEpic(Epic epic);
    public void updateEpic(Epic epic);
    public List<Epic> getAllEpic();
    public void deleteEpic();
    public void deleteEpicId(int idEpic);
    public Epic getEpicId(int idEpic);
    public void createSubTask(Subtask subtask);
    public void updateSubTask(Subtask subTask);
    public List<Subtask> getAllSubTask();
    public List<Subtask> getAllSubTaskEpicId(int idEpic);
    public void deleteSubTask();
    public void deleteSubTaskId(int idSubTask);
    public Subtask getSubTaskId(int idSubTask);
}
