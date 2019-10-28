package com.mqc.map;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap<String,String> map=new ConcurrentHashMap<>();
        map.put("test","test");
        map.put("test2","test2");
        map.put("test3","test3");
        String test=map.get("test");

        int size=map.size();
    }
}
