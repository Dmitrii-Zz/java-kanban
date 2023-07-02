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
        return idTask + ","
                + Type.SUBTASK + ","
                + nameTask + ","
                + statusTask + ","
                + descriptionTask + ","
                + idEpic + "\n";
    }
}