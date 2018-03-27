package nio.Client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class Client {
    private static int DEFAULT_PORT = 12345;
    private static InetAddress DEFAULT_SERVER_IP;

    static {
        try {
            DEFAULT_SERVER_IP = InetAddress.getByName(null);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static ClientHandler clientHandler;

    public static void start() {
        start(DEFAULT_SERVER_IP, DEFAULT_PORT);
    }

    public static synchronized void start(InetAddress ip, int port) {
        if (clientHandler != null) {
            clientHandler.stop();
        }
        clientHandler = new ClientHandler(ip, port);
        new Thread(clientHandler, "Client").start();
    }

    public static boolean sendMsg(String msg) throws Exception {
        if ("q".equals(msg)) return false;
        clientHandler.sendMsg(msg);
        return true;
    }

    public static void main(String[] args) throws Exception {
        start();
        Thread.sleep(1000);
        while (sendMsg(Long.toString(new Date().getTime()))){
            Thread.sleep(5000);
        };
    }
}