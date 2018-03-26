package bio;

import bio.Client.Client;

import java.util.Date;

public class MainForClient1 {
    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    long expression = new Date().getTime();
                    Client.send(expression);
                }
            }
        }).start();
    }
}
