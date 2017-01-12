package com.tools.ztest.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/11 下午11:22
 */
public class ByteBufferTest {
    public static void main(String[] args) throws Exception {
        RandomAccessFile aFile = new RandomAccessFile("/Users/YJ/Downloads/nio.txt", "rw");
        FileChannel inChannel = aFile.getChannel();
        ByteBuffer buf = ByteBuffer.allocate(48);

        int bytesRead = inChannel.write(buf);
        System.out.println(bytesRead);
        buf.flip();
        String text = "ByteBufferTest.....";
        for (byte b : text.getBytes()) {
            buf.put(b);
        }
        buf.clear();
        System.out.println(bytesRead);
        aFile.close();
    }
}
