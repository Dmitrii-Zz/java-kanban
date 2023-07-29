package logic;

public class Managers {

    public static HttpTaskManager getDefault(String path) {
        return new HttpTaskManager(path);
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager<>();
    }
}
