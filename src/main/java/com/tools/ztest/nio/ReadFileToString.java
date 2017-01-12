package com.tools.ztest.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

/**
 * Descripe: http://www.jianshu.com/p/a33f741fe450
 *
 * @author yingjie.wang
 * @since 17/1/12 上午9:59
 */
public class ReadFileToString {
    public static void main(String[] args) throws Exception {
        String filePath = "/Users/YJ/Downloads/nio.txt";
        Charset charset = Charset.defaultCharset();

        FileInputStream in = new FileInputStream(filePath);
        FileChannel channel = in.getChannel();
        long fileSize = channel.size();
        int bufferSize = 1024;
        if (fileSize < 1024){
            bufferSize = (int)fileSize;
        }
        StringBuilder builder = new StringBuilder((int)(fileSize/2));
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
        CharBuffer charBuffer = CharBuffer.allocate(bufferSize/2);
        CharsetDecoder decoder = charset.newDecoder();

        while (channel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            CoderResult coderResult;
            do {
                coderResult = decoder.decode(byteBuffer,charBuffer,false);
                charBuffer.flip();
                builder.append(charBuffer.array(),0,charBuffer.limit());
                charBuffer.clear();
            } while (coderResult.isOverflow());
            byteBuffer.compact();
        }

        byteBuffer.flip();
        decoder.decode(byteBuffer,charBuffer,true);
        charBuffer.flip();
        builder.append(charBuffer.array(),0,charBuffer.limit());
        charBuffer.clear();

        System.out.println(builder.toString());
    }
}
