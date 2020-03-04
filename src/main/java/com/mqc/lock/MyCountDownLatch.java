package com.mqc.lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Author Administrator
 * @create 2020/3/4 9:11
 */
public class MyCountDownLatch{
    private Sync sync;

    public MyCountDownLatch(int count){
        sync=new Sync(count);
    }

    class Sync extends AbstractQueuedSynchronizer{
        public Sync(int count){
            setState(count);
        }
        @Override
        protected int tryAcquireShared(int arg) {
            return getState()==0?1:-1;
        }

        @Override
        protected boolean tryAcquire(int arg) {
            return super.tryAcquire(arg);
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for(;;){
                int n=getState();
                if(n==0){
                    return false;
                }
                int c=n-arg;
                if(compareAndSetState(n,c)){
                    return c==0;
                }
            }
        }
    }


    public void countDown(){
        sync.releaseShared(1);
    }

    public void await(){
        sync.acquireShared(1);
    }

    public static void main(String[] args) {
        MyCountDownLatch countDownLatch=new MyCountDownLatch(2);
        new Thread(()->{
            countDownLatch.await();
            System.out.println("lock out");
        }).start();
        countDownLatch.countDown();
        countDownLatch.countDown();

    }

}
