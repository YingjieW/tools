package com.tools.ztest.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/11 下午11:06
 */
public class FileChannelTest {
    public static void main(String[] args) throws Exception {
        RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/YJ/Downloads/nio.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);

        int bytesRead = fileChannel.read(byteBuffer);
        while (bytesRead != -1) {
            System.out.println("Start Reading: " + bytesRead);
            byteBuffer.flip();
            while(byteBuffer.hasRemaining()){
                System.out.print((char) byteBuffer.get());
            }
            byteBuffer.clear();
            bytesRead = fileChannel.read(byteBuffer);
        }

        randomAccessFile.close();
    }
}
