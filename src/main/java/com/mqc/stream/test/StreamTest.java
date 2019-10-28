package com.mqc.stream.test;

import java.util.List;
import java.util.Random;

public class StreamTest {
    public static void main(String[] args) {
        Random random = new Random();
        random.ints().limit(10).sorted().forEach(System.out::println);


    }
}
