import logic.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Поехали!");
        new KVServer().start();
        HttpTaskServer server = new HttpTaskServer();
        server.start();
    }
}