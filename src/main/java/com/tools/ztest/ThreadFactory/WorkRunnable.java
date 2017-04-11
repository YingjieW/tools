package com.tools.ztest.ThreadFactory;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/4/11 上午8:46
 */
public class WorkRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("complete a task");
    }
}
