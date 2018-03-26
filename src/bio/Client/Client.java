package bio.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * block i/o client
 */
public class Client {
    private static int DEFAULT_SERVER_PORT = 12345;
    private static InetAddress DEFAULT_SERVER_IP;

    static {
        try {
            DEFAULT_SERVER_IP = InetAddress.getByName(null);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void send(long expression) {
        send(DEFAULT_SERVER_PORT, expression);
    }

    public static void send(int port, long expression) {
        System.out.println("The expression is:" + expression);
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try {
            socket = new Socket(DEFAULT_SERVER_IP, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(expression);
            System.out.println("The result is:" + in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }

            if (out != null) {
                out.close();
                out = null;
            }

            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out = null;
            }
        }
    }
}
