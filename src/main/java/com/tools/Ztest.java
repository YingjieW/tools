package com.tools;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Ztest {

    public static void main(String[] args) throws Throwable {

        LinkedBlockingQueue waitQueue = new LinkedBlockingQueue(3);

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1,
                1, TimeUnit.MINUTES, waitQueue, new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 7; i++) {
            try {
                final int sequenceNo = i;
                poolExecutor.submit(() -> {
                    LocalTime start = LocalTime.now();
                    System.out.println("-----> " + sequenceNo + " - start sleep: " + start);
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    LocalTime end = LocalTime.now();
                    System.out.println("-----> " + sequenceNo + " - end   sleep: " + end
                            + " - cost: " + (end.getSecond() - start.getSecond()));
                });
            } catch (RejectedExecutionException e) {
                System.out.println("=====> " + i + " * caught exception: " + LocalTime.now());
            }
        }

        poolExecutor.shutdown();
    }
}
/* --------------------------------------------------------------------
-----> 0 - start sleep: 18:19:07.093
=====> 4 * caught exception: 18:19:07.093
=====> 5 * caught exception: 18:19:07.094
=====> 6 * caught exception: 18:19:07.094
-----> 0 - end   sleep: 18:19:12.094 - cost: 5
-----> 1 - start sleep: 18:19:12.095
-----> 1 - end   sleep: 18:19:17.096 - cost: 5
-----> 2 - start sleep: 18:19:17.096
-----> 2 - end   sleep: 18:19:22.097 - cost: 5
-----> 3 - start sleep: 18:19:22.097
-----> 3 - end   sleep: 18:19:27.097 - cost: 5
----------------------------------------------------------------------- */

