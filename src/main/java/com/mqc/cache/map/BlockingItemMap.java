package com.mqc.cache.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @Author Administrator
 * @create 2019/12/18 15:19
 */
public class BlockingItemMap {
    private static Map<String, BlockingItem> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            String key = "test" + i;
            list.add(key);
        }
        for (String key : list) {
            executorService.submit(() -> get(key));

//            executorService.submit(() -> put(key, "answer:" + key));
        }
        try {
            Thread.sleep(5*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (String key : list) {
//            executorService.submit(() ->  get(key));

            executorService.submit(() -> put(key, "answer:" + key));
        }

        executorService.shutdown();
    }

    public static void put(String key, String value) {
        BlockingItem queue = map.get(key);
        if (queue == null) {
            queue = new BlockingItem();
            BlockingItem existedQueue = map.putIfAbsent(key, queue);
            if (existedQueue == null) {
                queue.setItem(value);
                System.out.println("put key:" + key);
            } else {
                existedQueue.setItem(value);
                System.out.println("put key:" + key);
            }
        } else {
            queue.setItem(value);
            System.out.println("put key:" + key);
        }
    }

    public static String get(String key) {
        BlockingItem queue = map.get(key);
        if (queue == null) {
            queue = new BlockingItem();
            BlockingItem existedQueue = map.putIfAbsent(key, queue);
            if (existedQueue == null) {
                String answer = queue.getItem();
                System.out.println(answer);
                return answer;
            } else {
                String answer = existedQueue.getItem();
                System.out.println(answer);
                return answer;
            }
        } else {
            String answer = queue.getItem();
            System.out.println(answer);
            return answer;
        }
    }
}
