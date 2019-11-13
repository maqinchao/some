package com.mqc.leetcode;

import java.util.Arrays;

/**
 * @Author Administrator
 * @create 2019/10/31 19:45
 */
public class MaxProfit {
    public static int maxProfit2(int[] prices) {
        int max = 0;

        for (int i = 0; i <= prices.length - 2; i++) {
            int price = prices[i];
            for (int j = i + 1; j <= prices.length - 1; j++) {
                int salePrice = prices[j];
                if (max < salePrice - price) {
                    max = salePrice - price;
                }
            }
        }
        return max;
    }

    public static int maxProfit1(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }

        // 有可能不做交易，因此结果集的初始值设置为 0
        int res = 0;

        // 表示在当前位置之前的最小值，假设修正法（打擂台法）
        int minVal = prices[0];
        // 注意：这里从 1 开始
        for (int i = 1; i < len; i++) {
            res = Math.max(res, prices[i] - minVal);
            minVal = Math.min(minVal, prices[i]);
        }
        return res;
    }
    public static int maxProfit(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }

        // 0：用户手上不持股所能获得的最大利润，特指卖出股票以后的不持股，非指没有进行过任何交易的不持股
        // 1：用户手上持股所能获得的最大利润

        // 注意：因为题目限制只能交易一次，因此状态只可能从 1 到 0，不可能从 0 到 1
        // 状态转移方程：
        // dp[i][0] = max(dp[i - 1][0], dp[i - 1][1] + prices[i])
        // dp[i][1] = max(dp[i - 1][1], -prices[i])
        int[][] dp = new int[len][2];
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < len; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], -prices[i]);
        }
        return dp[len - 1][0];
    }

    public static void main(String[] args) {
        int[] ar = {3, 2, 4};
        System.out.printf("" + maxProfit(ar));
    }
}
