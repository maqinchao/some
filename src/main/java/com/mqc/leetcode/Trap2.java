package com.mqc.leetcode;

import java.util.Stack;

/**
 * @Author Administrator
 * @create 2020/1/19 10:25
 */
public class Trap2 {
    public static int trap(int[] height) {
        if(height.length==0){
            return 0;
        }
        int sum=0;
        Stack<Integer> stack=new Stack<>();
        stack.push(height[0]);
        for(int i=1;i<height.length;i++){

        }
        return sum;
    }
    public static void main(String[] args) {

    }
}
