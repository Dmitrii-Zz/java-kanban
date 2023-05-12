package task;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> idSubTasks = new ArrayList<>();

    public ArrayList<Integer> getIdSubTasks() {
        return idSubTasks;
    }

    public void setIdSubTasks(ArrayList<Integer> idSubTasks) {
        this.idSubTasks = idSubTasks;
    }

    @Override
    public String toString() {
        return "\n" + "Имя эпика': '" + nameTask + "', описание эпика': '" + descriptionTask + "', статус эпика: '"
                + statusTask + "', ИД эпика: '" + idTask + "', ИД подзадач: " + idSubTasks;
    }
}