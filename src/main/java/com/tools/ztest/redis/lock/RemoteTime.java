package com.tools.ztest.redis.lock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/16 上午11:10
 */
public class RemoteTime {

    private static final String TIME_CMD = "date";

    private String host;

    private int port;

    private SocketAddress socketAddress;

    private SocketChannel socketChannel;

    public RemoteTime(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        this.socketAddress = new InetSocketAddress(host, port);
        this.socketChannel = SocketChannel.open(socketAddress);
        this.socketChannel.configureBlocking(true);
    }

    public long getTimeMillis() {
        try {
            socketChannel.write(ByteBuffer.wrap(TIME_CMD.getBytes()));
            ByteBuffer buf = ByteBuffer.allocate(64);
            socketChannel.read(buf);

            buf.flip(); // flip for use of read
            byte[] bytes = new byte[buf.limit() - buf.position()];
            System.arraycopy(buf.array(), buf.position(), bytes, 0, bytes.length);

            return Long.parseLong(new String(bytes));
        } catch (Throwable t) {
            throw new RuntimeException(t.getMessage());
        }
    }


    public static void main(String[] args) throws Throwable {
        // 好像不是这么用的,测试失败 /(ㄒoㄒ)/~~
        RemoteTime remoteTime = new RemoteTime("127.0.0.1", 6379);
//        RemoteTime remoteTime = new RemoteTime("10.151.30.3", 8062);
        System.out.println(remoteTime.getTimeMillis());
    }
}
