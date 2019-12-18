package com.mqc.cache.map;

import cn.hutool.core.thread.ThreadUtil;

import java.util.SortedMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Administrator
 * @create 2019/12/18 11:24
 */
public class BlockingItem {
    private Semaphore condition=new Semaphore(1);
    private String item=null;

    public String getItem(){
        while (item==null){
            try {
                condition.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return item;
    }

    public void setItem(String value){
        item=value;
        condition.release();
    }

    public static void main(String[] args) {
        BlockingItem item=new BlockingItem();
        ThreadUtil.execute(()->{
            String value=item.getItem();
            System.out.println(value);
        });

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ThreadUtil.execute(()->{
            item.setItem("1");
        });


    }
}
