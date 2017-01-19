package com.tools.ztest.rmi;

import java.rmi.registry.LocateRegistry;

/**
 * Descripe: http://blog.csdn.net/xinghun_4/article/details/45787549
 *
 * @author yingjie.wang
 * @since 17/1/19 下午11:27
 */
/* 注册远程对象,向客户端提供远程对象服务
 * 远程对象是在远程服务上创建的，你无法确切地知道远程服务器上的对象的名称
 * 但是，将远程对象注册到RMI Service之后，客户端就可以通过RMI Service请求
 * 到该远程服务对象的stub了，利用stub代理就可以访问远程服务对象了
 */
public class HelloServer {

    public static void main(String[] args) {
        try {
            /** 生成stub和skeleton,并返回stub代理引用 */
            IHello hello = new HelloImpl();

            /**
             * 本地创建并启动RMI Service，被创建的Registry服务将在指定的端口上侦听到来的请求
             * 实际上，RMI Service本身也是一个RMI应用，我们也可以从远端获取Registry:
             * public interface Registry extends Remote;
             * public static Registry getRegistry(String host, int port) throws RemoteException;
             */
            LocateRegistry.createRegistry(1099);

            /** 将stub代理绑定到Registry服务的URL上 */
            java.rmi.Naming.rebind("rmi://localhost:1099/hello", hello);

            System.out.print("Ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
