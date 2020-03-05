package com.mqc.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Author Administrator
 * @create 2020/3/4 13:45
 */
public class MyReentryLock {
    class Sync extends AbstractQueuedSynchronizer{
        @Override
        protected boolean tryAcquire(int arg) {
            Thread t=Thread.currentThread();
            int state = getState();
            //多个线程如果在此时发生竞争 只 有一个线程能完成更新 其余线程返回false
            if(state==0){
                if(compareAndSetState(0,1)){
                    setExclusiveOwnerThread(t);
                    return true;
                }
            } else if(t==getExclusiveOwnerThread()){
                //进入失败处理 如果相同线程 则说明出现重入
                //这里不需要进行cas操作 更新状态的时候 如果进入到这个分支 说明此时已经只有一个线程在执行串行操作
                //state 不需要再次获取一次
                setState(state+1);
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int releases) {
            Thread currentOwner=getExclusiveOwnerThread();
            if(currentOwner!=Thread.currentThread()){
                throw  new RuntimeException("error Thread");
            }

            //此处无需考虑并发问题
            int state=getState()-1;
            if(state==0){
                setState(state);
                setExclusiveOwnerThread(null);
                return true;
            }else{
                setState(state);
                return false;
            }

        }

        public void lock() {
            //如果当前无线程占用 则更新状态码 更新失败 则说明有线程同时抢占
            if(compareAndSetState(0,1)){
                setExclusiveOwnerThread(Thread.currentThread());
            }else{
                acquire(1);
            }
        }
    }

    private Sync sync=new Sync();

    public void lock(){
        sync.lock();
    }

    public void unlock(){
        sync.release(1);
    }

    public static void main(String[] args) {
        MyReentryLock lock=new MyReentryLock();
        lock.lock();
        Thread thread = new Thread(() -> {
            lock.lock();
            System.out.println("lock out");
            lock.unlock();
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
    }
}
