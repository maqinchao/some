package com.mqc.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Administrator
 * @create 2019/11/20 17:14
 */
public class MinimumTotal {

    private static int row;
    private static Integer[][] memo;

    public static int minimumTotal(List<List<Integer>> triangle) {
        row = triangle.size();
        memo = new Integer[row][row];
        return helper(0, 0, triangle);
    }

    private static int helper(int level, int c, List<List<Integer>> triangle) {
        if (memo[level][c] != null) {
            return memo[level][c];
        }
        if (level == row - 1) {
            return memo[level][c] = triangle.get(level).get(c);
        }
        int left = helper(level + 1, c, triangle);
        int right = helper(level+1, c + 1, triangle);
        return memo[level][c] = Math.min(left, right) + triangle.get(level).get(c);
    }

//    public static int minimumTotal(List<List<Integer>> triangle) {
//        int [][] cost=new int[triangle.size()+1][triangle.size()];
//        cost[0][0]=triangle.get(0).get(0);
//
//        //cost[n][i]=min(cost[n-1][i],cost[n-1][i-1])
//        for(int n=1,outerSize=triangle.size();n<outerSize;n++){
//            List<Integer> innerList=triangle.get(n);
//            for(int i=0,innerSize=innerList.size();i<innerSize;i++){
//                int left=i-1;
//                int right=i;
//                int localCost=innerList.get(i);
//                if(left<0&&right<=n-1){
//                    cost[n][i]=cost[n-1][right]+localCost;
//                }else if(left>=0&&right>n-1){
//                    cost[n][i]=cost[n-1][left]+localCost;
//                }else {
//                    cost[n][i]=Math.min(cost[n-1][left]+localCost,cost[n-1][right]+localCost);
//                }
//            }
//        }
//
//        int minSum=cost[triangle.size()-1][0];
//        for(int i=0;i<cost[triangle.size()-1].length;i++){
//            minSum=Math.min(minSum,cost[triangle.size()-1][i]);
//        }
//
//        return minSum;
//    }

    public static void main(String[] args) {
        List<List<Integer>> triangle=new ArrayList<>();
        triangle.add(Arrays.asList(2));
        triangle.add(Arrays.asList(3,4));
        triangle.add(Arrays.asList(6,5,7));
        triangle.add(Arrays.asList(4,1,8,3));
        System.out.printf(""+minimumTotal(triangle));
    }
}
/*给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。

例如，给定三角形：

[
     [2],
    [3,4],
   [6,5,7],
  [4,1,8,3]
]
自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。

说明：

如果你可以只使用 O(n) 的额外空间（n 为三角形的总行数）来解决这个问题，那么你的算法会很加分。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/triangle
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。*/
