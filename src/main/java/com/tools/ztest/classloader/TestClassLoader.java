package com.tools.ztest.classloader;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/26 上午10:56
 */
public class TestClassLoader {

    private static final int DEFAULT_BUFFER_SIZE = 2048;

    public static void main(String[] args) throws Exception {
        TestClassLoader testClassLoader = new TestClassLoader();
        String filePath = "runtimecfg/rmimock.properties";
        /**
         * 因为有3种方法得到ClassLoader，对应有如下3种方法读取文件
         * 使用的路径是相对于这个ClassLoader的那个点的相对路径，此处只能使用相对路径
         */
        InputStream inputStream = null;
        // 方法1
        inputStream = testClassLoader.getClass().getClassLoader().getResourceAsStream(filePath);
        // 方法2
        inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        // 方法3
        inputStream = ClassLoader.getSystemResourceAsStream(filePath);
    }

    /**
     * print inputStream
     * @param inputStream
     * @throws Exception
     */
    private static void print(InputStream inputStream) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] dataBuffer = new byte[DEFAULT_BUFFER_SIZE];
        int count = -1;
        while((count = inputStream.read(dataBuffer,0,DEFAULT_BUFFER_SIZE)) != -1) {
            byteArrayOutputStream.write(dataBuffer, 0, count);
        }
        dataBuffer = null;
        System.out.println(new String(byteArrayOutputStream.toByteArray(),"ISO-8859-1"));
    }


}
