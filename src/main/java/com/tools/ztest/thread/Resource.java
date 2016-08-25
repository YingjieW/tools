package com.tools.ztest.thread;

/**
 * Descripe: http://blog.csdn.net/javazejian/article/details/50878665
 *
 * @author yingjie.wang
 * @since 16/8/24 下午6:31
 */
public class Resource {

    private String name;

    // 生产者初始生产的总量
    private int sum = 0;

    // 产品剩余值(也即 当前可用产品)
    private int remainder = 0;

    // 生产者方法
    public synchronized void produce(String name) {
        // 当已经有产品时,生产者需要等待
        // 注意: 使用while循环进行验证,而不要使用if单次验证;
        // 因为,如果使用if单次验证,则其中被唤醒的线程获取到锁后,可能会执行多次,消费多次。
        while(remainder > 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        sum++;
        remainder++;
        this.name = name + sum;
        System.out.println(Thread.currentThread().getName() + "...Producer..." + this.name);
        notifyAll();
    }

    // 消费者方法
    public synchronized void consume() {
        // 当没有产品时,消费者需要等待
        while (remainder == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "...Consumer......" + this.name);
        remainder--;
        notifyAll();
    }
}
