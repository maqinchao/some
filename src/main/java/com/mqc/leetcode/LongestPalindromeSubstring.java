package com.mqc.leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class LongestPalindromeSubstring {
    public static void main(String[] args) {

    }
    public String longestPalindrome1(String s) {
        String [] arr=s.split("");
        List<Stack<String>> stackList=new LinkedList<>();

        for(int i=0;i<arr.length;i++){
            String s1=arr[i];
            Stack<String> stack=new Stack<>();
            stack.push(s1);

        }
        return null;
    }
}
