package javaHighConcurrentDesign.chapter5.socket.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 */
public class AioEchoClient {

    //public static void main(String[] args)   {

    //}

    public static void main(String[] args)  throws IOException{
            final AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
        client.connect(new InetSocketAddress("localhost", 8000), null, new CompletionHandler<Void, Object>() {

            @Override
            public void completed(Void result, Object attachment) {
                client.write(ByteBuffer.wrap("Hello!".getBytes()), null, new CompletionHandler<Integer, Object>() {
                    @Override
                    public void completed(Integer result, Object attachment) {
                        try {

                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                       client.read(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                           @Override
                           public void completed(Integer result, ByteBuffer buffer) {
                               buffer.flip();
                               try {
                                   client.close();
                               }catch (IOException e){
                                   e.printStackTrace();
                               }
                           }
                           @Override
                           public void failed(Throwable exc, ByteBuffer attachment) {

                           }
                       });
                     }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void failed(Throwable exc, Object attachment) {

                    }
                });
            }

            @Override
            public void failed(Throwable exc, Object attachment) {

            }
        });

    }
}
