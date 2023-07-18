package task;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Epic extends Task {
    private List<Integer> idSubTasks = new ArrayList<>();
    private LocalDateTime endTime;

    public List<Integer> getIdSubTasks() {
        return idSubTasks;
    }

    public void setIdSubTasks(List<Integer> idSubTasks) {
        this.idSubTasks = idSubTasks;
    }

    @Override
    public String toString() {
        return  "Имя эпика': '" + nameTask + "', описание эпика': '" + descriptionTask + "', статус эпика: '"
                + statusTask + "', ИД эпика: '" + idTask + "', ИД подзадач: " + idSubTasks
                + "', start time: '" + startTime + "', duration: '" + taskDuration + "'";

    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic task = (Epic) o;
        return Objects.equals(nameTask, task.nameTask)
                && Objects.equals(descriptionTask, task.descriptionTask)
                && Objects.equals(statusTask, task.statusTask)
                && idTask == task.idTask
                && Objects.equals(typeTask, task.typeTask)
                && Objects.equals(idSubTasks, task.idSubTasks)
                && taskDuration == task.taskDuration
                && Objects.equals(startTime, task.startTime)
                && Objects.equals(endTime, task.endTime);
    }

    @Override
    public Optional<LocalDateTime> getEndTime() {
        if (endTime == null) {
            return Optional.empty();
        }
        return Optional.of(endTime);
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}