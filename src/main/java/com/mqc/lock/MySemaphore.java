package com.mqc.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Author Administrator
 * @create 2020/3/5 19:54
 */
public class MySemaphore {
    class Sync extends AbstractQueuedSynchronizer{
        public Sync(int count){
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {
            for(;;){
                int state=getState();
                int sub=state-arg;
                if(compareAndSetState(state,sub)){
                    return sub;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for(;;){
                int state=getState();
                if(compareAndSetState(state,state+1)){
                    return true;
                }
            }
        }
    }
    private Sync sync;
    public MySemaphore(int count){
        sync=new Sync(count);
    }
    public void acquire(){
        sync.acquireShared(1);
    }

    public void release(){
        sync.releaseShared(1);
    }
    public static void main(String[] args) throws InterruptedException {
        MySemaphore mySemaphore=new MySemaphore(0);
        mySemaphore.release();

        mySemaphore.acquire();
        System.out.println("1");
    }
}
