package task;

import java.util.Objects;

public class Subtask extends Task {
    private int idEpic;

    public int getIdEpic() {
        return idEpic;
    }

    public void setIdEpic(int idEpic) {
        this.idEpic = idEpic;
    }
    @Override
    public String toString() {
        return "Имя подзадачи': '" + nameTask + "', описание подзадачи': '" + descriptionTask
                + "', статус подзадачи: '" + statusTask + "', ИД подзадачи: '" + idTask
                + "', ИД эпика подзадачи: '" + idEpic + "', start time: '" + startTime
                + "', duration: '" + taskDuration + "'";
    }

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask task = (Subtask) o;
        return Objects.equals(nameTask, task.nameTask)
                && Objects.equals(descriptionTask, task.descriptionTask)
                && Objects.equals(statusTask, task.statusTask)
                && idTask == task.idTask
                && taskDuration == task.taskDuration
                && Objects.equals(startTime, task.startTime)
                && idEpic == task.idEpic;
    }

    @Override
    public Type getTypeTask() {
        return Type.SUBTASK;
    }
}