package com.tools.ztest.nio;

/**
 * Descripe: http://zalezone.cn/2014/09/17/NIO%E7%B2%BE%E7%B2%B9/
 *
 * @author yingjie.wang
 * @since 17/1/12 下午6:09
 */

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Simple echo-back server which listens for incoming stream connections and
 * echoes back whatever it reads. A single Selector object is used to listen to
 * the server socket (to accept new connections) and all the active socket channels.
 */
public class SelectSocketServer {

    public static int PORT_NUMBER = 1234;

    public static void main(String[] argv) throws Exception {
        new SelectSocketServer().go(argv);
    }

    public void go(String[] argv) throws Exception {
        int port = PORT_NUMBER;
        // 覆盖默认的监听端口
        if (argv.length > 0) {
            port = Integer.parseInt(argv[0]);
        }
        System.out.println("Listening on port " + port);

        // 创建一个Selector供下面使用
        Selector selector = Selector.open();
        // 打开一个未绑定的ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 得到一个ServerSocket去和它绑定
        ServerSocket serverSocket = serverSocketChannel.socket();
        // 设置server channel将会监听的端口
        serverSocket.bind(new InetSocketAddress(port));
        // 设置非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 将ServerSocketChannel注册到Selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            /**
             * This may block for a long time. Upon returning, the selected set contains
             * keys of the ready channels.
             *
             * This method performs a blocking selection operation. Selects a set of keys
             * whose corresponding channels are ready for I/O operations.
             */
            int n = selector.select();
            if (n == 0) {
                System.out.println(".... selector.select() is null.");
                continue;
            }

            Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
            while (selectionKeyIterator.hasNext()) {
                SelectionKey selectionKey = (SelectionKey) selectionKeyIterator.next();
                // 判断是否是一个连接到来
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel server =(ServerSocketChannel) selectionKey.channel();
                    SocketChannel channel = server.accept();
                    registerChannel(selector, channel,SelectionKey.OP_READ);
                    sayHello(channel);
                }
                //判断这个channel上是否有数据要读
                if (selectionKey.isReadable()) {
                    readDataFromSocket(selectionKey);
                }
                //从selected set中移除这个key，因为它已经被处理过了
                selectionKeyIterator.remove();
            }
        }
    }

    /**
     * Register the given channel with the given selector for the given
     * operations of interest
     */
    protected void registerChannel(Selector selector, SelectableChannel channel, int ops) throws Exception {
        if (channel == null) {
            return;
        }
        channel.configureBlocking(false);
        channel.register(selector, ops);
    }


    /**
     * Use the same byte buffer for all channels. A single thread is
     * servicing all the channels, so no danger of concurrent acccess.
     * (对所有的通道使用相同的缓冲区。单线程为所有的通道进行服务，所以并发访问没有风险)
     */
    private ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

    /**
     * A SelectionKey object associated with a channel determined by
     * the selector to be ready for reading. If the channel returns
     * an EOF condition, it is closed here, which automatically
     * invalidates the associated key. The selector will then
     * de-register the channel on the next select call.
     * (一个选择器决定了和通道关联的SelectionKey object是准备读状态。如果通道返回EOF，通道将被关闭。
     *  并且会自动使相关的key失效，选择器然后会在下一次的select call时取消掉通道的注册)
     */
    protected void readDataFromSocket(SelectionKey key) throws Exception {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int count;
        byteBuffer.clear();
        // Loop while data is available; channel is nonblocking
        while ((count = socketChannel.read(byteBuffer)) > 0) {
            byteBuffer.flip();
            // Send the data; don't assume it goes all at once
            while (byteBuffer.hasRemaining()) {
                socketChannel.write(byteBuffer);
            }
            /**
             * WARNING: the above loop is evil. Because it's writing back to the same
             * nonblocking channel it read the data from, this code can potentially
             * spin in a busy loop. In real life you'd do something more useful than this.
             */
            byteBuffer.clear();
        }
        if (count < 0) {
            // Close channel on EOF, invalidates the key
            socketChannel.close();
        }
    }

    /**
     * Spew a greeting to the incoming client connection.
     * The newly connected SocketChannel to say hello to.
     */
    private void sayHello(SocketChannel channel) throws Exception {
        byteBuffer.clear();
        byteBuffer.put("Hi there!\r\n".getBytes());
        byteBuffer.flip();
        channel.write(byteBuffer);
    }
}
