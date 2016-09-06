package com.tools.ztest.test;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/9/6 下午3:03
 */
public class TokenBucketDemo {
    public long timeStamp = System.currentTimeMillis();
    // 桶的容量
    public int capacity;
    // 令牌放入速度
    public int rate;
    // 当前令牌数量
    public int tokens;

    public boolean grant() {
        long now = System.currentTimeMillis();
        // 先添加令牌
        tokens = Math.min(capacity, tokens + (int)(now - timeStamp) * rate);
        timeStamp = now;
        if (tokens < 1) {
            // 若不到1个令牌,则拒绝
            return false;
        }
        else {
            // 还有令牌，领取令牌
            tokens -= 1;
            return true;
        }
    }
}
