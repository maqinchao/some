package com.mqc.leetcode;

/**
 * @Author Administrator
 * @create 2019/10/31 19:00
 */
public class ClimbStairs {
    public int climbStairs(int n) {
        int [] arr=new int[n];
        if(n==1){
            return 1;
        }
        if(n==2){
            return 2;
        }
        arr[0]=1;
        arr[1]=2;
        for(int i=2;i<n;i++){
            arr[i]=arr[i-1]+arr[i-2];
        }
        return arr[n-1];
    }
}
