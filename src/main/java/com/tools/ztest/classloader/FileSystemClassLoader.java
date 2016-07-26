package com.tools.ztest.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/26 上午11:35
 */
public class FileSystemClassLoader extends ClassLoader {

    // 本地缓存
    private static final Map<String, Class> cachedClass = new HashMap<String, Class>(128);

    private final String CLASS_SUFFIX = ".class";

    private final int DEFAULT_BUFFER_SIZE = 4096;

    private String directory;

    public FileSystemClassLoader(String directory) {
        this.directory = directory;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 获取name指定类的字节流
        byte[] classBytes = getClassBytes(name);
        if(classBytes == null) {
            throw new ClassNotFoundException(name);
        }

        // defineClass方法是ClassLoader中自带方法
        // 其主要作用是把字节数组b中的内容转换成Java类, 返回的结果是java.lang.Class类的实例。
        Class clazz = defineClass(name, classBytes, 0, classBytes.length);

        // 添加到本地缓存
        if(clazz != null) {
            cachedClass.put(name, clazz);
        }

        return clazz;
    }

    /**
     * 获取类的字节流
     * @param name
     * @return
     */
    private byte[] getClassBytes(String name) {
        String location = getClassLocation(name);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(location);
            byte[] byteBuffer = new byte[DEFAULT_BUFFER_SIZE];
            int count = -1;
            while ((count = fileInputStream.read(byteBuffer, 0, DEFAULT_BUFFER_SIZE)) != -1) {
                byteArrayOutputStream.write(byteBuffer, 0, count);
            }
            byteArrayOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private String getClassLocation(String name) {
        return this.directory + File.separator + name.replace('.', File.separatorChar) + CLASS_SUFFIX;
    }

    public static void main(String[] args) throws Exception {
        String classLoaderPath = "/com/tools/ztest/classloader";
        String className = "com.tools.ztest.classloader.StaticClass";

        FileSystemClassLoader fileSystemClassLoader1 = new FileSystemClassLoader(classLoaderPath);
        FileSystemClassLoader fileSystemClassLoader2 = new FileSystemClassLoader(classLoaderPath);

        Class class1 = fileSystemClassLoader1.loadClass(className);
        Class class2 = fileSystemClassLoader2.loadClass(className);
        Class class3 = Thread.currentThread().getContextClassLoader().loadClass(className);

        System.out.println("class1: " + class1);
        System.out.println("class2: " + class2);
        System.out.println("class3: " + class3);

        System.out.println("class1 == class2 : " + (class1 == class2));
        System.out.println("class2 == class3 : " + (class2 == class3));

        //取得应用(系统)类加载器
        URLClassLoader appClassLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();

        System.out.println(appClassLoader);
        System.out.println("应用(系统)类加载器 的加载路径: ");

        URL[] urls = appClassLoader.getURLs();
        for(URL url : urls)
            System.out.println(url);

        System.out.println("----------------------------");
    }
}
