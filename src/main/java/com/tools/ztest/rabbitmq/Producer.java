package com.tools.ztest.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Producer
 *
 * @author yingjie.wang
 * @date 16/4/7 下午6:22
 */
public class Producer {
    private final static String QUEUE_NAME = "hello_world";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("yingjie.wang");
        factory.setPassword("yingjie.wang");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        for(int i = 100; i < 127; i++) {
            String message = String.valueOf(i);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        }
//        String message = "Hello World!";
//        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
//        System.out.println(" [x] Produce '" + message + "'");

        channel.close();
        connection.close();
    }
}
