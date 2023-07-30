package logic;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final URI url;
    private String apiToken;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    public KVTaskClient(String url) {
        this.url = URI.create(url);
    }

    public String registration() {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
        HttpRequest request = requestBuilder
                .GET()
                .uri(URI.create(url + "/register"))
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "text/html")
                .build();

        try {
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            HttpResponse<String> response = httpClient.send(request, handler);

            if (response.statusCode() == 200) {
                return apiToken = response.body();
            } else throw new TokenException("Токен не зарегистрирован");

        } catch (IOException | InterruptedException e) {
            System.out.println("Произошла ошибка " + e.getMessage());
        }
        throw new TokenException("неверный токен");
    }

    public void put(String key, String stringJson) {
        if (apiToken == null) {
            throw new TokenException("Токен отсутствует.");
        }

        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(stringJson);
        URI createUrl = URI.create(url + "/save" + key + "?API_TOKEN=" + apiToken);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(body)
                .uri(createUrl)
                .version(HttpClient.Version.HTTP_1_1)
                .header("Accept", "text/html")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new KVTaskClientException("Ошибка сохранения задач.");
            }

        } catch (IOException | InterruptedException exception) {
            System.out.println("Произошла ошибка " + exception.getMessage());
        }
    }

    public String load(String key) {
        if (apiToken == null) {
            return "в запросе отсутствует API_TOKEN.";
        }

        URI rUrl = URI.create(url + "/load" + key + "?API_TOKEN=" + apiToken);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(rUrl)
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new KVTaskClientException("Ошибка загрузки задач.");
            }

            return response.body();

        } catch (IOException | InterruptedException exception) {
            System.out.println("Произошла ошибка " + exception.getMessage());
        }

        return "Неверный запрос.";
    }
}
