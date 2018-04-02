package aio.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AsyncClientHandler implements CompletionHandler<Void, AsyncClientHandler>, Runnable {
    private AsynchronousSocketChannel clientChannel;
    private InetAddress ip;
    private int port;
    private CountDownLatch latch;

    public AsyncClientHandler(InetAddress ip, int port) {
        this.ip = ip;
        this.port = port;

        try {
            clientChannel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        clientChannel.connect(new InetSocketAddress(ip, port), this, this);

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            clientChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void completed(Void result, AsyncClientHandler attachment) {
        System.out.println("The Client has connected to Server successfully.");
    }

    @Override
    public void failed(Throwable exc, AsyncClientHandler attachment) {
        System.out.println("The Client connected failed");
        exc.printStackTrace();
        try {
            clientChannel.close();
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        byte[] bytes = msg.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();

        clientChannel.write(writeBuffer, writeBuffer, new WriteHandler(clientChannel, latch));
    }
}
