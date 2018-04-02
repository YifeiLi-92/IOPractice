package aio.Server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncServerHandler> {
    @Override
    public void completed(AsynchronousSocketChannel channel, AsyncServerHandler serverHandler) {
        Server.clientCount++;
        System.out.println("The count of clients:" + Server.clientCount);
        serverHandler.channel.accept(serverHandler, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer, buffer, new ReadHandler(channel));
    }

    @Override
    public void failed(Throwable exc, AsyncServerHandler serverHandler) {
        exc.printStackTrace();
        serverHandler.latch.countDown();
    }
}
