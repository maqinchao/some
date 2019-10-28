package com.mqc.some;

import java.util.concurrent.*;

public class ThreadPoolTest {
    public static void main(String[] args) {
        //new ThreadPoolExecutor.AbortPolicy()
        //new ThreadPoolExecutor.CallerRunsPolicy()
        //new ThreadPoolExecutor.DiscardPolicy()
        //new ThreadPoolExecutor.DiscardOldestPolicy()
        ExecutorService executorService= new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(1),Executors.defaultThreadFactory(),new ThreadPoolExecutor.DiscardOldestPolicy());
        for(int i=0;i<4;i++){
            System.out.println("submit:"+i);
            int finalI = i;
            try {
                executorService.submit(()->{
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("test:"+ finalI);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }
}

class LogErrorPolicy implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        System.out.println();
    }
}