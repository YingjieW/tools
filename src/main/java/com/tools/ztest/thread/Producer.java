package com.tools.ztest.thread;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/24 下午6:48
 */
public class Producer implements Runnable {

    private Resource resource;

    Producer(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        while (true) {
            resource.produce("TBBT");
        }
    }

    public static void main(String[] args) {
        Resource resource = new Resource();
        Producer producer = new Producer(resource);
        Consumer consumer = new Consumer(resource);

        // 两个生产者线程
        Thread producerThread1 = new Thread(producer);
        Thread producerThread2 = new Thread(producer);

        // 两个消费者线程
        Thread consumerThread1 = new Thread(consumer);
        Thread consumerThread2 = new Thread(consumer);

        // 启动线程
        producerThread1.start();
        producerThread2.start();
        consumerThread1.start();
        consumerThread2.start();
    }
}
