package bio.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerBetter {
    private static int DEFAULT_PORT = 12345;
    private static ServerSocket server;
    private static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public static void start() throws IOException {
        start(DEFAULT_PORT);
    }

    public static synchronized void start(int port) throws IOException {
        if (server != null) return;

        try {
            server = new ServerSocket(port);
            System.out.println("The server is starting, the port is:" + port);

            while (true) {
                Socket socket = server.accept();
                executorService.execute(new ServerHandler(port, socket));
            }

        } finally {
            if (server != null) {
                System.out.println("The server is closing.");
                server.close();
                server = null;
            }
        }
    }
}
