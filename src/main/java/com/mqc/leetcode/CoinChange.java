package com.mqc.leetcode;

/**
 * @Author Administrator
 * @create 2019/11/22 9:14
 */
public class CoinChange {
    //temp[n]=temp[n-c]+1
    public int coinChane(int conis[],int amount){
        int temp[] =new int [amount];
        temp[0]=0;
        for(int i=0;i<conis.length;i++){

        }
        return 0;
    }

}

/*给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。
如果没有任何一种硬币组合能组成总金额，返回 -1。

示例 1:

输入: coins = [1, 2, 5], amount = 11
输出: 3
解释: 11 = 5 + 5 + 1
示例 2:

输入: coins = [2], amount = 3
输出: -1
说明:
你可以认为每种硬币的数量是无限的。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/coin-change
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。*/
