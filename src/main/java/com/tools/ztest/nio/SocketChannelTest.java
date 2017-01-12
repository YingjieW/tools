package com.tools.ztest.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Descripe: http://www.jianshu.com/p/a33f741fe450
 *
 * @author yingjie.wang
 * @since 17/1/12 上午10:50
 */
public class SocketChannelTest {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost", 8080));
        ByteBuffer buffer = ByteBuffer.allocate(100);
        CharBuffer charBuffer = CharBuffer.allocate(100);
        CharsetDecoder decoder = Charset.defaultCharset().newDecoder();
        socketChannel.read(buffer);
        buffer.flip();
        decoder.decode(buffer,charBuffer,false);
        charBuffer.flip();
        while (charBuffer.hasRemaining()){
            System.out.println(charBuffer.get());
        }
        socketChannel.close();
    }
}
