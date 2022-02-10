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

    //build n-ary tree
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

    public List<Integer> deliverySystem(int cityNode, int[] cityFrom, int[] cityTo, int company) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < cityFrom.length; i++) {
            List<Integer> list = map.getOrDefault(cityFrom[i], new ArrayList<>());
            list.add(cityTo[i]);
            map.put(cityFrom[i], list);
        }

        Queue<Integer> q = new LinkedList<>(); // sort?
        q.add(company);
        List<Integer> res = new ArrayList<>();
        boolean[] vis = new boolean[cityNode+1];
        vis[company] = true;

        while (!q.isEmpty()) {
            int size = q.size();
            for (int k = 0; k < size; k++) {
                int node = q.poll();
                List<Integer> cur = map.getOrDefault(node, new ArrayList<>());
                Collections.sort(cur);
                for (int next : cur) {
                    if (!vis[next]) {
                        res.add(next);
                        q.add(next);
                        vis[next] = true;
                    }
                }
            }
        }

        return res;
    }
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Deque<Integer> deque = new ArrayDeque<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums2) {
            while (!deque.isEmpty() && n > deque.peekLast()) map.put(deque.pollLast(), n);
            deque.addLast(n);
        }

        for (int i = 0; i < nums1.length; i++) {
            nums1[i] = map.getOrDefault(nums1[i], -1);
        }
        return nums1;
    }
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Arrays.fill(res, -1);
        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < 2*n; i++) {
            while (!deque.isEmpty() && nums[i%n] > nums[deque.peekLast()]) {
                res[deque.pollLast()] = nums[i%n];
            }
            deque.addLast(i%n);
        }
        return res;
    }
    public String expressionExpand(String s) {
        Queue<Character> q = new LinkedList<>();
        for (char c : s.toCharArray()) q.add(c);
        return expressionExpand(q);
    }
    public String expressionExpand(Queue<Character> q) {
        // write your code here
        if (q == null || q.isEmpty()) return null;
        StringBuilder sb = new StringBuilder();
        int num = 0;
        while (!q.isEmpty()) {
            char c = q.poll();
            if (Character.isDigit(c)) {
                num = num * 10 + (c -'0');
            } else if (c == '[') {
                String next = expressionExpand(q);
                for (int k = 0; k < num; k++) {
                    sb.append(next);
                }
                num = 0;
            } else if (c == ']') {
                return sb.toString();
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public String fractionToDecimal(int numerator, int denominator) {
        // write your code here
        if (numerator == 0) return "0";
        StringBuilder sb = new StringBuilder();

        //sign
        sb.append(((numerator < 0) ^ (denominator < 0)) ? "-" : "");

        int num = Math.abs(numerator);
        int den = Math.abs(denominator);
        sb.append(num/den);
        num %= den;
        if (num == 0) return sb.toString();

        sb.append(".");
        Map<Integer, Integer> map = new HashMap<>(); // k,v -> cur remainder, idx
        map.put(num, sb.length());
        while (num != 0) {
            num *= 10;
            sb.append(num/den);
            num %= den;
            if (map.containsKey(num)) {
                sb.insert(map.get(num), "(");
                sb.append(")");
                return sb.toString();
            }
            map.put(num, sb.length());
        }
        return sb.toString();
    }

    public int getNumber(int k, List<Integer> scores) {
        if (k >= scores.size()) return scores.size();
        TreeMap<Integer, Integer> map = new TreeMap<>((a,b) -> b - a);
        for (int s : scores) {
            map.put(s, map.getOrDefault(s, 0) + 1);
        }
        int count = 0;

        for (int key : map.keySet()) {
//            System.out.println(key);
            count += map.get(key);
            k--;
            if (k == 0) return count;
        }
        return count;
    }
    public static void main(String[] args) {
        OA o = new OA();

        int[] a = {1,1,2,3,1};
        int[] b = {2,3,4,5,5};
        System.out.println(o.getNumber(3, Arrays.asList(1,1,1,2,2)));
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

/**

 You are asked to cut off trees in a forest for a golf event. The forest is represented as a non-negative 2D map, in this map:
 1  0 represents a pond can't be reached.
 2  1 represents the ground can be walked through.
 3  > 1 represents a tree can be walked through, and this positive number represents the tree's height.
 In one step you can walk in any of the four directions top, bottom, left and right also when standing in a point which is a tree you can decide whether or not to cut off the tree.
 You are asked to cut off all the trees in this forest in the order of tree's height.
 You will start from the point (0, 0) and you should output the minimum steps you need to walk to cut off all the trees.


 [1, 0, 1],
 [0, 2, 0]

 1

 */



/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class Tree {
    int x;
    int y;
    int height;

    public Tree(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
    }
}

class Point {
    int x;
    int y;
    int step;
    boolean visited;
    public Point(int x, int y, int step) {
        this.x = x;
        this.y = y;
        this.step = step;
    }

    public String toString() {

        return String.valueOf(x) + String.valueOf(y) + String.valueOf(step);
    }

}

class Solution {
    public int cutTree(int[][] map) {
        int res = 0;
        List<Tree> trees = new ArrayList<>();
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[0].length; j++) {
                if(map[i][j] > 1) {
                    Tree t = new Tree(i, j, map[i][j]);
                    trees.add(t);
                }
            }
        }

        Collections.sort(trees,(t1, t2) -> t1.height - t2.height);



        int s = getStep(new Tree(0,0,1), trees.get(0), map);
        if(s == -1) {
            return -1;
        }


        res += s;

        for(int i = 0; i < trees.size() - 1; i++) {
            int step =  getStep(trees.get(i), trees.get(i + 1), map);
            if(step == -1) {
                return -1;
            }
            res += step;
        }

        return res;

    }

    private int getStep(Tree t1, Tree t2, int[][] map) {
        int res = Integer.MAX_VALUE;
        int l = map.length;
        int w = map[0].length;
        Queue<Point> queue = new LinkedList<Point>();
        int[] dr1 = new int[]{1, -1, 0, 0};
        int[] dr2 = new int[]{0, 0, 1, -1};
        int[] visited = new int[l * w];

        queue.offer(new Point(t1.x, t1.y, 0));
        visited[t1.x * w + t1.y] = 1;
        while(!queue.isEmpty()) {
            int size = queue.size();
            for(int i = 0; i < size; i++) {
                Point p = queue.poll();
                // System.out.println(p);
                for(int k = 0; k < 4; k++) {
                    int x = p.x + dr1[k];
                    int y = p.y + dr2[k];

                    if(x == t2.x && y == t2.y && p.step + 1 < res) {
                        res = p.step + 1;
                    }

                    if(x >= 0 && x < map.length && y >= 0 && y < map[0].length && map[x][y] > 0 && visited[x * w + y] == 0 ) {
                        queue.offer(new Point(x, y, p.step + 1));
                        visited[x * w + y] = 1;
                    }
                }
            }
        }

        if(res == Integer.MAX_VALUE) {
            return -1;
        }
        return res;

    }


    //   [1, 0, 1],
// [0, 2, 0]
    public static void main(String[] args) {
        int[][] map = new int[][] {
                {1,1,1}, {0,2,0}
        };


        int[][] A = new int[][] {{1,1,1}
                                ,{1,0,2}
                                ,{2,1,1}};

        int[][] B = new int[][] {{1}};
        int[][] C = new int[][] {{2}};

        int[][] D = new int[][] {{4,0,1}
                ,{1,2,1}
                ,{1,1,3}};

        int[][] E = new int[][] {{1,0,1}
                ,{1,0,1}
                ,{1,0,3}};

        int[][] F = new int[][] {{1,0,1,1,0,1}
                ,{1,0,1,1,0,1}
                ,{1,0,3,1,1,4}};


        System.out.println(new Solution().cutTree(A));
        // System.out.println(new Solution().cutTree(B));
        // System.out.println(new Solution().cutTree(C));
//        System.out.println(new Solution().cutTree(D));
//        System.out.println(new Solution().cutTree(E));
//        System.out.println(new Solution().cutTree(F));

    }
}
