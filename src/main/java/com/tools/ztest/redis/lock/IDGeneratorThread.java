package com.tools.ztest.redis.lock;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/16 下午2:47
 */
public class IDGeneratorThread extends Thread {

    private IDGenerator idGenerator;

    private String name;

    private boolean stop = false;

    public IDGeneratorThread(IDGenerator idGenerator, String name) {
        super(name);
        this.name = name;
        this.idGenerator = idGenerator;
    }

    public void stopThread() {
        System.out.println("===> Stop thread - " + name);
        this.stop = true;
    }

    @Override
    public void run() {
        System.out.println("===> " + name + " start.....");
        while (!stop) {
            long id = idGenerator.getAndIncrement();
            if (id >= 0) {
                if (idGenerator.idExists(id)) {
                    System.out.println("---> [" + name + "] duplicate id generated, id: " + id);
                    this.stop = true;
                    break;
                }
                if (id > 90) {
                    System.out.println("---> [" + name + "] stop, id: " + id);
                    this.stop = true;
                    break;
                }
                idGenerator.add(id);
            }
        }
        System.out.println("**** " + name + " end.....");
    }
}
