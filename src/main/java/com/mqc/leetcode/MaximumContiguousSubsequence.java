package com.mqc.leetcode;

public class MaximumContiguousSubsequence {
    public static void main(String[] args) {
        Integer [] arr={6,-1,3,-4,-6,9,2,-2,5};
        int max=arr[0];
        Integer [] temp=new Integer[arr.length];
        temp[0]=arr[0];
        for(int i=1;i<arr.length;i++){
            int a=arr[i];
            if(temp[i-1]+a<a){
                temp[i]=a;
            }else {
                temp[i]=temp[i-1]+a;
            }
            if(temp[i]>max){
                max=temp[i];
            }
        }
        System.out.println(max);
    }
}
