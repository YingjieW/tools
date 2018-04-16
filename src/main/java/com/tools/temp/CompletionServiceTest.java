package com.tools.temp;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 18/4/15 下午9:21
 */
public class CompletionServiceTest {

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        // 构建完成服务
        CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executor);
        //构建10个生成者
        for(int i=0;i<10;i++){
            completionService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int ran=0;
                    ran = new Random().nextInt(1000);
                    Thread.sleep(ran);
                    System.out.println(Thread.currentThread().getName()+" Produce:"+ran);
                    return ran;
                }
            });
        }
        //利用主线程作为一个消费者
        for(int i=0;i<10;i++){
            try {
                int result = completionService.take().get();
                System.out.println(Thread.currentThread().getName()+" Comsumer:"+result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }
}
