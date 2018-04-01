package com.tools.ztest.thread;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 18/3/30 下午7:11
 */
public class TestStop {
    private String first = "ja";
    private String second = "va";

    public synchronized void print(String first, String second) throws Exception{
        this.first = first;
        Thread.sleep(10000);
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public static void main(String[] args) throws Exception {
        TestStop testStop = new TestStop();
        Thread t1 = new Thread(){
            public void run(){
                try {
                    testStop.print("1", "2");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        t1.start();
        Thread.sleep(1000);
        t1.stop();
        System.out.println("first : " + testStop.getFirst() + " " + "second : " + testStop.getSecond());
    }
}
