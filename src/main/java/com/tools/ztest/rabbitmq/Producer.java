package com.tools.ztest.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * https://www.rabbitmq.com/api-guide.html
 *
 * @author yingjie.wang
 * @date 16/4/7 下午6:22
 */
public class Producer {

    private final static String EXCHANGE = "com.tools.ztest.rabbitmq.exchange";

    private final static String ROUTING_KEY = "com.tools.ztest.rabbitmq.routingkey";

    private final static String EXCHANGE_TEST = "com.tools.ztest.rabbitmq.exchangeTest";

    private final static String ROUTING_KEY_TEST = "com.tools.ztest.rabbitmq.routingkeyTest";

    private final static String QUEUE_TEST = "com.tools.ztest.rabbitmq.queueTest";

    private static long counter = 0l;

    /**
     * exchange/routingKey/queue的关系生成及绑定有如下两种方式可以完成:
     * 方法一: 在rabbitMQ控制后台配置生成
     * 方法二: 通过接口自动生成
     */
    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setVirtualHost("test");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_TEST, "direct");
        channel.queueDeclare(QUEUE_TEST,true, false, false, null);
        channel.queueBind(QUEUE_TEST, EXCHANGE_TEST, ROUTING_KEY_TEST);

        while (true) {
            String message = String.valueOf(counter++);
            channel.basicPublish(EXCHANGE_TEST, ROUTING_KEY_TEST, null, message.getBytes());
//            channel.basicPublish(EXCHANGE, ROUTING_KEY, null, message.getBytes());
//            channel.basicPublish("", ROUTING_KEY, null, message.getBytes());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        channel.close();
//        connection.close();
//
//        System.out.println("====> End Successfully!");
    }
}
