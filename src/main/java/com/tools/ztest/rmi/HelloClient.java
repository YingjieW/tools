package com.tools.ztest.rmi;

import java.rmi.Naming;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/19 下午11:30
 */
public class HelloClient {

    public static void main(String[] args) {
        try {
            /**
             * 从RMI Registry中请求stub
             * 如果RMI Service就在本地机器上，URL就是：rmi://localhost:1099/hello
             * 否则，URL就是：rmi://RMIService_IP:1099/hello
             */
            IHello hello = (IHello) Naming.lookup("rmi://localhost:1099/hello");

            /** 通过stub调用远程接口实现 */
            System.out.println(hello.sayHello("Tomcat"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
