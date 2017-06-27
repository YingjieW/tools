package com.tools.ztest.nio;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Description: https://my.oschina.net/feichexia/blog/212318
 *
 * @author yingjie.wang
 * @since 17/6/27 下午5:13
 */
public class WriteBigFileComparison {
    // data chunk be written per time
    private static final int DATA_CHUNK = 8 * 1024 * 1024;

    // total data size is 2G
//    private static final long LEN = 2L * 1024 * 1024 * 1024L;
    private static final long LEN = 200 * 1024 * 1024L;

    private static final String CHARSET = "UTF-8";
    private static final String CONTENT = "`2017-06-19 17:41:21,`11170619173918806008,`cTrx1497865318103,`1111111110000216,`TRADE,`0.10,`0.05,`,`,`,`WECHAT_SCAN,`,`HELLOWORLD\r\n";
//    private static final int MAX_NUM = 10000000;
    private static final int MAX_NUM = 1000;
    private static byte[] CONTENT_BYTES = null;
    static {
        try {
            CONTENT_BYTES = CONTENT.getBytes(CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeWithFileChannel() throws IOException {
        File file = new File("/Users/YJ/Downloads/test/nio.txt");
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = raf.getChannel();
//        for (int i = 0; i < MAX_NUM; i++) {
//            ByteBuffer buffer = ByteBuffer.wrap(CONTENT.getBytes(CHARSET));
////            ByteBuffer buffer = ByteBuffer.wrap(CONTENT_BYTES);
//            while (buffer.hasRemaining()) {
//                fileChannel.write(buffer);
//            }
//        }
        MappedByteBuffer mbb = null;
        long position = 0L;
        for (int i = 0; i < MAX_NUM; i++) {
            byte[] contendBytes = CONTENT.getBytes(CHARSET);
            mbb = fileChannel.map(FileChannel.MapMode.READ_WRITE, position, contendBytes.length);
            mbb.put(contendBytes);
            position += contendBytes.length;
        }
        fileChannel.close();
        raf.close();
    }

    public static void writeWithBIO() throws IOException {
        File file = new File("/Users/YJ/Downloads/test/bio.txt");
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fos = new FileOutputStream(file, true);
        OutputStreamWriter osw = new OutputStreamWriter(fos, CHARSET);
        BufferedWriter bw = new BufferedWriter(osw);
        for (int i = 0; i < MAX_NUM; i++) {
            bw.write(CONTENT);
            bw.flush();
        }
        fos.close();
        osw.close();
        bw.close();
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 7; i++) {
            long startTime1 = System.currentTimeMillis();
            writeWithFileChannel();
            System.out.println("===> FileChannel cost: " + (System.currentTimeMillis() - startTime1));
            long startTime2 = System.currentTimeMillis();
            writeWithBIO();
            System.out.println("===> BIO cost: " + (System.currentTimeMillis() - startTime2));
            System.out.println();
        }
    }
//    ===> FileChannel cost: 21095
//    ===> BIO cost: 17669
}
