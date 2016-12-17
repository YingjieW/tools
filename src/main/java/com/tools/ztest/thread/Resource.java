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
        // 因为,如果使用if单次验证,当消费者唤醒其它消费者时,会导致同一个产品被消费多次;
        // 所以,必须使用while循环来验证获取锁的角色是否可以执行。
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
