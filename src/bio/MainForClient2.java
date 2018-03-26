package bio;

import bio.Client.Client;

import java.util.Date;

public class MainForClient2 {
    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    long expression = new Date().getTime();
                    //String expression = "This is the second client.";
                    Client.send(23456,expression);

                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
