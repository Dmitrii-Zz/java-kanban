package task;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> idSubTasks = new ArrayList<>();

    public List<Integer> getIdSubTasks() {
        return idSubTasks;
    }

    public void setIdSubTasks(List<Integer> idSubTasks) {
        this.idSubTasks = idSubTasks;
    }

    @Override
    public String toString() {
        return idTask + ","
                + Type.EPIC + ","
                + nameTask + ","
                + statusTask + ","
                + descriptionTask + ",\n";
    }
}