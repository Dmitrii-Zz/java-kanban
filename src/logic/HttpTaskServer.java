package logic;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import task.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

public class HttpTaskServer {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final int PORT = 8080;
    private static final int ID_TASK = 1;
    private final Gson gson = new Gson();
    private final HttpServer server;
    private String response;
    private String jsonTask;
    private int code;
    private final HttpTaskManager manager = Managers.getDefault("http://localhost:9090");

    public HttpTaskServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/tasks", this::ServerGetTaskSortPriority);
        server.createContext("/tasks/task", this::serverTask);
        server.createContext("/tasks/epic", this::serverEpic);
        server.createContext("/tasks/subtask", this::serverSubtask);
        server.createContext("/tasks/history", this::serverGetHistory);
    }

    public void start() {
        server.start();
        System.out.println("Сервер запущен на " + PORT + " порту.");
    }

    public void stop() {
        server.stop(1);
    }

    private void serverTask(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET":
                serverGetTask(exchange);
                break;
            case "POST":
                serverPostTask(exchange);
                break;
            case "DELETE":
                serverDeleteTask(exchange);
                break;
            default:
                sendResponseMethod(exchange);
        }
    }

    private void serverEpic(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET":
                serverGetEpic(exchange);
                break;
            case "POST":
                serverPostEpic(exchange);
                break;
            case "DELETE":
                serverDeleteEpic(exchange);
                break;
            default:
                sendResponseMethod(exchange);
        }
    }

    private void serverSubtask(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET":
                serverGetSubtask(exchange);
                break;
            case "POST":
                serverPostSubtask(exchange);
                break;
            case "DELETE":
                serverDeleteSubtask(exchange);
                break;
            default:
                sendResponseMethod(exchange);
        }
    }

    private void ServerGetTaskSortPriority(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("GET")) {
            response = "Ожидаем GET, а получили " + exchange.getRequestMethod();
            code = 400;
            sendRespond(exchange);
        }

        try {
            response = gson.toJson(manager.getTaskSortPriority());
            code = 200;
            sendRespond(exchange);
        } catch (HttpServerException e) {
            throw new HttpServerException("Ошибка получения приоритетных задач.");
        }
    }

    private void serverGetHistory(HttpExchange exchange) throws IOException {
        if (!exchange.getRequestMethod().equals("GET")) {
            response = "Ожидаем GET, а получили " + exchange.getRequestMethod();
            code = 400;
            sendRespond(exchange);
        }

        try {
            response = gson.toJson(manager.getHistory());
            code = 200;
            sendRespond(exchange);
        } catch (HttpServerException e) {
            throw new HttpServerException("Ошибка получения истории задач.");
        }
    }

    private void serverGetTask(HttpExchange exchange) throws IOException {
        try {
            String idTask = exchange.getRequestURI().getQuery();

            if (idTask != null) {
                response = gson.toJson(manager.getTask(Integer.parseInt(idTask.split("=")[ID_TASK])));
            } else {
                response = gson.toJson(manager.getAllTask());
            }

            if (!response.equals("null")) {
                code = 200;
            } else {
                code = 400;
                response = "Задачи с таким ИД не существует";
            }

            sendRespond(exchange);

        } catch (HttpServerException e) {
            throw new HttpServerException("Ошибка получения задачи.");
        } catch (NumberFormatException e) {
            response = "Введен нечисловой ИД";
            code = 400;
            sendRespond(exchange);
            throw new HttpServerException("Ошибка получения ИД задачи.");
        }
    }

    private void serverGetEpic(HttpExchange exchange) throws IOException {
        try {
            String idTask = exchange.getRequestURI().getQuery();

            if (idTask != null) {
                response = gson.toJson(manager.getEpicId(Integer.parseInt(idTask.split("=")[ID_TASK])));
            } else {
                response = gson.toJson(manager.getAllEpic());
            }

            if (!response.equals("null")) {
                code = 200;
            } else {
                code = 400;
                response = "Задачи с таким ИД не существует.";
            }

            sendRespond(exchange);

        } catch (HttpServerException e) {
            throw new HttpServerException("Ошибка получения эпика.");
        } catch (NumberFormatException e) {
            response = "Введен нечисловой ИД";
            code = 400;
            sendRespond(exchange);
            throw new HttpServerException("Ошибка получения ИД эпика.");
        }
    }

    private void serverGetSubtask(HttpExchange exchange) throws IOException {
        try {
            String idTask = exchange.getRequestURI().getQuery();

            if (idTask != null) {
                response = gson.toJson(manager.getSubTaskId(Integer.parseInt(idTask.split("=")[ID_TASK])));
            } else {
                response = gson.toJson(manager.getAllSubTask());
            }

            if (!response.equals("null")) {
                code = 200;
            } else {
                code = 400;
                response = "Задачи с таким ИД не существует.";
            }

            sendRespond(exchange);

        } catch (HttpServerException e) {
            throw new HttpServerException("Ошибка получения подзадачи.");
        } catch (NumberFormatException e) {
            response = "Введен нечисловой ИД";
            code = 400;
            sendRespond(exchange);
            throw new HttpServerException("Ошибка получения ИД подзадачи.");
        }
    }

    private void serverPostTask(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        jsonTask = new String (inputStream.readAllBytes(), DEFAULT_CHARSET);
        Task task = gson.fromJson(jsonTask, Task.class);

        if (Pattern.matches("/tasks/task/update$", exchange.getRequestURI().getPath())) {
            manager.updateTask(task);
        } else {
            manager.createTask(task);
        }

        code = 201;
        response = "Задача добавлена";
        sendRespond(exchange);
    }

    private void serverPostEpic(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        jsonTask = new String (inputStream.readAllBytes(), DEFAULT_CHARSET);
        Epic epic = gson.fromJson(jsonTask, Epic.class);

        if (Pattern.matches("/tasks/epic/update$", exchange.getRequestURI().getPath())) {
            manager.updateEpic(epic);
        } else {
            manager.createEpic(epic);
        }

        code = 201;
        response = "Задача добавлена";
        sendRespond(exchange);
    }

    private void serverPostSubtask(HttpExchange exchange) throws IOException {
        InputStream inputStream = exchange.getRequestBody();
        jsonTask = new String (inputStream.readAllBytes(), DEFAULT_CHARSET);
        Subtask subtask = gson.fromJson(jsonTask, Subtask.class);

        if (Pattern.matches("/tasks/subtask/update$", exchange.getRequestURI().getPath())) {
            manager.updateSubTask(subtask);
        } else {
            manager.createSubTask(subtask);
        }

        code = 201;
        response = "Задача добавлена";
        sendRespond(exchange);
    }

    private void serverDeleteTask(HttpExchange exchange) throws IOException {
        try {
            String idTask = exchange.getRequestURI().getQuery();

            if (idTask != null) {
                manager.deleteTaskId(Integer.parseInt(idTask.split("=")[ID_TASK]));
                response = "Задача удалена.";
            } else {
                manager.deleteTask();
                response = "Все задачи удалены.";
            }

            code = 200;
            sendRespond(exchange);

        } catch (HttpServerException e) {
            throw new HttpServerException("Произошла ошибка удаления задачи.");
        } catch (NumberFormatException e) {
            response = "Введен нечисловой ИД.";
            code = 400;
            sendRespond(exchange);
            throw new HttpServerException("Ошибка получения ИД задачи.");
        }
    }

    private void serverDeleteEpic(HttpExchange exchange) throws IOException {
        try {
            String idTask = exchange.getRequestURI().getQuery();

            if (idTask != null) {
                manager.deleteEpicId(Integer.parseInt(idTask.split("=")[ID_TASK]));
                response = "Задача удалена.";
            } else {
                manager.deleteEpic();
                response = "Все задачи удалены.";
            }

            code = 200;
            sendRespond(exchange);

        } catch (HttpServerException e) {
            throw new HttpServerException("Произошла ошибка удаления эпика.");
        } catch (NumberFormatException e) {
            response = "Введен нечисловой ИД";
            code = 400;
            sendRespond(exchange);
            throw new HttpServerException("Ошибка получения ИД эпика.");
        }
    }

    private void serverDeleteSubtask(HttpExchange exchange) throws IOException {
        try {
            String idTask = exchange.getRequestURI().getQuery();

            if (idTask != null) {
                manager.deleteSubTaskId(Integer.parseInt(idTask.split("=")[ID_TASK]));
                response = "Задача удалена.";
            } else {
                manager.deleteSubTask();
                response = "Все задачи удалены.";
            }

            code = 200;
            sendRespond(exchange);

        } catch (HttpServerException e) {
            throw new HttpServerException("Ошибка удаления подзадачи.");
        } catch (NumberFormatException e) {
            response = "Введен нечисловой ИД";
            code = 400;
            sendRespond(exchange);
            throw new HttpServerException("Ошибка получения ИД подзадачи.");
        }
    }

    private void sendRespond(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(code, 0);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void sendResponseMethod(HttpExchange exchange) throws IOException {
        response = "Ожидаем GET, POST или DELETE, а получили " + exchange.getRequestMethod();
        code = 400;
        sendRespond(exchange);
    }
}