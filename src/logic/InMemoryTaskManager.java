package logic;
import task.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
    private int idTask = 0;
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, Subtask> subTasks = new HashMap<>();
    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void createTask(Task task) {
        idTask++;
        task.setTypeTask(Type.TASK);
        task.setIdTask(idTask);
        tasks.put(idTask, task);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getIdTask())) {
            tasks.put(task.getIdTask(), task);
        }
    }

    @Override
    public List<Task> getAllTask() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteTask() {

        for (int id : tasks.keySet()) {
            historyManager.remove(id);
        }

        tasks.clear();
    }

    @Override
    public void deleteTaskId(int idTask) {
        tasks.remove(idTask);
        historyManager.remove(idTask);
    }

    @Override
    public Task getTask(int idTask) {
        if (tasks.containsKey(idTask)) {
            historyManager.addTask(tasks.get(idTask));
        }
        return tasks.get(idTask);
    }

    @Override
    public void createEpic(Epic epic) {
        idTask++;
        epic.setTypeTask(Type.EPIC);
        epic.setStatusTask(StatusTask.NEW);
        epic.setIdTask(idTask);
        epics.put(idTask, epic);
    }

    @Override
    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getIdTask())) {
            Epic updateEpic = epics.get(epic.getIdTask());
            updateEpic.setNameTask(epic.getNameTask());
            updateEpic.setDescriptionTask(epic.getDescriptionTask());
            fillEpicStatus(epic.getIdTask());
        }
    }

    @Override
    public List<Epic> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteEpic() {

        for (int id : subTasks.keySet()) {
            historyManager.remove(id);
        }

        for (int id : epics.keySet()) {
            historyManager.remove(id);
        }

        tasks.clear();
        epics.clear();

    }

    @Override
    public void deleteEpicId(int idEpic) {
        if (epics.containsKey(idEpic)) {
            Epic epic = epics.get(idEpic);
            List<Integer> idSubTasks = epic.getIdSubTasks();
            for (int idSubTask : idSubTasks) {
                subTasks.remove(idSubTask);
                historyManager.remove(idSubTask);
            }
            epics.remove(idEpic);
            historyManager.remove(idEpic);
        }
    }

    @Override
    public Epic getEpicId(int idEpic) {
        if (epics.containsKey(idEpic)) {
            historyManager.addTask(epics.get(idEpic));
        }
        return epics.get(idEpic);
    }

    @Override
    public void createSubTask(Subtask subtask) {
        if (epics.containsKey(subtask.getIdEpic())) {
            idTask++;
            subtask.setTypeTask(Type.SUBTASK);
            subtask.setIdTask(idTask);
            Epic epic = epics.get(subtask.getIdEpic());
            List<Integer> idSubTasks = epic.getIdSubTasks();
            idSubTasks.add(idTask);
            epic.setIdSubTasks(idSubTasks);
            subTasks.put(idTask, subtask);
            fillEpicStatus(subtask.getIdEpic());
        }
    }

    @Override
    public void updateSubTask(Subtask subTask) {
        if (subTasks.containsKey(subTask.getIdTask())) {
            subTasks.put(subTask.getIdTask(), subTask);
            fillEpicStatus(subTask.getIdEpic());
        }
    }

    @Override
    public List<Subtask> getAllSubTask() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public List<Subtask> getAllSubTaskEpicId(int idEpic) {
        Epic epic = epics.get(idEpic);
        List<Subtask> getAllSubTaskEpicId = new ArrayList<>();
        for(int idSubTask : epic.getIdSubTasks()) {
            getAllSubTaskEpicId.add(subTasks.get(idSubTask));
        }
        return getAllSubTaskEpicId;
    }

    @Override
    public void deleteSubTask() {

        for (int id : subTasks.keySet()) {
            historyManager.remove(id);
        }

        subTasks.clear();

        List<Integer> newIdSubTasks = new ArrayList<>();
        for (int idEpic : epics.keySet()) {
            Epic epic = epics.get(idEpic);
            epic.setIdSubTasks(newIdSubTasks);
            fillEpicStatus(idEpic);
        }
    }

    @Override
    public void deleteSubTaskId(int idSubTask) {
        if (subTasks.containsKey(idSubTask)) {
            Subtask subtask = subTasks.get(idSubTask);
            int idEpic = subtask.getIdEpic();
            Epic epic = epics.get(idEpic);
            List<Integer> newSubTaskId = epic.getIdSubTasks();
            newSubTaskId.remove((Integer) idSubTask);
            epic.setIdSubTasks(newSubTaskId);
            subTasks.remove(idSubTask);
            fillEpicStatus(idEpic);
            historyManager.remove(idSubTask);
        }
    }

    @Override
    public Subtask getSubTaskId(int idSubTask) {
        if (subTasks.containsKey(idSubTask)) {
            historyManager.addTask(subTasks.get(idSubTask));
        }
        return subTasks.get(idSubTask);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void fillEpicStatus(int idEpic) {
        Epic epic = epics.get(idEpic);
        List<Integer> idSubTasks = epic.getIdSubTasks();
        if (idSubTasks.isEmpty()) {
            epic.setStatusTask(StatusTask.NEW);
            return;
        }
        boolean statusNew = false;
        boolean statusDone = false;
        for (int idSubTask : idSubTasks) {
            Subtask subtask = subTasks.get(idSubTask);
            switch (subtask.getStatusTask()) {
                case IN_PROGRESS:
                    epic.setStatusTask(StatusTask.IN_PROGRESS);
                    return;
                case NEW:
                    statusNew = true;
                    break;
                case DONE:
                    statusDone = true;
                    break;
            }
        }
        if (statusNew && statusDone) {
            epic.setStatusTask(StatusTask.IN_PROGRESS);
        } else if (statusNew) {
            epic.setStatusTask(StatusTask.NEW);
        } else {
            epic.setStatusTask(StatusTask.DONE);
        }
    }

    protected void setTaskId (int idTask) {
        this.idTask = idTask;
    }
}