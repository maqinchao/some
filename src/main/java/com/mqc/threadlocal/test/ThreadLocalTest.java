package com.mqc.threadlocal.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalTest {
    private static ThreadLocal<String> stringThreadLocal=new ThreadLocal<>();

    public static void main(String[] args) {
        ExecutorService service= Executors.newCachedThreadPool();
        service.submit(()->{
            System.out.println("name:"+stringThreadLocal.get());
            stringThreadLocal.set(Thread.currentThread().getName());
            System.out.println("name:"+stringThreadLocal.get());
        });
        service.submit(()->{
            System.out.println("name:"+stringThreadLocal.get());
            stringThreadLocal.set(Thread.currentThread().getName());
            System.out.println("name:"+stringThreadLocal.get());
        });
        service.shutdown();
    }
}
