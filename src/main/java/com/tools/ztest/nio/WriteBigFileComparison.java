package com.tools.ztest.nio;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: https://my.oschina.net/feichexia/blog/212318
 *
 * @author yingjie.wang
 * @since 17/6/27 下午5:13
 */
public class WriteBigFileComparison {
    // data chunk be written per time
    private static final int DATA_CHUNK = 128 * 1024 * 1024;

    // total data size is 2G
    private static final long LEN = 2L * 1024 * 1024 * 1024L;
//    private static final long LEN = 200 * 1024 * 1024L;

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

    public static void writeWithBIO(int itemNum) throws IOException {
        File file = new File("/Users/YJ/Downloads/test/bio.txt");
        if (file.exists()) {
            file.delete();
        }
        file.getParentFile().mkdirs();
        file.createNewFile();

//        String item = "`2017-06-19 17:41:21,`11170619173918806008,`cTrx1497865318103,`1111111110000216,`TRADE,`0.10,`0.05,`,`,`,`WECHAT_SCAN,`,`HELLOWORLD";
//        List<String> itemList = new ArrayList<>();
//        for (int i = 0; i < itemNum; i++) {
//            itemList.add(item);
//        }

        FileOutputStream fos = new FileOutputStream(file, true);
        OutputStreamWriter osw = new OutputStreamWriter(fos, CHARSET);
        BufferedWriter bw = new BufferedWriter(osw);
        for (int i = 0; i < 50000 * itemNum; i++) {
//            StringBuilder sb = new StringBuilder();
//            for (String str : itemList) {
//                sb.append(str).append(File.separator);
//            }
//            bw.write(sb.toString());
            bw.write(CONTENT);
            bw.flush();
        }
        fos.close();
        osw.close();
        bw.close();
    }

//    ===> FileChannel cost: 21095
//    ===> BIO cost: 17669

    /**
     * write big file with MappedByteBuffer
     * @throws IOException
     */
    public static void writeWithMappedByteBuffer(int itemNum) throws IOException {
        File file = new File("/Users/YJ/Downloads/test/nio.txt");
        if (file.exists()) {
            file.delete();
        }
        file.getParentFile().mkdirs();
        file.createNewFile();

        String item = "`2017-06-19 17:41:21,`11170619173918806008,`cTrx1497865318103,`1111111110000216,`TRADE,`0.10,`0.05,`,`,`,`WECHAT_SCAN,`,`HELLOWORLD";
        List<String> itemList = new ArrayList<>();
        for (int i = 0; i < itemNum; i++) {
            itemList.add(item);
        }

        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel fileChannel = raf.getChannel();
        int pos = 0;
        MappedByteBuffer mbb = null;
        byte[] data = null;
        int batch = 50000;
        for (int i = 0; i < batch; i++) {
            StringBuilder sb = new StringBuilder();
            for (String str : itemList) {
                sb.append(str).append(File.separator);
            }
            data = sb.toString().getBytes("utf-8");
//            System.out.println("write a data chunk: " + (data.length / 1024) + " KB");
            mbb = fileChannel.map(FileChannel.MapMode.READ_WRITE, pos, data.length);
            mbb.put(data);
            pos += data.length;
            data = null;
        }
        data = null;
        unmap(mbb);   // release MappedByteBuffer
        fileChannel.close();
    }

    /**
     * 在MappedByteBuffer释放后再对它进行读操作的话就会引发jvm crash，在并发情况下很容易发生
     * 正在释放时另一个线程正开始读取，于是crash就发生了。所以为了系统稳定性释放前一般需要检
     * 查是否还有线程在读或写
     * @param mappedByteBuffer
     */
    public static void unmap(final MappedByteBuffer mappedByteBuffer) {
        try {
            if (mappedByteBuffer == null) {
                return;
            }
            mappedByteBuffer.force();
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    try {
                        Method getCleanerMethod = mappedByteBuffer.getClass().getMethod("cleaner", new Class[0]);
                        getCleanerMethod.setAccessible(true);
                        sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(mappedByteBuffer, new Object[0]);
                        cleaner.clean();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("clean MappedByteBuffer completed");
                    return null;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        List<Long> costTimeList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            long startTime = System.currentTimeMillis();
//            writeWithMappedByteBuffer(300);
            writeWithBIO(300);
            costTimeList.add((System.currentTimeMillis() - startTime));
        }
        System.out.println(costTimeList);
        System.out.println(costTimeList.stream().reduce(Long.MAX_VALUE, Long::min));
        System.out.println(costTimeList.stream().reduce(Long.MIN_VALUE, Long::max));
        System.out.println(costTimeList.stream().mapToLong(Long::longValue).sum()/costTimeList.size());
    }
}
