package aio.Server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private AsynchronousSocketChannel channel;

    public ReadHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void completed(Integer t, ByteBuffer buffer) {
        buffer.flip();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        try {
            String expression = new String(bytes, "UTF-8");
            System.out.println("Server received data:" + expression);
            String result = sdf.format(new Date(Long.parseLong(expression)));

            doWrite(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void doWrite(String result) {
        byte[] bytes = result.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        writeBuffer.put(bytes);
        writeBuffer.flip();

        channel.write(writeBuffer, writeBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer t, ByteBuffer buffer) {
                if(buffer.hasRemaining()) {
                    channel.write(buffer, buffer, this);
                } else {
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    channel.read(readBuffer, readBuffer, new ReadHandler(channel));
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
