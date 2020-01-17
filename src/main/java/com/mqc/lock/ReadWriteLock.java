package com.mqc.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author Administrator
 * @create 2020/1/15 21:56
 */
public class ReadWriteLock {
    private static int i=0;
    public static void main(String[] args) {
        ExecutorService service=Executors.newCachedThreadPool();

        Sync sync=new Sync();
//        sync.acquire(1);
//        i++;
//        sync.release(1);
        for (int c=0;c<1;c++) {
            service.submit(()->{
                sync.acquireShared(1);
                i++;
                sync.releaseShared(1);
            });
        }
        service.shutdown();
        while (!service.isTerminated()){

        }
        System.out.println(i+"");



    }
}

class Sync extends AbstractQueuedSynchronizer{
    @Override
    protected boolean tryAcquire(int acquires) {
        final Thread current = Thread.currentThread();
        int c = getState();
        if (c == 0) {
            if (compareAndSetState(0, acquires)) {
                setExclusiveOwnerThread(current);
                return true;
            }
        }
        else if (current == getExclusiveOwnerThread()) {
            int nextc = c + acquires;
            if (nextc < 0) {
                throw new Error("Maximum lock count exceeded");
            }
            setState(nextc);
            return true;
        }
        return false;
    }

    @Override
    protected boolean tryRelease(int releases) {
        int c = getState() - releases;
        if (Thread.currentThread() != getExclusiveOwnerThread())
            throw new IllegalMonitorStateException();
        boolean free = false;
        if (c == 0) {
            free = true;
            setExclusiveOwnerThread(null);
        }
        setState(c);
        return free;
    }

    @Override
    protected int tryAcquireShared(int arg) {
        return (getState() == 0) ? 1 : -1;
    }

    @Override
    protected boolean tryReleaseShared(int arg) {
        for (;;) {
            int c = getState();
            if (c == 0)
                return false;
            int nextc = c-1;
            if (compareAndSetState(c, nextc))
                return nextc == 0;
        }
    }
}