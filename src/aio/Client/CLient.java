package aio.Client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class CLient {
    private static int DEFAULT_SERVER_PORT = 12345;
    private static InetAddress DEFAULT_SERVER_IP;

    static {
        try {
            DEFAULT_SERVER_IP = InetAddress.getByName(null);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static AsyncClientHandler clientHandler;

    public static void start() {
        start(DEFAULT_SERVER_IP, DEFAULT_SERVER_PORT);
    }

    public static synchronized void start(InetAddress ip, int port) {
        if (clientHandler != null) return;
        clientHandler = new AsyncClientHandler(ip, port);

        new Thread(clientHandler, "Client").start();
    }

    public static boolean sendMsg(String msg) {
        if ("q".equals(msg)) return false;
        clientHandler.sendMsg(msg);
        return true;
    }

    public static void main(String[] args) throws Exception {
        start();
        Thread.sleep(1000);
        while (sendMsg(Long.toString(new Date().getTime()))){
            Thread.sleep(2000);
        };
    }
}
