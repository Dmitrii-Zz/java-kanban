package logic;
import task.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private int idTask = 0;
    public HashMap<Integer, Task> tasks = new HashMap<>();
    public HashMap<Integer, Epic> epics = new HashMap<>();
    public HashMap<Integer, Subtask> subTasks = new HashMap<>();

    public void createTask(String nameTask, String descriptionTask, String statusTask) {
        idTask++;
        Task task = new Task();
        task.setNameTask(nameTask);
        task.setDescriptionTask(descriptionTask);
        task.setStatusTask(statusTask);
        task.setIdTask(idTask);
        tasks.put(idTask, task);
    }

    public void updateTask(String nameTask, String descriptionTask, String statusTask, int idTask) {
        Task task = tasks.get(idTask);
        task.setNameTask(nameTask);
        task.setDescriptionTask(descriptionTask);
        task.setStatusTask(statusTask);
        task.setIdTask(idTask);
        tasks.put(idTask, task);
    }

    public ArrayList<Task> getAllTask() {
        ArrayList<Task> getAllTask = new ArrayList<>();
        for (Task task : tasks.values()) {
            getAllTask.add(task);
        }
        return getAllTask;
    }

    public void deleteTask() {
        tasks.clear();
    }

    public void deleteTaskId(int idTask) {
        tasks.remove(idTask);
    }

    public Task getTask(int idTask) {
        return tasks.get(idTask);
    }

    public void createEpic(String nameEpic, String descriptionEpic) {
        idTask++;
        Epic epic = new Epic();
        epic.setNameTask(nameEpic);
        epic.setDescriptionTask(descriptionEpic);
        epic.setStatusTask("NEW");
        epic.setIdTask(idTask);
        epics.put(idTask, epic);
    }

    public void updateEpic(String nameEpic, String descriptionEpic, int idEpic) {
        Epic epic = epics.get(idEpic);
        epic.setNameTask(nameEpic);
        epic.setDescriptionTask(descriptionEpic);
        //epic.setIdTask(idTask);
        epics.put(idEpic, epic);
        checkStatusEpic(idEpic);
    }

    public ArrayList<Epic> getAllEpic() {
        ArrayList<Epic> getAllEpic = new ArrayList<>();
        for (Epic epic : epics.values()) {
            getAllEpic.add(epic);
        }
        return getAllEpic;
    }

    public void deleteEpic() {
        epics.clear();
        tasks.clear();
    }

    public void deleteEpicId(int idEpic) {
        Epic epic = epics.get(idEpic);
        ArrayList<Integer> idSubTasks = epic.getIdSubTasks();
        for (int idSubTask : idSubTasks) {
            subTasks.remove(idSubTask);
        }
        epics.remove(idEpic);
    }

    public Epic getEpicId(int idEpic) {
        return epics.get(idEpic);
    }

    public void createSubTask(String nameSubtask, String descriptionSubtask, String statusSubtask, int idEpic) {
        idTask++;
        Subtask subTask = new Subtask();
        subTask.setNameTask(nameSubtask);
        subTask.setDescriptionTask(descriptionSubtask);
        subTask.setStatusTask(statusSubtask);
        subTask.setIdTask(idTask);
        subTask.setIdEpic(idEpic);
        Epic epic = epics.get(idEpic);
        ArrayList<Integer> idSubTasks = epic.getIdSubTasks();
        idSubTasks.add(idTask);
        epic.setIdSubTasks(idSubTasks);
        subTasks.put(idTask, subTask);
        checkStatusEpic(idEpic);
    }

    public void updateSubTask(String nameSubtask, String descriptionSubtask, String statusSubtask, int idSubTask) {
        Subtask subTask = subTasks.get(idSubTask);
        subTask.setNameTask(nameSubtask);
        subTask.setDescriptionTask(descriptionSubtask);
        subTask.setStatusTask(statusSubtask);
        subTasks.put(idSubTask, subTask);
        checkStatusEpic(subTask.getIdEpic());
    }

    public ArrayList<Subtask> getAllSubTask() {
        ArrayList<Subtask> getAllSubTask = new ArrayList<>();
        for (Subtask subtask : subTasks.values()) {
            getAllSubTask.add(subtask);
        }
        return getAllSubTask;
    }

    public ArrayList<Subtask> getAllSubTaskEpicId(int idEpic) {
        Epic epic = epics.get(idEpic);
        ArrayList<Subtask> getAllSubTaskEpicId = new ArrayList<>();
        for(int idSubTask : epic.getIdSubTasks()) {
            getAllSubTaskEpicId.add(subTasks.get(idSubTask));
        }
        return getAllSubTaskEpicId;
    }

    public void deleteSubTask() {
        subTasks.clear();
    }

    public void deleteSubTaskId(int idSubTask) {
        subTasks.remove(idSubTask);
    }

    public Subtask getSubTaskId(int idSubTask) {
        return subTasks.get(idSubTask);
    }

    public void checkStatusEpic(int idEpic) {
        Epic epic = epics.get(idEpic);
        ArrayList<Integer> idSubTasks = epic.getIdSubTasks();
        if (idSubTasks.size() == 0) {
            epic.setStatusTask("NEW");
            return;
        }
        boolean statusNew = false;
        boolean statusDone = false;
        for (int idSubTask : idSubTasks) {
            Subtask subtask = subTasks.get(idSubTask);
            if (subtask.getStatusTask().equals("IN_PROGRESS")) {
                epic.setStatusTask("IN_PROGRESS");
                return;
            } else if (subtask.getStatusTask().equals("NEW")) {
                statusNew = true;
            } else if (subtask.getStatusTask().equals("DONE")) {
                statusDone = true;
            }
        }
        if (statusNew && statusDone) {
            epic.setStatusTask("IN_PROGRESS");
        } else if (statusNew && !statusDone) {
            epic.setStatusTask("NEW");
        } else {
            epic.setStatusTask("DONE");
        }
    }
}