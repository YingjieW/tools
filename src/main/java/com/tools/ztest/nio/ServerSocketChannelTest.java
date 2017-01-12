package com.tools.ztest.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Descripe: http://www.jianshu.com/p/a33f741fe450
 *
 * @author yingjie.wang
 * @since 17/1/12 上午10:29
 */
public class ServerSocketChannelTest {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress("localhost", 8080));
        /**
         * 将serverSocketChannel设置为non-blocking
         * 如果不设置,则默认为blocking
         */
        serverSocketChannel.configureBlocking(false);
        String text = "Good morning!";
        ByteBuffer buffer = ByteBuffer.wrap(text.getBytes());
        while (true){
            System.out.println("wait for connections");
            /**
             * If this channel is in non-blocking mode then this method will
             * immediately return null if there are no pending connections.
             * Otherwise it will block indefinitely until a new connection is available
             * or an I/O error occurs.
             */
            SocketChannel clientSocket = serverSocketChannel.accept();
            if (null == clientSocket){
                try {
                    System.out.println("Sleeping 1 second .....");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Writing.....");
                // Buffer.rewind()将position设回0，所以你可以重读Buffer中的所有数据。
                // limit保持不变，仍然表示能从Buffer中读取多少个元素（byte、char等）。
                buffer.rewind();
                System.out.println(clientSocket.write(buffer));
                clientSocket.close();
            }
        }
    }
}
