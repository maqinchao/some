package com.mqc.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParallelTest {


    static {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            data.add("===" + i);
        }
        List<String> collect = data.parallelStream().map(ParallelTest::m).collect(Collectors.toList());
    }

    public static String m(String e) {
        System.out.println(Thread.currentThread() + "====" + e);
        return e + "---";
    }


    public static void main(String[] args) {
        System.out.println("end");
    }
}