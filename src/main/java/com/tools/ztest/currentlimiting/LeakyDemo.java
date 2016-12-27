package com.tools.ztest.currentlimiting;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/9/6 下午2:55
 */
public class LeakyDemo {
    public long timeStamp = System.currentTimeMillis();
    // 桶的容量
    public int capacity;
    // 水漏出的速度
    public int rate;
    // 当前水量(当前累积请求数)
    public int water;

    public boolean grant() {
        long now = System.currentTimeMillis();
        // 先执行漏水，计算剩余水量
        water = Math.max(0, water - (int)(now - timeStamp) * rate);
        timeStamp = now;
        if ((water + 1) < capacity) {
            // 尝试加水,并且水还未满
            water += 1;
            timeStamp = now;
            return true;
        }
        else {
            // 水满，拒绝加水
            return false;
        }
    }
}
