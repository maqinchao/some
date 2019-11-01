package com.mqc.leetcode;

import java.util.Arrays;

/**
 * @Author Administrator
 * @create 2019/10/31 19:45
 */
public class MaxProfit {
    public static int maxProfit(int[] prices) {
        int max=0;

        for(int i=0;i<=prices.length-2;i++){
            int price=prices[i];
            for(int j=i+1;j<=prices.length-1;j++){
                int salePrice=prices[j];
                if(max<salePrice-price){
                    max=salePrice-price;
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        int [] ar={3,2,4};
        System.out.printf(""+maxProfit(ar));
    }
}
