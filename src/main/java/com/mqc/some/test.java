package com.mqc.some;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test {
    public static void main(String[] args) {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+":test");
            }
        };

        Runnable runnable2=new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+":test2");
            }
        };
        ExecutorService executorService= Executors.newFixedThreadPool(5);
        for(int i=1;i<10;i++){
            executorService.submit(runnable);
            executorService.submit(runnable);
        }

        executorService.shutdown();

        Pool pool=new Pool(1);
        pool.submit(runnable);
        pool.submit(runnable2);
    }
}


class Pool{
    List<Worker> workers=new ArrayList<>();
    int workerSize;
    Queue<Runnable> taskQueue=new ArrayBlockingQueue(10);
    public Pool(int maxWorkers){
        this.workerSize=maxWorkers;
    }
    public int state;

    public void shutDown(){
        state=0;
    }

    public void submit(Runnable runnable) {
        if(workers.size()<workerSize){
            Worker worker=new Worker(runnable);
            workers.add(worker);
            worker.thread.start();
        }else{
            taskQueue.add(runnable);
        }
    }

    class Worker implements Runnable{
        Thread thread;
        Runnable task;

        public Worker(Runnable task){
            this.task=task;
            this.thread=new Thread(this);
        }

        @Override
        public void run() {
            runWorker();
        }

        private void runWorker() {
            while (state==0||this.task!=null||(this.task=getTask())!=null){
                this.task.run();
                this.task=null;
            }
        }
        private Runnable getTask() {
            Runnable runnable=taskQueue.poll();
            return runnable;
        }
    }
}