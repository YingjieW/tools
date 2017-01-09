package com.tools.ztest.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Descripe: http://blog.csdn.net/evankaka/article/details/48009645
 *
 * @author yingjie.wang
 * @since 17/1/9 下午10:19
 */
public class ProviderClient {
    public static void main(String[] args) throws Exception {
        String configLocation="spring/dubbo-provider.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        context.start();
        System.out.println("提供者服务已注册成功");
        System.out.println("请按任意键取消提供者服务");
        try {
            System.in.read();//让此程序一直跑，表示一直提供服务
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
