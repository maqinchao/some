package com.mqc.leetcode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

class ZeroEvenOdd {
    private int n;

    private int current=0;

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    private Semaphore zeroSemaphore=new Semaphore(1);

    private Semaphore evenSemaphore=new Semaphore(0);

    private Semaphore oddSemaphore=new Semaphore(0);

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for(int i=0;i<n;i++){
            zeroSemaphore.acquire();
            printNumber.accept(0);
            if((++current)%2==0){
                evenSemaphore.release();
            }else{
                oddSemaphore.release();
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        int count=n%2==0?n/2:(n-1)/2;
        for(int i=0;i<count;i++){
            evenSemaphore.acquire();
            printNumber.accept(current);
            zeroSemaphore.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        int count=n%2==0?n/2:(n+1)/2;
        for(int i=0;i<count;i++){
            oddSemaphore.acquire();
            printNumber.accept(current);
            zeroSemaphore.release();
        }
    }

    public static void main(String[] args) {
        ZeroEvenOdd zeroEvenOdd=new ZeroEvenOdd(2);
        ExecutorService executorService= Executors.newCachedThreadPool();
        executorService.submit(()-> {
            try {
                zeroEvenOdd.zero(new IntConsumer() {
                    @Override
                    public void accept(int value) {
                        System.out.print(value);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.submit(()-> {
            try {
                zeroEvenOdd.even(new IntConsumer() {
                    @Override
                    public void accept(int value) {
                        System.out.print(value);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.submit(()-> {
            try {
                zeroEvenOdd.odd(new IntConsumer() {
                    @Override
                    public void accept(int value) {
                        System.out.print(value);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }
}