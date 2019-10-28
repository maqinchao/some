package com.mqc.leetcode;

public class LongestIncreasingSubsequence {
    public static void main(String[] args) {
        Integer [] arr=new Integer[]{4,5,2,3,5,6,4,8,7,9};
        Integer [] temp=new Integer[arr.length];
        int maxLength=0;
        for(int i=0;i<arr.length;i++){
            int a=arr[i];

            int maxSubArrSize=0;
            for(int j=0;j<i;j++){
                if(arr[j]<a&&maxSubArrSize<temp[j]){
                    maxSubArrSize=temp[j];
                }
            }
            temp[i]=maxSubArrSize+1;
            if(maxLength<temp[i]){
                maxLength=temp[i];
            }
        }
        System.out.println(maxLength);
    }
}
