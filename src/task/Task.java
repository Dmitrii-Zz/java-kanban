package task;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class Task {
    protected String nameTask;
    protected String descriptionTask;
    protected StatusTask statusTask;
    protected int idTask;
    protected Type typeTask;
    protected int taskDuration;
    protected LocalDateTime startTime;
    protected boolean isCrossTask;


    public void setNameTask(String nameTask) {
        this.nameTask = nameTask;
    }

    public void setDescriptionTask(String descriptionTask) {
        this.descriptionTask = descriptionTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public void setStatusTask(StatusTask statusTask) {
        this.statusTask = statusTask;
    }

    public String getNameTask() {
        return nameTask;
    }

    public String getDescriptionTask() {
        return descriptionTask;
    }

    public int getIdTask() {
        return idTask;
    }

    public StatusTask getStatusTask() {
        return statusTask;
    }

    public Type getTypeTask() {
        return typeTask;
    }

    public void setTypeTask(Type typeTask) {
        this.typeTask = typeTask;
    }

    @Override
    public String toString() {
        return "Имя задачи: '" + nameTask + "', описание задачи: '" + descriptionTask + "', статус задачи: '"
                + statusTask + "', ИД задачи: '" + idTask + "', start time: '" + startTime
                + "', duration: '" + taskDuration + "', пересечение: '" + isCrossTask + "'";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(nameTask, task.nameTask)
                && Objects.equals(descriptionTask, task.descriptionTask)
                && Objects.equals(statusTask, task.statusTask)
                && idTask == task.idTask
                && Objects.equals(typeTask, task.typeTask)
                && taskDuration == task.taskDuration
                && Objects.equals(startTime, task.startTime);
    }

    public void setTaskDuration(int taskDuration) {
        this.taskDuration = taskDuration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Optional<LocalDateTime> getStartTime() {
        return Optional.ofNullable(startTime);
    }

    public Optional<LocalDateTime> getEndTime() {
        if (startTime == null) {
            return Optional.empty();
        }

        return Optional.of(startTime.plusMinutes(taskDuration));
    }

    public int getTaskDuration() {
        return taskDuration;
    }

    public void setIsCrossTask(boolean isCross) {
        this.isCrossTask = isCross;
    }

    public boolean getIsCrossTask() {
        return isCrossTask;
    }
}