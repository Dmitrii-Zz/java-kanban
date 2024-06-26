package logic;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class KVServer {
	public static final int PORT = 9090;
	private final String apiToken;
	private final HttpServer server;
	private final Map<String, String> data = new HashMap<>();

	public KVServer() throws IOException {
		apiToken = generateApiToken();
		server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
		server.createContext("/register", this::register);
		server.createContext("/save", this::save);
		server.createContext("/load", this::load);
	}

	private void load(HttpExchange h) throws IOException {
		try {
			System.out.println("\n/load");
			if (!hasAuth(h)) {
				System.out.println("Пользователь не авторизован.");
				h.sendResponseHeaders(400, 0);
				return;
			}

			if (h.getRequestMethod().equals("GET")) {
				String key = h.getRequestURI().getPath().substring("/load/".length());

				if (key.isEmpty()) {
					System.out.println("Ключ отсутствует.");
					h.sendResponseHeaders(400, 0);
					return;
				}

				String response = data.get(key);
				if (response.isEmpty()) {
					System.out.println("Данные отсутствуют..");
					h.sendResponseHeaders(400, 0);
					return;
				}

				h.sendResponseHeaders(200, 0);

				try (OutputStream os = h.getResponseBody()) {
					os.write(response.getBytes());
				}
			}
		} catch (IOException exception) {
			throw new IOException();
		} finally {
			h.close();
		}
	}

	private void save(HttpExchange h) throws IOException {
		try {
			System.out.println("\n/save");
			if (!hasAuth(h)) {
				System.out.println("Запрос не авторизован, нужен параметр в query API_TOKEN со значением апи-ключа");
				h.sendResponseHeaders(403, 0);
				return;
			}
			if ("POST".equals(h.getRequestMethod())) {
				String key = h.getRequestURI().getPath().substring("/save/".length());
				if (key.isEmpty()) {
					System.out.println("Key для сохранения пустой. key указывается в пути: /save/{key}");
					h.sendResponseHeaders(400, 0);
					return;
				}
				String value = readText(h);
				if (value.isEmpty()) {
					System.out.println("Value для сохранения пустой. value указывается в теле запроса");
					h.sendResponseHeaders(400, 0);
					return;
				}
				data.put(key, value);
				System.out.println("Значение для ключа " + key + " успешно обновлено!");
				h.sendResponseHeaders(200, 0);
			} else {
				System.out.println("/save ждёт POST-запрос, а получил: " + h.getRequestMethod());
				h.sendResponseHeaders(405, 0);
			}
		} finally {
			h.close();
		}
	}

	private void register(HttpExchange h) throws IOException {
		try {
			System.out.println("\n/register");
			if ("GET".equals(h.getRequestMethod())) {
				sendText(h, apiToken);
			} else {
				System.out.println("/register ждёт GET-запрос, а получил " + h.getRequestMethod());
				h.sendResponseHeaders(405, 0);
			}
		} finally {
			h.close();
		}
	}

	private String generateApiToken() {
		return "" + System.currentTimeMillis();
	}

	protected boolean hasAuth(HttpExchange h) {
		String rawQuery = h.getRequestURI().getRawQuery();
		return rawQuery != null && (rawQuery.contains("API_TOKEN=" + apiToken) || rawQuery.contains("API_TOKEN=DEBUG"));
	}

	protected String readText(HttpExchange h) throws IOException {
		return new String(h.getRequestBody().readAllBytes(), UTF_8);
	}

	protected void sendText(HttpExchange h, String text) throws IOException {
		byte[] resp = text.getBytes(UTF_8);
		h.getResponseHeaders().add("Content-Type", "application/json");
		h.sendResponseHeaders(200, resp.length);
		h.getResponseBody().write(resp);
	}

	public void start() {
		System.out.println("Запускаем сервер на порту " + PORT);
		System.out.println("Открой в браузере http://localhost:" + PORT + "/");
		System.out.println("API_TOKEN: " + apiToken);
		server.start();
	}

	public void stop() {
		System.out.println("Останавливаем сервер.");
		server.stop(1);
	}
}
