package com.yongliang.lib_launch.launch.launchstarter.sort;

/**
 * 有向无环图的拓扑排序算法
 */
public class Graph {
    //顶点数
    private int mVerticeCount;
    //邻接表
    private java.util.List<Integer>[] mAdj;

    public Graph(int verticeCount) {
        this.mVerticeCount = verticeCount;
        mAdj = new java.util.ArrayList[mVerticeCount];
        for (int i = 0; i < mVerticeCount; i++) {
            mAdj[i] = new java.util.ArrayList<Integer> ();
        }
    }

    /**
     * 添加边
     *
     * @param u from
     * @param v to
     */
    public void addEdge(int u, int v) {
        mAdj[u].add(v);
    }

    /**
     * 拓扑排序
     */
    public java.util.Vector<Integer> topologicalSort() {
        int indegree[] = new int[mVerticeCount];
        for (int i = 0; i < mVerticeCount; i++) {//初始化所有点的入度数量
            java.util.ArrayList<Integer> temp = (java.util.ArrayList<Integer>) mAdj[i];
            for (int node : temp) {
                indegree[node]++;
            }
        }
        java.util.Queue<Integer> queue = new java.util.LinkedList<Integer> ();
        for (int i = 0; i < mVerticeCount; i++) {//找出所有入度为0的点
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }
        int cnt = 0;
        java.util.Vector<Integer> topOrder = new java.util.Vector<Integer> ();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            topOrder.add(u);
            for (int node : mAdj[u]) {//找到该点（入度为0）的所有邻接点
                if (--indegree[node] == 0) {//把这个点的入度减一，如果入度变成了0，那么添加到入度0的队列里
                    queue.add(node);
                }
            }
            cnt++;
        }
        if (cnt != mVerticeCount) {//检查是否有环，理论上拿出来的点的次数和点的数量应该一致，如果不一致，说明有环
            throw new IllegalStateException ("Exists a cycle in the graph");
        }
        return topOrder;
    }
}
