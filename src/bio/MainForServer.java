package bio;

import bio.Server.ServerBetter;

import java.io.IOException;

public class MainForServer {

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //ServerNormal.start();
                    ServerBetter.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //ServerNormal.start();
                    ServerBetter.start(23456);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
    }
}
