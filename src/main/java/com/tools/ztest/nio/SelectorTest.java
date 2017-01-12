package com.tools.ztest.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Set;

/**
 * Descripe: http://www.jianshu.com/p/a33f741fe450
 *
 * @author yingjie.wang
 * @since 17/1/12 上午11:29
 */
public class SelectorTest {
    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress("localhost",8888));
        channel.configureBlocking(false);

        SelectionKey selectionKey = channel.register(selector,SelectionKey.OP_ACCEPT);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        CharsetDecoder decoder = Charset.defaultCharset().newDecoder();

        while (true){
            int readyNum = selector.select();
            if (readyNum <= 0){
                continue;
            }
            Set<SelectionKey> readyKey = selector.selectedKeys();
            for (SelectionKey tempKey : readyKey){
                if (tempKey.isAcceptable()){
                    ServerSocketChannel tempChannel = (ServerSocketChannel) tempKey.channel();
                    SocketChannel clientChannel = tempChannel.accept();
                    if (null != clientChannel){
                        System.out.println("one connection:" + clientChannel.socket().getRemoteSocketAddress());
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector,SelectionKey.OP_READ);
                    }
                }

                if(tempKey.isReadable()){
                    SocketChannel tempChannel = (SocketChannel) tempKey.channel();
                    tempChannel.read(buffer);
                    buffer.flip();
                    decoder.decode(buffer,charBuffer,false);
                    charBuffer.flip();
                    String getData = new String(charBuffer.array(),0,charBuffer.limit());
                    System.out.println(tempChannel.socket().getRemoteSocketAddress() + ":" + getData);
                    buffer.clear();
                    charBuffer.clear();
                    tempChannel.write(ByteBuffer.allocate(0));
                    if (getData.equalsIgnoreCase("exit")){
                        tempChannel.close();
                    }
                }

                if (tempKey.isWritable()){
                    SocketChannel tempChannel = (SocketChannel) tempKey.channel();
                    System.out.println(tempChannel.socket().getRemoteSocketAddress() + ": read");
                }
                readyKey.remove(tempKey);
            }
        }
    }
}
