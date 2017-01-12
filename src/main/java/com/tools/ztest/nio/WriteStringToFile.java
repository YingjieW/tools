package com.tools.ztest.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * Descripe: http://www.jianshu.com/p/a33f741fe450
 *
 * @author yingjie.wang
 * @since 17/1/12 上午10:17
 */
public class WriteStringToFile {
    public static void main(String[] args) throws Exception {
        String filePath = "/Users/YJ/Downloads/nio01.txt";
        String content = "hello everyone";
        Charset charset = Charset.defaultCharset();

        long writeSize = 0;
        FileOutputStream out = new FileOutputStream(filePath);
        FileChannel channel = out.getChannel();

        // 将一个现有的数组转换为缓冲区
        // wrap() 方法将一个数组包装为缓冲区。必须非常小心地进行这类操作。一旦完成包装，底层数据就可以通过缓冲区或者直接访问。
        ByteBuffer buffer = ByteBuffer.wrap(content.getBytes(charset));
        int i = 0;
        while (buffer.hasRemaining()){
            System.out.println("---> " + (i++));
            writeSize += channel.write(buffer);
        }
        channel.force(false);

        System.out.println(writeSize);
    }
}
