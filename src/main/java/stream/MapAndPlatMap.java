package stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author Administrator
 * @create 2019/11/19 9:05
 */
public class MapAndPlatMap {
    public static void main(String[] args) {
        String ss = "Hello";

        String[] aa = ss.split("");

        String[] bb = {"H", "e", "l", "l", "o"};

        String[] strings = {"Hello", "World"};

        List<Stream<String>> streamList= Arrays.stream(strings).map(e->e.split("",-1)).map(e->Arrays.stream(e)).collect(Collectors.toList());
        List<String> wordArr=streamList.stream().flatMap(u->u).collect(Collectors.toList());
        System.out.printf(wordArr.toString());


    }
}
