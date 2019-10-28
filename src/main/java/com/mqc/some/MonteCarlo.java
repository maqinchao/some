package com.mqc.some;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class MonteCarlo {

    public static void main(String[] args) throws InterruptedException {
        final int N = 500000000;
        AtomicInteger sum =new AtomicInteger(0);
        CountDownLatch latch=new CountDownLatch(8);
        Runnable runnable=()->{
            for (int i = 1; i < N/8; i++) {
                double x = Math.random();
                double y = Math.random();
                if ((x * x + y * y) < 1) {
                    sum.getAndIncrement();
                }
            }
            latch.countDown();
            System.out.println(Thread.currentThread().getName()+" end");
        };
        ExecutorService executorService= Executors.newFixedThreadPool(8);

        for(int i=0;i<8;i++){
            executorService.submit(runnable);
        }
        latch.await();
        System.out.println("PI = " + (double) 4 * sum.get()/ N);
    }
 
}
