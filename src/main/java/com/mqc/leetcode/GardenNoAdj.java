package com.mqc.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author Administrator
 * @create 2020/2/12 10:48
 */
public class GardenNoAdj {
    public int[] gardenNoAdj1(int N, int[][] paths) {
        /* 这是一道简单题，限制每个节点的度为3，同时提供四种颜色，因此不需要回溯 */
        /* 初始化节点，使用map保存节点与其临界点的关系 */
        /* 第一版本采用了内部类构建，参考评论区的HashMap更简洁 */
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 0; i < N; i++) {
            graph.put(i, new HashSet<>());
        }
        /* 初始化路径信息 */
        for (int[] path: paths) {
            int a = path[0] - 1;
            int b = path[1] - 1;
            graph.get(a).add(b);
            graph.get(b).add(a);
        }
        int[] res = new int[N];
        for (int i = 0; i < N; i++) {
            boolean[] used = new boolean[5];
            /* 查看当前节点的所有邻接点的色彩 */
            for (int adj: graph.get(i)) {
                used[res[adj]] = true;
            }
            /* 为当前节点染色 */
            for (int j = 1; j <= 4; j++) {
                if (!used[j]) {
                    res[i] = j;
                }
            }
        }
        return res;
    }
    public int[] gardenNoAdj2(int N, int[][] paths) {
        if (paths.length==0){//如果没有花园相通
            int []result=new int[N];
            for (int i = 0; i <N; i++) {
                result[i]=1;
            }
        }
        //每个位置维持一个5个长度的数组，第一个位置存放有多少个相邻花园，最后一个位置放花的种类
        int [][]result =new int[N+1][5];
        //先遍历 ，把相邻位置放进去
        for (int i = 0; i <paths.length; i++) {
            result[paths[i][0]][0]++;//增加一个相邻花园
            result[paths[i][1]][0]++;//增加一个相邻花园
            result[paths[i][0]][ result[paths[i][0]][0]]=paths[i][1];//把相邻花园放进去
            result[paths[i][1]][ result[paths[i][1]][0]]=paths[i][0];//把相邻花园放进去
        }
        for (int i = 1; i <=N; i++) {//遍历每一个花园
            for (int j = 1; j <=4 ; j++) {//遍历决定放哪一种花
                int k=0;//定义在外面，好判断进行到哪了
                for (k = 1; k <4 ; k++) {//看这个花有没有相邻花园使用
                    if(result[result[i][k]][4]==j)break;//
                }
                if (k==4){
                    result[i][4]=j;
                    break;
                }
            }
        }
        int []result2=new int[N];//真正的结果
        for (int i = 0; i <N; i++) {
            result2[i]=result[i+1][4];
        }
        return result2;
    }
}

//有 N 个花园，按从 1 到 N 标记。在每个花园中，你打算种下四种花之一。
//
//        paths[i] = [x, y] 描述了花园 x 到花园 y 的双向路径。
//
//        另外，没有花园有 3 条以上的路径可以进入或者离开。
//
//        你需要为每个花园选择一种花，使得通过路径相连的任何两个花园中的花的种类互不相同。
//
//        以数组形式返回选择的方案作为答案 answer，其中 answer[i] 为在第 (i+1) 个花园中种植的花的种类。花的种类用  1, 2, 3, 4 表示。保证存在答案。
//
//         
//
//        示例 1：
//
//        输入：N = 3, paths = [[1,2],[2,3],[3,1]]
//        输出：[1,2,3]
//        示例 2：
//
//        输入：N = 4, paths = [[1,2],[3,4]]
//        输出：[1,2,1,2]
//        示例 3：
//
//        输入：N = 4, paths = [[1,2],[2,3],[3,4],[4,1],[1,3],[2,4]]
//        输出：[1,2,3,4]
//         
//
//        提示：
//
//        1 <= N <= 10000
//        0 <= paths.size <= 20000
//        不存在花园有 4 条或者更多路径可以进入或离开。
//        保证存在答案。
//        贡献者
//
//        来源：力扣（LeetCode）
//        链接：https://leetcode-cn.com/problems/flower-planting-with-no-adjacent
//        著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。