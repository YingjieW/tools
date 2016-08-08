package com.tools.ztest.test;

import com.tools.utils.FileUtils;

import java.io.*;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/5 下午5:10
 */
public class TestJavaIO {

    public static void main(String[] args) throws Exception {
        testPipedStream();
    }

    private static void testFileReader() throws Exception {
        String path = "/Users/YJ/Downloads/hello.txt";
        File file = FileUtils.createFile(path, false);
        FileReader fileReader = new FileReader(file);
        // 使用FileReader进行读取时,只能一个一个字符的读取,故此处采用char[]处理。
        char[] chars = new char[1024];
        int temp = 0;
        int i = 0;
        while((temp = fileReader.read()) != -1) {
            chars[i++] = (char) temp;
        }
        String text = new String(chars, 0, i);
        System.out.println("text: " + text);
        fileReader.close();
    }

    private static void testFileWriter() throws Exception {
        String path = "/Users/YJ/Downloads/hello.txt";
        File file = FileUtils.createFile(path, false);
        String text = "Hello你好";
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(text);
        // FileWriter除了直接写入字符串,还可以写入char[]
        fileWriter.close();
    }

    private static void testFileOutputStream() throws Exception {
        String path = "/Users/YJ/Downloads/hello.txt";
        File file = FileUtils.createFile(path, false);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        // 如果在文件已经存在时,添加的文件尾部,而不是复写,则可以这样声明
//        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        // 也可以使用new FileOutputStream(path),但不推荐这样做
        String text = "Hello你好";
        byte[] bytes = text.getBytes();
        fileOutputStream.write(bytes);
        fileOutputStream.close();
    }

    private static void testFileInputStream() throws IOException {
        String path = "/Users/YJ/Downloads/hello.txt";
        File file = FileUtils.createFile(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[1024];
        int length = fileInputStream.read(bytes);
        // 也可以使用下面这种方式进行读取操作
//        int length = (int) file.length();
//        byte[] bytes = new byte[length];
//        for(int i = 0; i < bytes.length; i++) {
//            bytes[i] = (byte)fileInputStream.read();
//        }
        String text = new String(bytes, 0, length);
        System.out.println("text: " + text);
        fileInputStream.close();
    }


    private static void testPipedStream() throws Exception {
        SendThread sendThread = new SendThread();
        ReceiveThread receiveThread = new ReceiveThread();
        try {
            sendThread.getPipedOutputStream().connect(receiveThread.getPipedInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Thread(sendThread).start();
        new Thread(receiveThread).start();
    }
}

class SendThread implements Runnable {

    PipedOutputStream pipedOutputStream;

    public SendThread() {
        pipedOutputStream = new PipedOutputStream();

    }

    public PipedOutputStream getPipedOutputStream() {
        return this.pipedOutputStream;
    }

    @Override
    public void run() {
        System.out.println("SendThread -------------------------  Start");
        String text = "Hello你好测试";
        System.out.println("SendThread - write: " + text);
        try {
            this.pipedOutputStream.write(text.getBytes());
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                this.pipedOutputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        System.out.println("SendThread -------------------------  End");
    }
}

class ReceiveThread implements Runnable {

    PipedInputStream pipedInputStream;

    public ReceiveThread() {
        this.pipedInputStream = new PipedInputStream();
    }

    public PipedInputStream getPipedInputStream() {
        return this.pipedInputStream;
    }

    @Override
    public void run() {
        System.out.println("ReceiveThread -------------------------  Start");
        byte[] bytes = new byte[1024];
        int length = 0;
        try {
            length = pipedInputStream.read(bytes);
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                this.pipedInputStream.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        System.out.println("ReceiveThread - read: " + new String(bytes, 0, length));
        System.out.println("ReceiveThread ------------------------- End");
    }
}
