package logic;
import task.*;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    static List<Task> taskHistory = new ArrayList<>();

    @Override
    public void addTask(Task task) {
        taskHistory.add(task);
        checkSizeTaskHistory();
    }

    @Override
    public List<Task> getHistory() {
        return taskHistory;
    }

    public void checkSizeTaskHistory() {
        while (taskHistory.size() > 10) {
            taskHistory.remove(0);
        }
    }
}