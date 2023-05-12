package logic;
import task.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    private int idTask = 0;
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subTasks = new HashMap<>();

    public void createTask(Task task) {
        idTask++;
        task.setIdTask(idTask);
        tasks.put(idTask, task);
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getIdTask())) {
            tasks.put(task.getIdTask(), task);
        }
    }

    public ArrayList<Task> getAllTask() {
        return new ArrayList<>(tasks.values());
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

    public void createEpic(Epic epic) {
        idTask++;
        epic.setStatusTask("NEW");
        epic.setIdTask(idTask);
        epics.put(idTask, epic);
    }

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getIdTask())) {
            epics.put(epic.getIdTask(), epic);
            fillEpicStatus(epic.getIdTask());
        }
    }

    public ArrayList<Epic> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    public void deleteEpic() {
        epics.clear();
        tasks.clear();
    }

    public void deleteEpicId(int idEpic) {
        if (epics.containsKey(idEpic)) {
            Epic epic = epics.get(idEpic);
            ArrayList<Integer> idSubTasks = epic.getIdSubTasks();
            for (int idSubTask : idSubTasks) {
                subTasks.remove(idSubTask);
            }
            epics.remove(idEpic);
        }
    }

    public Epic getEpicId(int idEpic) {
        return epics.get(idEpic);
    }

    public void createSubTask(Subtask subtask) {
        idTask++;
        subtask.setIdTask(idTask);
        Epic epic = epics.get(subtask.getIdEpic());
        ArrayList<Integer> idSubTasks = epic.getIdSubTasks();
        idSubTasks.add(idTask);
        epic.setIdSubTasks(idSubTasks);
        subTasks.put(idTask, subtask);
        fillEpicStatus(subtask.getIdEpic());
    }

    public void updateSubTask(Subtask subTask) {
        if (subTasks.containsKey(subTask.getIdTask())) {
            subTasks.put(subTask.getIdTask(), subTask);
            fillEpicStatus(subTask.getIdEpic());
        }
    }

    public ArrayList<Subtask> getAllSubTask() {
        return new ArrayList<>(subTasks.values());
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
        ArrayList<Integer> newIdSubTasks = new ArrayList<>();
        for (int idEpic : epics.keySet()) {
            Epic epic = epics.get(idEpic);
            epic.setIdSubTasks(newIdSubTasks);
            fillEpicStatus(idEpic);
        }
    }

    public void deleteSubTaskId(int idSubTask) {
        if (subTasks.containsKey(idSubTask)) {
            Subtask subtask = subTasks.get(idSubTask);
            int idEpic = subtask.getIdEpic();
            Epic epic = epics.get(idEpic);
            ArrayList<Integer> newSubTaskId = epic.getIdSubTasks();
            newSubTaskId.remove((Integer) idSubTask);
            epic.setIdSubTasks(newSubTaskId);
            subTasks.remove(idSubTask);
            fillEpicStatus(idEpic);
        }
    }

    public Subtask getSubTaskId(int idSubTask) {
        return subTasks.get(idSubTask);
    }

    private void fillEpicStatus(int idEpic) {
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