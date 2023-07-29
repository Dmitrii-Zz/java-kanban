package logic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.*;
import task.StatusTask;
import task.*;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class HttpTest {

    private final KVTaskClient kvTaskClient = new KVTaskClient("http://localhost:9090");
    private static KVServer kvServer;
    private static HttpTaskServer httpTaskServer;
    private final HttpClient client = HttpClient.newHttpClient();
    private HttpRequest request;
    private URI url;
    private final Gson gson = new Gson();
    private String jsonTask;
    private Task taskLoad;
    private Task taskLoad1;
    private Epic epicLoad;
    private Subtask subtaskLoad;

    @BeforeAll
    public static void startKVServer() throws IOException {
        kvServer = new KVServer();
        kvServer.start();
        httpTaskServer = new HttpTaskServer();
        httpTaskServer.start();
    }

    @AfterAll
    public static void stopKVServer() throws IOException {
        kvServer.stop();
        httpTaskServer.stop();
    }

    @BeforeAll
    public static void createTask() {

    }

    @BeforeEach
    public void addTaskOnServer() {
        kvTaskClient.put("/task", gson.toJson(taskLoad));
        kvTaskClient.put("/epic", gson.toJson(epicLoad));
        kvTaskClient.put("/subtask", gson.toJson(subtaskLoad));
    }

    @BeforeEach
    public void addTask() throws IOException, InterruptedException {
        Task task = new Task();
        task.setNameTask("Создать тест для HTTP.");
        task.setDescriptionTask("Создаем тест...");
        task.setStatusTask(StatusTask.IN_PROGRESS);
        task.setStartTime(LocalDateTime.of(2023, 7, 29, 12, 58));
        task.setTaskDuration(120);
        task.setIdTask(1);
        jsonTask = gson.toJson(task);
        url = URI.create("http://localhost:8080/tasks/task");
        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(jsonTask);
        request = HttpRequest.newBuilder()
                .uri(url)
                .POST(body)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("\nСоздаем задачу." + "\nТело ответа: " + response.body());
        assertEquals(201, response.statusCode());
    }

    @Test
    public void handleTest() throws IOException {
        String token = kvTaskClient.registration();
        assertEquals(13, token.length());
    }

    @Test
    public void httpTaskServerTest() throws IOException, InterruptedException {
        System.out.println("\nЗапрашиваем все задачи.");
        url = URI.create("http://localhost:8080/tasks/task");
        request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Тело ответа " + response.body());

        String expectedJsonTask = "[" + jsonTask + "]";
        assertEquals(expectedJsonTask, response.body());
        assertEquals(200, response.statusCode());
    }
}
