package nio;

import nio.Client.Client;

import java.util.Date;

public class MainForClient {
    public static void main(String[] args) throws Exception {
        Client.start();
        Thread.sleep(1000);
        while (Client.sendMsg(Long.toString(new Date().getTime()))){
            Thread.sleep(3000);
        };
    }
}
