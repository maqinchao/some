package com.mqc.cache.map;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.*;

import java.util.Map;

/**
 * @Author Administrator
 * @create 2019/12/17 8:59
 */
@Slf4j
public class BlockingMap {
    private static Map<String, BlockingQueue<String>> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            String key = "test" + i;
            list.add(key);
        }
        for (String key : list) {
            executorService.submit(() -> put(key, "answer:" + key));

            executorService.submit(() ->  get(key));
        }

        executorService.shutdown();
    }

    public static void put(String key, String value) {
        BlockingQueue<String> queue = map.get(key);
        if (queue == null) {
            queue = new LinkedBlockingQueue<>();
            BlockingQueue existedQueue = map.putIfAbsent(key, queue);
            if (existedQueue == null) {
                queue.offer(value);
                System.out.println("put key:" + key);
            } else {
                existedQueue.offer(value);
                System.out.println("put key:" + key);
            }
        } else {
            queue.offer(value);
            System.out.println("put key:" + key);
        }

    }

    public static String get(String key) {
        try {
            BlockingQueue<String> queue = map.get(key);
            if (queue == null) {
                queue = new LinkedBlockingQueue<>();
                BlockingQueue<String> existedQueue = map.putIfAbsent(key, queue);
                if (existedQueue == null) {
                    String answer = queue.poll(1, TimeUnit.DAYS);
                    System.out.println(answer);
                    return answer;
                } else {
                    String answer = existedQueue.poll(1, TimeUnit.DAYS);
                    System.out.println(answer);
                    return answer;
                }
            } else {
                String answer = queue.poll(1, TimeUnit.DAYS);
                System.out.println(answer);
                return answer;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
