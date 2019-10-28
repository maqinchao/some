package com.mqc.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    private Semaphore semaphore1=new Semaphore(1);
    private Semaphore semaphore2=new Semaphore(0);

    private int n;

    public SemaphoreTest() {
    }

    public SemaphoreTest(int n) {
        this.n = n;
    }

    public void test1(){
        for(int i=0;i<n;i++){
            try {
                semaphore1.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("foo");
            semaphore2.release();
        }
    }

    public void test2(){
        for(int i=0;i<n;i++){
            try {
                semaphore2.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("bak");
            semaphore1.release();
        }
    }

    public static void main(String[] args) {
        SemaphoreTest semaphoreTest=new SemaphoreTest(5);
        ExecutorService service= Executors.newCachedThreadPool();
        service.submit(()->semaphoreTest.test1());
        service.submit(()->semaphoreTest.test2());
        service.shutdown();
    }
}
