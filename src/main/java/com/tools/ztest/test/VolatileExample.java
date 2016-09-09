package com.tools.ztest.test;

/**
 * Descripe: http://www.jianshu.com/p/bb57d1f2ef0e
 *
 * @author yingjie.wang
 * @since 16/9/8 下午3:54
 */
public class VolatileExample extends Thread{
    private static int marker = 0;
    // 设置类静态变量,各线程访问这同一共享变量
    private  static volatile boolean flag = false;
    // 无限循环,等待flag变为true时才跳出循环
    public void run() {
        while (!flag){};
        System.out.println("已停止");
    }

    public static void main(String[] args) throws Exception {
        new VolatileExample().start();
        // sleep的目的是等待线程启动完毕,也就是说进入run的无限循环体了
        Thread.sleep(100);
        flag = true;
    }
}
