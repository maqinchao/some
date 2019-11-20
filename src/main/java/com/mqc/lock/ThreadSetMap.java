package com.mqc.lock;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Administrator
 * @create 2019/11/18 9:15
 */
public class ThreadSetMap {
    public static void main(String[] args) {
        Map<String,String> map=new ConcurrentHashMap<>();
        ExecutorService executorService=Executors.newCachedThreadPool();
        for(int i=0;i<10000;i++){
            executorService.submit(new Task(map,"1","1"));
        }
        executorService.shutdown();
        while (!executorService.isTerminated()){

        }
        System.out.println(Task.num+"");
    }
}

class Task extends Thread{
    public static int num=0;
    private Map<String,String> map;
    private String key;
    private String value;

    public Task(){};

    public Task( Map<String,String> map, String key, String value){
        this.map=map;
        this.key=key;
        this.value=value;
    }


    @Override
    public void run() {
//        synchronized (Task.class){
//            num++;
//        }
        Long id=Thread.currentThread().getId();
        System.out.println(id+"-等待锁");
        while (map.putIfAbsent(key,value)!=null){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(id+"-获得锁");
        num++;
        System.out.println(map.remove("1")+"-"+id+"-解锁");
    }
}
