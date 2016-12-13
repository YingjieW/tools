package com.tools.ztest.redis;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/12 下午4:12
 */
public class RedisIDGeneratorThread extends Thread {

    private RedisIDGenerator redisIdGenerator;

    private String name;

    private boolean stop = false;

    public RedisIDGeneratorThread(RedisIDGenerator redisIdGenerator, String name) {
        super(name);
        this.name = name;
        this.redisIdGenerator = redisIdGenerator;
    }

    public void stopThread() {
        System.out.println("===> Stop thread - " + name);
        this.stop = true;
    }

    @Override
    public void run() {
        System.out.println("===> " + name + " start.....");
        while (!stop) {
            String id = redisIdGenerator.getAndIncrement();
            if (null != id) {
                if (redisIdGenerator.idExists(id)) {
                    System.out.println("---> [" + name + "] duplicate id generated, id: " + id);
                    this.stop = true;
                    break;
                }
                if (Integer.valueOf(id) > 30) {
                    System.out.println("---> [" + name + "] greater than 30, stop, id: " + id);
                    this.stop = true;
                    break;
                }
                redisIdGenerator.add(id);
            }
        }
        System.out.println("**** " + name + " end.....");
    }
}
