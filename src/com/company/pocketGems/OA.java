package com.company.pocketGems;

import java.util.*;

public class OA {
    //lc435
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;

        Arrays.sort(intervals, (a, b)
                -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);

        int pre = intervals[0][1];
        int count = 0;

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < pre) {
                count++;
                pre = Math.min(pre, intervals[i][1]); //keep the one with min end
            } else {
                pre = intervals[i][1];
            }
        }

        return count;
    }

    //1282
    public List<List<Integer>> groupThePeople(int[] groupSizes) {
        if (groupSizes == null || groupSizes.length == 0) return new ArrayList<>();
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < groupSizes.length; i++) {
            List<Integer> list = map.getOrDefault(groupSizes[i], new ArrayList<>());
            list.add(i);
            if (list.size() == groupSizes[i]) {
                res.add(list);
                map.put(groupSizes[i], new ArrayList<>());
            } else {
                map.put(groupSizes[i], list);
            }
        }

//        for (Map.Entry<Integer, List<Integer>> e : map.entrySet()) {
//            int size = e.getKey();
//            List<Integer> list = e.getValue();
//            if (size == list.size()) {
//                res.add(list);
//            } else {
//                int idx = 0;
//                while (idx + size <= list.size()) {
//                    res.add(list.subList(idx, idx + size));
//                    idx = idx + size;
//                }
//            }
//        }

        return res;
    }

    public int reductor(int[] a, int[] b, int d) {
/**     //BF O(n*m) - TLE
 if (a == null || b == null) return 0;

 int count = 0;
 for (int i = 0; i < a.length; i++) {
 int cur = 1;
 for (int j = 0; j < b.length; j++) {
 if (Math.abs(a[i] - b[j]) <= d) {
 cur = 0;
 break;
 }
 }
 count += cur;
 }

 return count;
 **/

        int count = 0;
        Arrays.sort(b);
        for (int i = 0; i < a.length; i++) {
            int idx = Arrays.binarySearch(b, a[i]);
            if (idx >= 0) continue; //found the same num -> abs == 0
            idx = -idx - 1;
            if ((idx == b.length || Math.abs(a[i] - b[idx]) > d)
                    && (idx == 0 || Math.abs(a[i] - b[idx - 1]) > d)) {
                System.out.println(a[i]);
                count++;
            }
        }
        return count;
    }

    //1761
    public int bestTrio(int friendsNodes, List<Integer> friendsFrom, List<Integer> friendTo) {
        boolean[][] connected = new boolean[friendsNodes + 1][friendsNodes + 1];
        Map<Integer, Integer> map = new HashMap<>();
        int n = friendsFrom.size();

        for (int i = 0; i < n; i++) {
            int a = friendsFrom.get(i);
            int b = friendTo.get(i);
            map.put(a, map.getOrDefault(a, 0) + 1);
            map.put(b, map.getOrDefault(b, 0) + 1);
            connected[a][b] = true;
            connected[b][a] = true;
        }

        int min = Integer.MAX_VALUE;
        for (int i = 1; i <= friendsNodes; i++) {
            for (int j = i + 1; j <= friendsNodes; j++) {
                for (int k = j + 1; k <= friendsNodes; k++) {
                    if (connected[i][j] && connected[i][k] && connected[j][k]) {
                        int cur = map.get(i) + map.get(j) + map.get(k) - 6;
                        min = Math.min(min, cur);
                    }
                }
            }
        }

        return min == Integer.MAX_VALUE ? -1 : min;
    }


    public int ways(int total, int k) {
//        dp[8][2] = dp[7][2] + dp[8][1]
        return -1;
    }

    //1143
    public int longestCommonSubsequence(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        int[][] dp = new int[m + 1][n + 1];
        //i == j dp[i][j] = 1 + dp[i-1][j-1]
        //       dp[i][j] = max(dp[i-1][j], dp[i][j-1])
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    dp[i + 1][j + 1] = 1 + dp[i][j];
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        return dp[m][n];
    }

    int res;

    public int bestSumAnyTreePath(int[] parent, int[] value) {
        TreeNode[] nodes = new TreeNode[parent.length];

        for (int i = 0; i < parent.length; i++) {
            TreeNode cur = new TreeNode(i, value[i]);
            nodes[i] = cur;
            if (parent[i] != -1) {
                //input parent has to be valid
                TreeNode root = nodes[parent[i]];
                root.children.add(cur);
            }
        }
        System.out.println(nodes[0]);
        res = Integer.MIN_VALUE;
        dfs(nodes[0]);

        return res;
    }

    private int dfs(TreeNode root) {
        if (root == null) return 0;

        int first = Integer.MIN_VALUE;
        int second = Integer.MIN_VALUE;
        for (TreeNode next : root.children) {
            int n = dfs(next);
            if (n > first) {
                second = first;
                first = n;
            } else if (n > second) {
                second = n;
            }
        }

        int maxNext = first == Integer.MIN_VALUE ? 0 : first;
        int oneChildRoot = Math.max(root.weight, root.weight + maxNext);
        int curRoot = Math.max(oneChildRoot, oneChildRoot + (second == Integer.MIN_VALUE ? 0 : second));

        res = Math.max(res, curRoot);

        return oneChildRoot;
    }

    public int jungleBook(int[] arr) {
        int n = arr.length;
        List<Integer>[] list = new List[n];
        Queue<Integer> q = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            if (arr[i] == -1) {
                q.offer(i);
            } else {
                if (list[arr[i]] == null) {
                    list[arr[i]] = new ArrayList<>();
                }
                list[arr[i]].add(i);
            }
        }

        int depth = 0;

        while (!q.isEmpty()) {
            depth++;
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int idx = q.poll();
                if (list[idx] == null) continue;
                for (int k : list[idx]) {
                    q.offer(k);
                }
            }
        }

        return depth++;
    }

    //lc 740
    public int deleteAndEarn(int[] nums) {
        int n = 10001;
        int[] values = new int[n];
        for (int num : nums)
            values[num] += num;

        int take = 0, skip = 0;
        for (int i = 0; i < n; i++) {
            int takei = skip + values[i];
            int skipi = Math.max(skip, take);
            take = takei;
            skip = skipi;
        }
        return Math.max(take, skip);
    }
    public static void main(String[] args) {
        OA o = new OA();
        int[] p = {-1,0,1};
        System.out.println( o.jungleBook(p));
    }
}

class TreeNode {
    int val;
    int weight;
    List<TreeNode> children;

    public TreeNode() {
    }

    public TreeNode(int val, int weight) {
        this.val = val;
        this.weight = weight;
        this.children = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "val=" + val +
                ", weight=" + weight +
                ", children=" + children +
                '}';
    }
}
