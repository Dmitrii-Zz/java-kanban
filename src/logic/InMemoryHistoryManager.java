package logic;
import task.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager<T> implements HistoryManager {

    private final Map<Integer, Node<Task>> historyTask = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;
    private int size;

    private static class Node<Task> {
        private Task data;
        private Node<Task> next;
        private Node<Task> prev;

        public Node(Task data, Node<Task> next, Node<Task> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }

    private void linkLast(Task task, Integer id) {

        if (historyTask.containsKey(id)) {
            remove(id);
            historyTask.remove(id);
        }

        Node<Task> oldTail = tail;
        Node<Task> newNode = new Node<>(task, null, oldTail);
        tail = newNode;
        historyTask.put(id, tail);
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        size++;
    }

    private List<Task> getTasks() {
        List<Task> tempTasksArray = new ArrayList<>();
        Node<Task> curHead = tail;
        while (curHead != null) {
            tempTasksArray.add(curHead.data);
            curHead = curHead.prev;
        }
        return tempTasksArray;
    }

    @Override
    public void addTask(Task task) {
        linkLast(task, task.getIdTask());
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {

        if (historyTask.get(id) != null) {

            Node<Task> deleteNode = historyTask.get(id);

            Task data = deleteNode.data;
            Node<Task> next = deleteNode.next;
            Node<Task> prev = deleteNode.prev;

            if (prev == null) {
                head = next;
            } else {
                prev.next = next;
                deleteNode.prev = null;
            }

            if (next == null) {
                tail = prev;
            } else {
                next.prev = prev;
                deleteNode.next = null;
            }

            deleteNode.data = null;
            size--;
        }
    }
}