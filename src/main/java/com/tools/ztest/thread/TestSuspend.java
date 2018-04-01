package com.tools.ztest.thread;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 18/3/30 下午8:21
 */
public class TestSuspend {
    public synchronized void print(){
        if(Thread.currentThread().getName().equals("A")){
            System.out.println("A 线程 独占该资源了");
            Thread.currentThread().suspend();
        }
    }

    public static void main(String [] args) throws Exception{
        TestSuspend testSuspend = new TestSuspend();
        Thread t1 = new Thread(){
            public void run(){
                testSuspend.print();
            }
        };
        t1.setName("A");
        t1.start();
        Thread.sleep(1000);

        Thread t2 = new Thread(){
            public void run(){
                System.out.println("B已启动,但进入不到print方法中");
                testSuspend.print();
            }
        };
        t2.setName("B");
        t2.start();
    }
}
