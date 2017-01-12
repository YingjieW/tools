package com.tools.ztest.nio;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Descripe: http://www.jianshu.com/p/a33f741fe450
 *
 * @author yingjie.wang
 * @since 17/1/12 上午11:43
 */
public class DatagramChannelTest {
    public static void main(String[] args) throws Exception {
        client();
        server();
    }

    private static void server() throws Exception {
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.socket().bind(new InetSocketAddress("localhost",8888));
        ByteBuffer buffer = ByteBuffer.allocate(100);
        CharBuffer charBuffer = CharBuffer.allocate(100);
        CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
        while (true){
            buffer.clear();
            charBuffer.clear();
            SocketAddress remoteAddress = channel.receive(buffer);
            if (null == remoteAddress) {
                System.out.println("remoteAddress is null, sleep 1 second.....");
                Thread.sleep(1000);
            } else {
                buffer.flip();
                decoder.decode(buffer, charBuffer, false);
                charBuffer.flip();
                System.out.println(remoteAddress + ":" + new String(charBuffer.array(), 0, charBuffer.limit()));
            }
        }
    }

    private static void client() throws Exception {
        DatagramChannel channel = DatagramChannel.open();
        String sendData = "哈哈哈 hello programmer!";
        ByteBuffer buffer = ByteBuffer.wrap(sendData.getBytes());
        channel.send(buffer, new InetSocketAddress("localhost",8888));
        System.out.println("send end!");
    }
}
