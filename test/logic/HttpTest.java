package logic;

import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import task.StatusTask;
import task.*;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public class HttpTest {

    private final KVTaskClient kvTaskClient = new KVTaskClient("http://localhost:9090");
    private static KVServer kvServer;
    private static HttpTaskServer httpTaskServer;
    private final HttpClient client = HttpClient.newHttpClient();
    private HttpRequest request;
    private URI url;
    private final Gson gson = new Gson();
    private HttpRequest.BodyPublisher body;
    private HttpResponse<String> response;
    private String jsonTask;
    private String jsonEpic;

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
        body = HttpRequest.BodyPublishers.ofString(jsonTask);
        request = HttpRequest.newBuilder()
                .uri(url)
                .POST(body)
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("\nСоздаем задачу." + "\nТело ответа: " + response.body());
        assertEquals(201, response.statusCode());

        Epic epic = new Epic();
        epic.setNameTask("Создать тест для эпика");
        epic.setDescriptionTask("Создаем...");
        epic.setStatusTask(StatusTask.NEW);
        epic.setIdTask(2);
        jsonEpic = gson.toJson(epic);
        url = URI.create("http://localhost:8080/tasks/epic");
        body = HttpRequest.BodyPublishers.ofString(jsonEpic);
        request = HttpRequest.newBuilder()
                .uri(url)
                .POST(body)
                .build();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("\nСоздаем эпик." + "\nТело ответа: " + response.body());
        assertEquals(201, response.statusCode());
    }

    @BeforeEach
    public void handleTest() throws IOException {
        String token = kvTaskClient.registration();
        assertEquals(13, token.length());

    }

    @Test
    public void httpTaskServerGetTaskTest() throws IOException, InterruptedException {
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

    @Test
    public void httpTaskServerGetEpicTest() throws IOException, InterruptedException {
        System.out.println("\nЗапрашиваем все эпики.");
        url = URI.create("http://localhost:8080/tasks/epic");
        request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Тело ответа " + response.body());

        String expectedJsonTask = "[" + jsonEpic + "]";
        assertEquals(expectedJsonTask, response.body());
        assertEquals(200, response.statusCode());
    }
}
