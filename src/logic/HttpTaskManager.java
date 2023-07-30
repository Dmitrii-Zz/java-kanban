package logic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import task.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {

    private final Gson gson = new Gson();
    private final KVTaskClient kvTaskClient;

    public KVTaskClient getKvTaskClient() {
        return kvTaskClient;
    }

    public HttpTaskManager(String path) {
        super(path);
        kvTaskClient = new KVTaskClient(path);
        kvTaskClient.registration();
    }

    @Override
    protected void save() {
        if (kvTaskClient == null) {
            System.out.println("Пользователь не авторизирован.");
            return;
        }

        kvTaskClient.put("/tasks", gson.toJson(getTaskSortPriority()));
        kvTaskClient.put("/task", gson.toJson(getAllTask()));
        kvTaskClient.put("/epic", gson.toJson(getAllEpic()));
        kvTaskClient.put("/subtask", gson.toJson(getAllSubTask()));

    }

    public void loadTasks() {
        if (kvTaskClient == null) {
            System.out.println("Пользователь не авторизирован.");
            return;
        }

        String jsonAllTasks = kvTaskClient.load("/tasks");
        Type typeAllTask = TypeToken.getParameterized(ArrayList.class, Task.class, Epic.class, Subtask.class)
                .getType();
        ArrayList<Task> allTasks = gson.fromJson(jsonAllTasks, typeAllTask);
        taskSortPriority.addAll(allTasks);

        String jsonTasks = kvTaskClient.load("/task");
        Type typeTask = TypeToken.getParameterized(ArrayList.class, Task.class).getType();
        ArrayList<Task> allTask = gson.fromJson(jsonAllTasks, typeTask);
        for (Task t : allTask) {
            tasks.put(t.getIdTask(), t);
        }

        String jsonEpics = kvTaskClient.load("/epics");
        Type typeEpic = TypeToken.getParameterized(ArrayList.class, Epic.class).getType();
        ArrayList<Epic> allEpic = gson.fromJson(jsonEpics, typeEpic);
        for (Epic e : allEpic) {
            tasks.put(e.getIdTask(), e);
        }

        String jsonSubtask = kvTaskClient.load("/subtasks");
        Type typeSubtask = TypeToken.getParameterized(ArrayList.class, Epic.class).getType();
        ArrayList<Subtask> allSubtask = gson.fromJson(jsonSubtask, typeSubtask);
        for (Subtask s : allSubtask) {
            tasks.put(s.getIdTask(), s);
        }

        String jsonHistory = kvTaskClient.load("/history");
        String historyString = jsonHistory.substring(1, jsonHistory.length() - 1);
        if (historyString.equals("\"\"")) {
            return;
        } else {
            String[] contentHistory = historyString.split(",");
            for (String id : contentHistory) {
                for (Task task : allTasks) {
                    if (task.getIdTask() == Integer.parseInt(id)) {
                        historyManager.addTask(task);
                    }
                }
            }
        }
    }

    @Override
    public List<Task> getAllTask() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpic() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubTask() {
        return new ArrayList<>(subTasks.values());
    }
}
