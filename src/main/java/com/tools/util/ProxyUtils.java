package com.tools.util;

import com.tools.ztest.proxy.jdk.JdkRealSubjectX;
import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Descripe: 模拟生成JDK动态代理生成代理类
 *
 * @author yingjie.wang
 * @since 16/8/25 下午4:11
 */
public class ProxyUtils {

    public static void generateClassFile(Class clazz) {
        String proxyName = clazz.getName() + "Proxy";
        //根据类信息和提供的代理类名称，生成字节码
        byte[] classFile = ProxyGenerator.generateProxyClass(proxyName, clazz.getInterfaces());
        String paths = clazz.getResource(".").getPath();
        System.out.println("...paths: " + paths);
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(paths + proxyName + ".class");
            out.write(classFile);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Class clazz = JdkRealSubjectX.class;
        ProxyUtils.generateClassFile(clazz);
    }
}
