package logic;
import task.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int MAX_SIZE_TASK_HISTORY = 10;
    private final LinkedList<Task> taskHistory = new LinkedList<>();

    @Override
    public void addTask(Task task) {
        taskHistory.add(task);
        checkSizeTaskHistory();
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(taskHistory);
    }

    public void checkSizeTaskHistory() {
        while (taskHistory.size() > MAX_SIZE_TASK_HISTORY) {
            taskHistory.removeFirst();
        }
    }
}