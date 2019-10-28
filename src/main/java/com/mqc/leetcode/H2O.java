package com.mqc.leetcode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

class H2O {
    private Semaphore hydrogenSemaphore = new Semaphore(2);
    private Semaphore oxygenSemaphore = new Semaphore(1);

    public H2O() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        hydrogenSemaphore.acquire();
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        oxygenSemaphore.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        oxygenSemaphore.acquire();
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        hydrogenSemaphore.release(2);
    }

    public static void main(String[] args) {
        H2O h2O = new H2O();
        String input = "OOHHHH";
        ExecutorService service = Executors.newCachedThreadPool();
        service.submit(() -> {
            try {
                for (int i = 0; i < 4; i++) {
                    h2O.hydrogen(() -> System.out.println("H"));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.submit(() -> {
            try {
                for (int i = 0; i < 2; i++) {
                    h2O.oxygen(() -> System.out.println("O"));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.shutdown();
    }
}