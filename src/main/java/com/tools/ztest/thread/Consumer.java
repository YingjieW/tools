package com.tools.ztest.thread;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/24 下午6:46
 */
public class Consumer implements Runnable {

    private Resource resource;

    Consumer(Resource resource) {
        this.resource = resource;
    }

    @Override
    public void run() {
        while (true) {
            resource.consume();
        }
    }
}
