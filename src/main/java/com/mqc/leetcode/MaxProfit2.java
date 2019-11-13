package com.mqc.leetcode;

/**
 * @Author Administrator
 * @create 2019/11/1 14:50
 */
public class MaxProfit2 {
    public int maxProfit(int[] prices) {
        return calculate(prices, 0);
    }

    public int calculate(int prices[], int s) {
        if (s >= prices.length) {
            return 0;
        }
        int max = 0;
        for (int start = s; start < prices.length; start++) {
            int maxprofit = 0;
            for (int i = start + 1; i < prices.length; i++) {
                if (prices[start] < prices[i]) {
                    int profit = calculate(prices, i + 1) + prices[i] - prices[start];
                    if (profit > maxprofit) {
                        maxprofit = profit;
                    }
                }
            }
            if (maxprofit > max) {
                max = maxprofit;
            }
        }
        return max;
    }

}

/*
方法三：动态规划
        想到动态规划的原因是：可以用贪心算法解决的问题，一般情况下都可以用动态规划。因此，不妨从“状态”、“状态转移方程”的角度考虑一下，使用动态规划的思路解决这道问题。

        根据 「力扣」第 121 题的思路，需要设置一个二维矩阵表示状态。

        定义状态 dp[i][j] 定义如下：

        第一维 i 表示索引为 i 的那一天（具有前缀性质，即考虑了之前天数的收益）能获得的最大利润；
        第二维 j 表示索引为 i 的那一天我是持有股票，还是持有现金，这里 0 表示持有现金（cash），1 表示持有股票（stock）。
        思考状态转移方程：

        状态从持有现金（cash）开始，到最后一天我们关心的状态也是持有现金（cash），因为股价是正数，最后一天买进一支股票，手上的现金肯定少了；
        每一天状态可以转移，也可以不动。 可以用下图表示：


        说明：

        因为不限制交易次数，除了最后一天，每一天的状态可能不变化，也可能转移；
        写代码的时候，可以不用对最后一天单独对待，输出最后一天，状态为 0 的时候的值即可。
        确定起始和终止：

        起始的时候，不持有股票。如果什么都不做，dp[0][0] = 0，如果买入股票，当前收益是负数，即 dp[0][1] = -prices[i]；
        终止的时候，上面也分析了，应该输出 dp[len - 1][0]，因为一定有 dp[len - 1][0] > dp[len - 1][1]。

        参考代码 3：

        Java
public class Solution {

    // 动态规划

    // 0：持有现金
    // 1：持有股票

    public int maxProfit(int[] prices) {
        int len = prices.length;
        if (len < 2) {
            return 0;
        }

        // 状态数组
        // 状态转移：cash → stock → cash → stock → cash → stock → cash
        // 状态转移：0 → 1 → 0 → 1 → 0 → 1 → 0
        int[][] dp = new int[len][2];

        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < len; i++) {
            // 说明这两行调换顺序也是可以的
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
        }
        return dp[len - 1][0];
    }
}
复杂度分析：

        时间复杂度：O(N)O(N)，这里 NN 表示股价数组的长度。
        空间复杂度：O(N)O(N)，虽然是二维数组，但是第二维是常数，与问题规模无关。
        我们也可以将状态数组分开设置，语义更明确。

        作者：liweiwei1419
        链接：https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/solution/tan-xin-suan-fa-by-liweiwei1419-2/
        来源：力扣（LeetCode）
        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。*/
