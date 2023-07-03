package logic;
import task.*;
import java.util.List;

public interface TaskManager {

    void createTask(Task task) throws ManagerSaveException;
    void updateTask(Task task) throws ManagerSaveException;
    List<Task> getAllTask() throws ManagerSaveException;
    void deleteTask() throws ManagerSaveException;
    void deleteTaskId(int idTask) throws ManagerSaveException;
    Task getTask(int idTask) throws ManagerSaveException;
    void createEpic(Epic epic) throws ManagerSaveException;
    void updateEpic(Epic epic) throws ManagerSaveException;
    List<Epic> getAllEpic() throws ManagerSaveException;
    void deleteEpic() throws ManagerSaveException;
    void deleteEpicId(int idEpic) throws ManagerSaveException;
    Epic getEpicId(int idEpic) throws ManagerSaveException;
    void createSubTask(Subtask subtask) throws ManagerSaveException;
    void updateSubTask(Subtask subTask) throws ManagerSaveException;
    List<Subtask> getAllSubTask() throws ManagerSaveException;
    List<Subtask> getAllSubTaskEpicId(int idEpic) throws ManagerSaveException;
    void deleteSubTask() throws ManagerSaveException;
    void deleteSubTaskId(int idSubTask) throws ManagerSaveException;
    Subtask getSubTaskId(int idSubTask) throws ManagerSaveException;
    List<Task> getHistory();
}
