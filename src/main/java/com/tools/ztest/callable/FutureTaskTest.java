package com.tools.ztest.callable;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/10/2 下午1:30
 */
public class FutureTaskTest implements Callable<Integer> {
    private int num;
    public FutureTaskTest(int num) {
        this.num = num;
    }
    @Override
    public Integer call() throws Exception {
        TimeUnit.SECONDS.sleep(3);
        return this.num;
    }
    public static void main(String[] args) throws Exception {
        ExecutorService exec = Executors.newFixedThreadPool(5);
        List<FutureTask<Integer>> taskList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 1; i <= 10; i++) {
            FutureTask<Integer> task = new FutureTask<Integer>(new FutureTaskTest(i));
            taskList.add(task);
            exec.submit(task);
        }
        int sum = 0;
        for (FutureTask<Integer> task : taskList) {
            // get()方法时阻塞方法，无需调用isDone方法来判断
            sum += task.get();
        }
        System.out.println("---> cost: " + (System.currentTimeMillis() - startTime) + ", sum: " + sum);
        exec.shutdown();

        FutureTask<Integer> task = new FutureTask<Integer>(new FutureTaskTest(2));
        System.out.println(task.get());
    }

    private ConcurrentHashMap<String,FutureTask<Connection>>connectionPool = new ConcurrentHashMap<>();

    public Connection getConnection(String key) throws Exception{
        FutureTask<Connection>connectionTask=connectionPool.get(key);
        if(connectionTask != null){
            return connectionTask.get();
        } else {
            Callable<Connection> callable = new Callable<Connection>(){
                @Override
                public Connection call() throws Exception {
                    return createConnection();
                }
            };
            FutureTask<Connection> newTask = new FutureTask<Connection>(callable);
            connectionTask = connectionPool.putIfAbsent(key, newTask);
            if(connectionTask == null){
                connectionTask = newTask;
                connectionTask.run(); // → call() → createConnection()
            }
            // get()方法时阻塞的，此时如果多个持有相同key的线程走到此处，仍然需要等到get()执行完毕后才能最终拿到链接
            return connectionTask.get();
        }
    }

    private Connection createConnection(){
        return null;
    }
}
