package com.tools.ztest.redis.lock;

/**
 * Descripe: http://lixiaohui.iteye.com/blog/2320554
 *
 * @author yingjie.wang
 * @since 16/12/14 下午10:15
 */
public interface Releasable {

    /**
     * 释放资源
     */
    void release();
}
