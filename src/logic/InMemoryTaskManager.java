package logic;

import task.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Comparator;

public class InMemoryTaskManager implements TaskManager {
    private static final int FIRST_TASK = 0;
    private int idTask = 0;
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();
    protected final Map<Integer, Subtask> subTasks = new HashMap<>();
    protected final HistoryManager historyManager = Managers.getDefaultHistory();
    protected final TreeSet<Task> taskSortPriority = new TreeSet<>(
            new Comparator<Task>() {
                @Override
                public int compare(Task task1, Task task2) {

                    Optional<LocalDateTime> task1TaskStartTime = task1.getStartTime();
                    Optional<LocalDateTime> task2TaskStartTime = task2.getStartTime();

                    if (task1TaskStartTime.isEmpty() && task2TaskStartTime.isEmpty()) {
                        return task1.getIdTask() - task2.getIdTask();
                    }

                    if (task1.getStartTime().equals(task2.getStartTime())) {
                        return task1.getIdTask() - task2.getIdTask();
                    }

                    if (task1TaskStartTime.isEmpty()) {
                        return 1;
                    }

                    if (task2TaskStartTime.isEmpty()) {
                        return -1;
                    }

                    if (task1TaskStartTime.get().isBefore(task2TaskStartTime.get())) {
                        return -1;
                    }

                    return 1;
                }
            });

    protected void setTaskId (int idTask) {
        this.idTask = idTask;
    }

