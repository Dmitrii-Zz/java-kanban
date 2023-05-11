package task;

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
        return "\n" + "Имя подзадачи': '" + nameTask + "', описание подзадачи': '" + descriptionTask
                + "', статус подзадачи: '" + statusTask + "', ИД подзадачи: '" + idTask
                + "', ИД эпика подзадачи: " + idEpic;
    }
}