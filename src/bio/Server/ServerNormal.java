package bio.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Bio server code
 */
public final class ServerNormal {
    private static int DEFAULT_PORT = 12345;
    private static ServerSocket server;

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
                ServerHandler handler = new ServerHandler(port, socket);
                handler.run();
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
