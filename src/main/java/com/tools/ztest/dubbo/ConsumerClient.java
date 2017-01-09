package com.tools.ztest.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/9 下午10:17
 */
public class ConsumerClient {
    public static void main(String[] args) {
        String configLocation="spring/dubbo-consumer.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
        context.start();
        ProviderService providerService = (ProviderService) context.getBean("providerService");
        System.out.println(providerService.sayHello("Yeepay"));
        System.out.println("Press any key to exit.");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
