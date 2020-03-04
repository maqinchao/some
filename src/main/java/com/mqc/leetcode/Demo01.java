package com.mqc.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Demo01 {
    public static void test1(int vertexNum, char[] vertex, int[][] matrix) {
        Graph g = new Graph(vertexNum, vertex, matrix);
        ArrayList<Integer> visitedSerials = traverseByBFS(g, 1); // 以下标为1的顶点开始BFS图g
        System.out.println("邻接矩阵法");
        System.out.print("BFS:");
        for (Integer visited : visitedSerials) {
            System.out.print(vertex[visited]);
        }
        System.out.println();
        Arrays.fill(g.visited, false); // 重置所有顶点均未被访问
        for (int distance : minDistance(g, 0)) {
            System.out.print(distance);
        }
        System.out.println();
        Arrays.fill(g.visited, false); // 重置所有顶点均未被访问
        System.out.print("DFS:");
        traverseByDFS(g, 0);
    }

    public static void test2(int vertexNum, char[] vertexs, int[][] matrix) {
        Graph2 g = new Graph2(vertexNum);
        Graph2[] g2 = g.create(vertexs, matrix);
        ArrayList<Integer> visitedSerials = traverseByBFS(g2, 1);
        System.out.println("邻接表法");
        System.out.print("BFS:");
        for (Integer visited : visitedSerials) {
            System.out.print(g2[visited].vertex);
        }
        Arrays.fill(Graph2.visited, false);
        System.out.print("\nDFS:");
        traverseByDFS(g2, 0);
    }

    public static void main(String[] args) {
        int vertexNum = 11;
        char[] vertex = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K' };
        int[][] matrix = new int[][] { { 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0 }, { 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0 },
                { 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0 }, { 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
                { 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0 }, { 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0 }, { 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } }; // 此图包含三个连通分量
        test1(vertexNum, vertex, matrix);
        System.out.println();
        test2(vertexNum, vertex, matrix);
    }

    // 以顶点i开始的图的广度优先搜索(邻接矩阵法)
    public static ArrayList<Integer> traverseByBFS(Graph g, int i) {
        ArrayList<Integer> visitedSerialOfAll = new ArrayList<Integer>();
        if (!g.visited[i]) {
            visitedSerialOfAll.addAll(BFS(g, i)); // 如果以下标为i的顶点未被访问，那么对从i开始的顶点进行BFS
        }
        for (int ii = 0; ii < g.vertexNum; ii++) { // 对图中所有顶点进行查看是否访问过(从第0号节点开始访问)，因为图可能是非连通的(由几个连通分量组成)，那么由某个顶点开始就不能将图所有节点遍历到
            if (!g.visited[ii]) {
                visitedSerialOfAll.addAll(BFS(g, ii)); // 如果下标为i的顶点未被访问，那么对从i开始的顶点进行BFS
            }
        }
        return visitedSerialOfAll;
    }

    // 以顶点i开始的连通图的广度优先搜索(邻接矩阵法)
    public static ArrayList<Integer> BFS(Graph g, int i) {
        ArrayList<Integer> visitedSerialOfPart = new ArrayList<Integer>(); // 存放某个连通分量顶点依次访问的序列
        LinkedList<Integer> l = new LinkedList<Integer>();
        visitedSerialOfPart.add(i);
        g.visited[i] = true; // 下标为i的顶点已访问
        l.addLast(i); // 将下标为i的顶点入队
        while (!l.isEmpty()) {
            int ii = l.removeFirst(); // 出队
            ArrayList<Integer> vertexs = g.getAllNeighbors(ii); // 得到ii顶点的所有邻接顶点
            while (!vertexs.isEmpty()) {
                int iii = vertexs.get(0); // 得到顶点ii的第一个邻接顶点
                vertexs.remove(0); // 从顶点ii的邻接序列中移除
                if (!g.visited[iii]) { // 未被访问则访问之
                    visitedSerialOfPart.add(iii);
                    g.visited[iii] = true;
                    l.addLast(iii); // 入队
                }
            }
        }
        return visitedSerialOfPart;
    }

    // 以顶点i开始的图的广度优先搜索(邻接表法)
    public static ArrayList<Integer> traverseByBFS(Graph2[] g, int i) {
        ArrayList<Integer> visitedSerialOfAll = new ArrayList<Integer>();
        if (!Graph2.visited[i]) {
            visitedSerialOfAll.addAll(BFS(g, i)); // 如果以下标为i的顶点未被访问，那么对从i开始的顶点进行BFS
        }
        for (int ii = 0; ii < Graph2.vertexNum; ii++) { // 对图中所有顶点进行查看是否访问过(从第0号节点开始访问)，因为图可能是非连通的(由几个连通分量组成)，那么由某个顶点开始就不能将图所有节点遍历到
            if (!Graph2.visited[ii]) {
                visitedSerialOfAll.addAll(BFS(g, ii)); // 如果下标为i的顶点未被访问，那么对从i开始的顶点进行BFS
            }
        }
        return visitedSerialOfAll;
    }

    // 以顶点i开始的连通图的广度优先搜索(邻接表法)
    public static ArrayList<Integer> BFS(Graph2[] g, int i) {
        ArrayList<Integer> visitedSerialOfPart = new ArrayList<Integer>(); // 存放某个连通分量顶点依次访问的序列
        LinkedList<Integer> l = new LinkedList<Integer>();
        visitedSerialOfPart.add(i);
        Graph2.visited[i] = true; // 下标为i的顶点已访问
        l.addLast(i); // 将下标为i的顶点入队
        while (!l.isEmpty()) {
            int ii = l.removeFirst(); // 出队
            Graph2 cur = g[ii];
            while (cur.next != null) {
                cur = cur.next;
                int iii = Graph2.indexOf(g, cur.vertex);
                if (!Graph2.visited[iii]) {
                    visitedSerialOfPart.add(iii);
                    Graph2.visited[iii] = true;
                    l.addLast(iii);
                }
            }
        }
        return visitedSerialOfPart;
    }

    // 以顶点i开始的图的深度优先搜索(邻接矩阵法)
    public static void traverseByDFS(Graph g, int i) {
        if (!g.visited[i]) {
            DFS(g, i); // 如果以下标为i的顶点未被访问，那么对从i开始的顶点进行BFS
        }
        for (int ii = 0; ii < g.vertexNum; ii++) { // 对图中所有顶点进行查看是否访问过(从第0号节点开始访问)，因为图可能是非连通的(由几个连通分量组成)，那么由某个顶点开始就不能将图所有节点遍历到
            if (!g.visited[ii]) {
                DFS(g, ii); // 如果下标为i的顶点未被访问，那么对从i开始的顶点进行BFS
            }
        }
    }

    // 以顶点i开始的连通图的深度优先搜素(邻接矩阵法)
    public static void DFS(Graph g, int i) {
        System.out.print(g.vertex[i]);
        g.visited[i] = true; // 下标为i的顶点已访问
        ArrayList<Integer> vertexs = g.getAllNeighbors(i); // 得到i顶点的所有邻接顶点
        while (!vertexs.isEmpty()) {
            int iii = vertexs.get(0); // 得到顶点ii的第一个邻接顶点
            vertexs.remove(0); // 从顶点ii的邻接序列中移除
            if (!g.visited[iii]) { // 未被访问则访问之
                DFS(g, iii);
            }
        }
    }

    // 以顶点i开始的图的深度优先搜索(邻接表法)
    public static void traverseByDFS(Graph2[] g, int i) {
        if (!Graph2.visited[i]) {
            DFS(g, i); // 如果以下标为i的顶点未被访问，那么对从i开始的顶点进行BFS
        }
        for (int ii = 0; ii < Graph2.vertexNum; ii++) { // 对图中所有顶点进行查看是否访问过(从第0号节点开始访问)，因为图可能是非连通的(由几个连通分量组成)，那么由某个顶点开始就不能将图所有节点遍历到
            if (!Graph2.visited[ii]) {
                DFS(g, ii); // 如果下标为i的顶点未被访问，那么对从i开始的顶点进行BFS
            }
        }
    }

    // 以顶点i开始的连通图的深度优先搜素(邻接表法)
    public static void DFS(Graph2[] g, int i) {
        System.out.print(g[i].vertex);
        Graph2.visited[i] = true; // 下标为i的顶点已访问
        Graph2 cur = g[i];
        while (cur.next != null) {
            cur = cur.next;
            int ii = Graph2.indexOf(g, cur.vertex);
            if (!Graph2.visited[ii]) {
                DFS(g, ii);
            }
        }
    }

    // 求解图的单源(i顶点)最短路径，如果从这个单源不可达，那么这些顶点就不会被访问，自然地距离也就会是默认的0
    public static int[] minDistance(Graph g, int i) {
        int[] minDistanceSerial = new int[g.vertexNum];
        LinkedList<Integer> l = new LinkedList<Integer>();
        minDistanceSerial[i] = 0;
        g.visited[i] = true; // 下标为i的顶点已访问
        l.addLast(i); // 将下标为i的顶点入队
        while (!l.isEmpty()) {
            int ii = l.removeFirst(); // 出队
            ArrayList<Integer> vertexs = g.getAllNeighbors(ii); // 得到ii顶点的所有邻接顶点
            while (!vertexs.isEmpty()) {
                int iii = vertexs.get(0); // 依次得到顶点ii邻接顶点
                vertexs.remove(0); // 从顶点ii的邻接序列中移除
                if (!g.visited[iii]) { // 未被访问则访问之
                    minDistanceSerial[iii] = minDistanceSerial[ii] + 1; // 在ii顶点基础上加1(因为BFS是按照距离由近到远来遍历图中每一个顶点的)
                    g.visited[iii] = true;
                    l.addLast(iii); // 入队
                }
            }
        }
        return minDistanceSerial;
    }

    private static class Graph {
        private int vertexNum; // 图中顶点的个数
        private char[] vertex = null; // 顶点信息
        private int[][] matrix = null; // 顶点之间的边信息
        private boolean[] visited = null; // 标记相应顶点是否访问

        public Graph(int vertexNum, char[] vertex, int[][] matrix) {
            this.vertexNum = vertexNum;
            this.vertex = vertex;
            this.matrix = matrix;
            visited = new boolean[vertexNum]; // 初始化所有顶点均未被访问
        }

        public ArrayList<Integer> getAllNeighbors(int i) { // 得到i顶点的所有邻接顶点
            ArrayList<Integer> vertexs = new ArrayList<Integer>();
            for (int ii = 0; ii < vertexNum; ii++) {
                if (matrix[i][ii] == 1) { // 与i顶点是邻接顶点
                    vertexs.add(ii);
                }
            }
            return vertexs;
        }

    }

    private static class Graph2 {
        char vertex;
        private Graph2 next;
        private static int vertexNum;
        private static boolean visited[] = null;

        public Graph2(char vertex) {
            this.vertex = vertex;
        }

        public Graph2(int vertexNum) {
            this.vertexNum = vertexNum;
            this.visited = new boolean[this.vertexNum];
        }

        public Graph2[] create(char[] vertexs, int[][] matrix) {
            Graph2[] g2 = new Graph2[vertexNum];
            for (int i = 0; i < matrix.length; i++) {
                g2[i] = new Graph2(vertexs[i]); // 创建顶点表
                Graph2 cur = g2[i];
                for (int ii = 0; ii < matrix[i].length; ii++) {
                    if (matrix[i][ii] != 0) { // 顶点i的邻接点
                        Graph2 tmp = new Graph2(vertexs[ii]); // 创建边表
                        cur.next = tmp; // 将其作为上一个顶点的后继
                        cur = tmp;
                    }
                }
            }
            return g2;
        }

        // 得到图中顶点为vertex的索引
        public static int indexOf(Graph2[] g, char vertex) {
            for (int i = 0; i < vertexNum; i++) {
                if (g[i].vertex == vertex) {
                    return i;
                }
            }
            return -1; // 未找到
        }
    }
}