    @Override
    public void createTask(Task task) {

        if (checkDataTime(task)) {
            return;
        }

        idTask++;
        task.setIdTask(idTask);
        tasks.put(idTask, task);
        taskSortPriority.add(task);
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getIdTask())) {

            if (checkDataTime(task)) {
                return;
            }

            tasks.put(task.getIdTask(), task);
            removeTaskPriority(task);
            taskSortPriority.add(task);
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
            removeTaskPriority(tasks.get(id));
        }

        tasks.clear();
    }

    @Override
    public void deleteTaskId(int idTask) {
        removeTaskPriority(tasks.get(idTask));
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

        subTasks.clear();
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

            if (checkDataTime(subtask)) {
                return;
            }

            idTask++;
            subtask.setIdTask(idTask);
            Epic epic = epics.get(subtask.getIdEpic());
            List<Integer> idSubTasks = epic.getIdSubTasks();
            idSubTasks.add(idTask);
            epic.setIdSubTasks(idSubTasks);
            subTasks.put(idTask, subtask);
            fillEpicStatus(subtask.getIdEpic());
            calculationEpicEndTime(subtask.getIdEpic());
            taskSortPriority.add((Task) subtask);
        }
    }

    @Override
    public void updateSubTask(Subtask subTask) {
        if (subTasks.containsKey(subTask.getIdTask())) {

            if (checkDataTime(subTask)) {
                return;
            }

            subTasks.put(subTask.getIdTask(), subTask);
            fillEpicStatus(subTask.getIdEpic());
            removeTaskPriority(subTask);
            taskSortPriority.add((Task) subTask);
            calculationEpicEndTime(subTask.getIdEpic());
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
            removeTaskPriority(subTasks.get(id));
        }

        subTasks.clear();

        List<Integer> newIdSubTasks = new ArrayList<>();

        for (int idEpic : epics.keySet()) {
            Epic epic = epics.get(idEpic);
            epic.setIdSubTasks(newIdSubTasks);
            fillEpicStatus(idEpic);
            calculationEpicEndTime(idEpic);
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
            historyManager.remove(idSubTask);
            subTasks.remove(idSubTask);
            fillEpicStatus(idEpic);
            calculationEpicEndTime(idEpic);
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

    public TreeSet<Task> getTaskSortPriority() {
        return taskSortPriority;
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

    private void calculationEpicEndTime(int idEpic) {
        Epic epic = epics.get(idEpic);
        List<Integer> idSubTasks = epic.getIdSubTasks();
        int lastTask = idSubTasks.size() - 1;

        if (idSubTasks.isEmpty()) {
            return;
        }

        int durationTask = 0;
        Subtask tempStartTimeSubTask = subTasks.get(idSubTasks.get(FIRST_TASK));
        Optional<LocalDateTime> startDataTime = tempStartTimeSubTask.getStartTime();
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;

        if (startDataTime.isPresent()) {
            startTime = startDataTime.get();
            epic.setStartTime(startTime);
            endTime = startDataTime.get();
        }

        for (int idSubTask : idSubTasks) {
            tempStartTimeSubTask = subTasks.get(idSubTask);
            Optional<LocalDateTime> tempDataTime = tempStartTimeSubTask.getStartTime();
            durationTask += tempStartTimeSubTask.getTaskDuration();

            if (tempDataTime.isPresent() && startDataTime.isPresent()) {
                LocalDateTime tempStartTime = tempDataTime.get();

                if (tempStartTime.isBefore(startTime)) {
                    startTime = tempStartTime;
                    epic.setStartTime(tempStartTime);
                }

                if (tempStartTime.isAfter(endTime)) {
                    endTime = tempStartTime;
                }
            }
        }

        if (endTime != null) {
            epic.setEndTime(endTime.plusMinutes(subTasks.get(idSubTasks.get(lastTask)).getTaskDuration()));
        }
        epic.setTaskDuration(durationTask);
    }

    private void removeTaskPriority(Task task) {
        Set<Task> tempSet = new TreeSet<>(taskSortPriority);
        for (Task t : tempSet) {
            if (t.getIdTask() == task.getIdTask()) {
                taskSortPriority.remove(t);
            }
        }
    }

    private boolean checkDataTime(Task task) {

        Optional<LocalDateTime> optionalTaskStartTime = task.getStartTime();
        Optional<LocalDateTime> optionalTaskEndTime = task.getEndTime();

        LocalDateTime taskStartTime;
        LocalDateTime taskEndTime;
        LocalDateTime tStartTime;
        LocalDateTime tEndTime;

        boolean isCrossTaskFirstCase;
        boolean isCrossTaskSecondCase;
        boolean isCrossTaskThirdCase;
        boolean isCrossTaskFourthCase;
        boolean isCrossTask;

        for (Task t : taskSortPriority) {

            if (t.equals(task)) {
                continue;
            }

            Optional<LocalDateTime> optionalTStartTime = t.getStartTime();
            Optional<LocalDateTime> optionalTEndTime = t.getEndTime();

            boolean isNotEmptyDataTime = optionalTaskStartTime.isPresent()
                    && optionalTStartTime.isPresent()
                    && optionalTaskEndTime.isPresent()
                    && optionalTEndTime.isPresent();

            if (isNotEmptyDataTime) {

                tStartTime = optionalTStartTime.get();
                tEndTime = optionalTEndTime.get();
                taskStartTime = optionalTaskStartTime.get();
                taskEndTime = optionalTaskEndTime.get();

                isCrossTaskFirstCase = taskStartTime.isBefore(tStartTime)
                        && taskEndTime.isBefore(tEndTime)
                        && taskEndTime.isAfter(tStartTime);

                isCrossTaskSecondCase = taskStartTime.isAfter(tStartTime)
                        && taskStartTime.isBefore(tEndTime)
                        && taskEndTime.isAfter(tEndTime);

                isCrossTaskThirdCase = taskStartTime.isAfter(tStartTime)
                        && taskEndTime.isBefore(tEndTime);

                isCrossTaskFourthCase = taskStartTime.isBefore(tStartTime)
                        && taskEndTime.isAfter(tEndTime);

                isCrossTask = isCrossTaskFirstCase || isCrossTaskSecondCase ||
                        isCrossTaskThirdCase || isCrossTaskFourthCase;

                if (isCrossTask) {
                    return true;
                }
            }
        }

        return false;
    }
}