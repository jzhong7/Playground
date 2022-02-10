package com.company;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VO {
    /*
        lc 1293 - hard - bfs with 3d vis
        You are given an m x n integer matrix grid where each cell is either 0 (empty) or 1 (obstacle).
        You can move up, down, left, or right from and to an empty cell in one step.

        Return the minimum number of steps to walk from the upper left corner (0, 0)
        to the lower right corner (m - 1, n - 1) given that you can eliminate at most k obstacles.
        If it is not possible to find such walk return -1.
        */
    public int shortestPath(int[][] grid, int k) {
        int m = grid.length;
        int n = m == 0 ? 0 : grid[0].length;
        boolean[][][] vis = new boolean[m][n][k + 1];
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0, 0, k}); // r,c,k
        vis[0][0][k] = true;

        int steps = 0;
        int[] dir = {0, 1, 0, -1, 0};

        while (!q.isEmpty()) {
            int size = q.size();
            for (int a = 0; a < size; a++) {
                int[] cur = q.poll();
                int r = cur[0];
                int c = cur[1];
                int x = cur[2];
                if (r == m - 1 && c == n - 1) return steps;

                for (int i = 0; i < 4; i++) {
                    int nr = r + dir[i];
                    int nc = c + dir[i + 1];

                    if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;
                    if (grid[nr][nc] == 0) {
                        if (!vis[nr][nc][x]) {
                            q.offer(new int[]{nr, nc, x});
                            vis[nr][nc][x] = true;
                        }
                    } else {
                        if (x >= 1 && !vis[nr][nc][x - 1]) {
                            q.offer(new int[]{nr, nc, x - 1});
                            vis[nr][nc][x - 1] = true;
                        }
                    }
                }
            }

            steps++;
        }

        return -1;
    }

    /*
        LC 1937. Maximum Number of Points with Cost
        You are given an m x n integer matrix points (0-indexed).
        Starting with 0 points, you want to maximize the number of points you can get from the matrix.

        To gain points, you must pick one cell in each row.
        Picking the cell at coordinates (r, c) will add points[r][c] to your score.

        However, you will lose points if you pick a cell too far from the cell that you picked in the previous row.
        For every two adjacent rows r and r + 1 (where 0 <= r < m - 1),
        picking cells at coordinates (r, c1) and (r + 1, c2) will subtract abs(c1 - c2) from your score.

        Return the maximum number of points you can achieve.
            */
    public long maxPoints(int[][] points) {
        int m = points.length;
        int n = m == 0 ? 0 : points[0].length;
        long[] dp = new long[n];
        long res = Integer.MIN_VALUE;

        for (int i = 0; i < m; i++) {
            change(dp);
            for (int j = 0; j < n; j++) {
                dp[j] += points[i][j];
                if (i == m - 1) {
                    res = Math.max(res, dp[j]);
                }
            }
        }
        return res;
    }

    private void change(long[] dp) {
        for (int i = 1; i < dp.length; i++) {
            dp[i] = Math.max(dp[i], dp[i - 1] - 1);
        }
        for (int i = dp.length - 2; i >= 0; i--) {
            dp[i] = Math.max(dp[i], dp[i + 1] - 1);
        }
    }

    // LC 931
    public int minFallingPathSum(int[][] matrix) {
        int res = Integer.MAX_VALUE;
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < matrix[0].length; i++) {
            res = Math.min(res, dfs(matrix, 0, i, map));
        }
        System.out.println(map);
        return res;
    }

    private int dfs(int[][] matrix, int r, int c, Map<String, Integer> map) {
        if (r >= matrix.length) return 0;
        if (c < 0 || c >= matrix[0].length) return Integer.MAX_VALUE;
        if (map.containsKey(r + "-" + c)) return map.get(r + "-" + c);

        int val = 0;
        int l1 = dfs(matrix, r + 1, c + 1, map);
        int l2 = dfs(matrix, r + 1, c, map);
        int l3 = dfs(matrix, r + 1, c - 1, map);
        val = matrix[r][c] + Math.min(Math.min(l1, l2), l3);
        map.put(r + "-" + c, val);
        return val;
    }

    /*
        LC 2007. Find Original Array From Doubled Array
        An integer array original
        is transformed into a doubled array changed by appending twice the value of every element in original,
        and then randomly shuffling the resulting array.

        Given an array changed, return original if changed is a doubled array.
        If changed is not a doubled array, return an empty array. The elements in original may be returned in any order.
        */
    public int[] findOriginalArray(int[] changed) {
        int n = changed.length;
        if (n % 2 != 0) return new int[0];

        Map<Integer, Integer> map = new TreeMap<>(); //<num, freq>
        for (int c : changed) map.put(c, map.getOrDefault(c, 0) + 1);
        int[] res = new int[n / 2];
        int idx = 0;

        for (int key : map.keySet()) {
            if (map.get(key) > map.getOrDefault(key + key, 0)) {
                return new int[0];
            }
            for (int i = 0; i < map.get(key); i++) {
                res[idx++] = key;
                map.put(key + key, map.get(key + key) - 1);
            }
        }

        return res;
    }

    // LC 954
    public boolean canReorderDoubled(int[] arr) {
        Map<Integer, Integer> map = new TreeMap<>();
        for (int n : arr) map.put(n, map.getOrDefault(n, 0) + 1);

        for (int key : map.keySet()) {
            if (map.get(key) == 0) {
                continue;
            }
            int want = key < 0 ? key / 2 : key * 2;
            if ((key < 0 && key % 2 != 0) || map.get(key) > map.getOrDefault(want, 0)) return false;
            map.put(want, map.get(want) - map.get(key));
        }

        return true;
    }

    // LC 1971. Find if Path Exists in Graph
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        if (source == destination) return true;
        if (edges == null || edges.length == 0) return false;
        List<Integer>[] list = new List[n];
        for (int i = 0; i < n; i++) {
            list[i] = new ArrayList<>();
        }
        for (int[] e : edges) {
            list[e[1]].add(e[0]);
            list[e[0]].add(e[1]);
        }

        boolean[] vis = new boolean[n];
        Queue<Integer> q = new LinkedList<>();
        q.add(source);
        vis[source] = true;

        while (!q.isEmpty()) {
            int cur = q.poll();
            if (cur == destination) return true;

            for (int next : list[cur]) {
                if (!vis[next]) {
                    vis[next] = true;
                    q.add(next);
                }
            }
        }

        return false;
    }

    // LC 224 calculate
    public int calculate(String s) {

        Stack<Integer> st = new Stack<>();
        int res = 0;
        int sign = 1;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                int cur = c - '0';
                while (i + 1 < s.length() && Character.isDigit(s.charAt(i + 1))) {
                    cur = cur * 10 + s.charAt(i + 1) - '0';
                    i++;
                }
                res += sign * cur;
            } else if (c == '+') {
                sign = 1;
            } else if (c == '-') {
                sign = -1;
            } else if (c == '(') {
                st.push(res);
                st.push(sign);
                res = 0;
                sign = 1;
            } else if (c == ')') {
                res = res * st.pop() + st.pop();
            }
        }
        return res;
    }
/*

    LC 1376. Time Needed to Inform All Employees
    A company has n employees with a unique ID for each employee from 0 to n - 1.
    The head of the company is the one with headID.

    Each employee has one direct manager given in the manager array where manager[i] is the direct manager of the i-th employee,
    manager[headID] = -1. Also, it is guaranteed that the subordination relationships have a tree structure.

    The head of the company wants to inform all the company employees of an urgent piece of news.
    He will inform his direct subordinates, and they will inform their subordinates,
    and so on until all employees know about the urgent news.

    The i-th employee needs informTime[i] minutes to inform all of his direct subordinates
    (i.e., After informTime[i] minutes, all his direct subordinates can start spreading the news).

    Return the number of minutes needed to inform all the employees about the urgent news.
*/

    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        if (n == 0) return 0;
        Map<Integer, List<Integer>> map = new HashMap<>(); // <manager, employee>
        for (int i = 0; i < manager.length; i++) {
            List<Integer> list = map.getOrDefault(manager[i], new ArrayList<>());
            list.add(i);
            map.put(manager[i], list);
        }

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{headID, 0}); //<id, time>
        int res = Integer.MIN_VALUE;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int id = cur[0];
            int time = cur[1];

            if (!map.containsKey(id)) {
                res = Math.max(res, time);
            } else {
                for (int next : map.get(id)) {
                    q.add(new int[]{next, time + informTime[id]});
                }
            }
        }
        return res;
    }

    /*
        LC 1552. Magnetic Force Between Two Balls

        In the universe Earth C-137,
        Rick discovered a special form of magnetic force between two balls if they are put in his new invented basket.
        Rick has n empty baskets, the ith basket is at position[i],
        Morty has m balls and needs to distribute the balls into the baskets
        such that the minimum magnetic force between any two balls is maximum.

        Rick stated that magnetic force between two different balls at positions x and y is |x - y|.

        Given the integer array position and the integer m. Return the required force.
        */
    public int maxDistance(int[] position, int m) {
        Arrays.sort(position);
        int lo = 1;
        int hi = position[position.length - 1] - position[0];
        int res = -1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (check(position, mid, m)) {
                res = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        return res;
    }

    private boolean check(int[] position, int target, int m) {
        int pre = -1;
        int idx = 0;

        while (idx < position.length) {
            if (pre == -1 || position[idx] - position[pre] >= target) {
                pre = idx;
                m--;
            }
            if (m == 0) return true;
            idx++;
        }

        return m == 0;
    }

    /*
        LC 1048. Longest String Chain
        You are given an array of words where each word consists of lowercase English letters.

        wordA is a predecessor of wordB if and only if we can insert exactly one letter anywhere in wordA
        without changing the order of the other characters to make it equal to wordB.

        For example, "abc" is a predecessor of "abac", while "cba" is not a predecessor of "bcad".
        A word chain is a sequence of words [word1, word2, ..., wordk] with k >= 1,
        where word1 is a predecessor of word2, word2 is a predecessor of word3,
        and so on. A single word is trivially a word chain with k == 1.

        Return the length of the longest possible word chain with words chosen from the given list of words.

        */
    public String longestStrChain(String[] words) {
        int res = 0;
        Map<String, Integer> map = new HashMap<>(); //ending word, length of the chain
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        String ans = "";

        for (String w : words) {
            int best = 0;
            for (int i = 0; i < w.length(); i++) {
                String sub = w.substring(0, i) + w.substring(i + 1);
                int len = map.getOrDefault(sub, 0) + 1;
                best = Math.max(best, len);
            }
            map.put(w, best);
//            res = Math.max(res, best);
            if (res < best) {
                res = best;
                ans = w;
            }
        }

        return ans;
    }

    //Given a list of Strings and a prefix, find number of strings that contain the prefix
    public int getNumberOfPrefix(String[] strs, String prefix) {
        Trie t = new Trie();
        for (String s : strs) {
            t.insert(s);
        }
        return t.find(prefix);
    }

    public static void main(String[] args) {
        VO vo = new VO();
        int[][] g = {{2, 1, 3}, {6, 5, 4}, {7, 8, 9}};
        int[] arr = {-5, -2};
        String[] dict = {"apk", "app", "apple", "arp", "array"};
        System.out.println(vo.getNumberOfPrefix(dict, "ar"));

    }

}

class TrieNode {
    TrieNode[] children;
    int count;
    boolean isEnd;

    public TrieNode() {
        this.children = new TrieNode[256];
        this.count = 0;
        this.isEnd = false;
    }
}

class Trie {
    TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    public void insert(String s) {
        TrieNode cur = root;

        for (char c : s.toCharArray()) {
            if (cur.children[c] == null) {
                cur.children[c] = new TrieNode();
            }
            cur = cur.children[c];
            cur.count++;
        }
        cur.isEnd = true;
    }

    public int find(String prefix) {
        TrieNode cur = root;
        for (char c : prefix.toCharArray()) {
            if (cur.children[c] == null) {
                return -1;
            }
            cur = cur.children[c];
        }
        return cur.count;
    }
}

class FindBadCommits {
    private static int[] commits = {0, 0, 0, 1, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 5, 5, 6, 6, 6};

    public static void main(String[] args) {
        FindBadCommits fbc = new FindBadCommits();
        System.out.println(fbc.findBadCommits(commits.length));
    }

    public List<Integer> findBadCommits(int n) {
        List<Integer> result = new LinkedList<>();
        binarySearch(0, n - 1, result);
        return result;
    }

    private void binarySearch(int l, int r, List<Integer> result) {
        if (l == r) {
            return;
        }
        if (l == r - 1) {
            if (isWorse(l, r)) {
                result.add(r);
            }
            return;
        }
        int m = l + (r - l) / 2;
        if (isWorse(l, m)) {
            binarySearch(l, m, result);
        }
        binarySearch(m, r, result);
    }

    private boolean isWorse(int i, int j) {
        //assert i, j < commits.length
        return commits[j] > commits[i];
    }
}


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

}

class Solution {
    //find leaves and delete
    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> resList = new ArrayList<>();
        while (root != null) {
            List list = new ArrayList<>();
            root = recur(root, list);
            resList.add(list);
        }
        return resList;
    }

    /**
     * 如果 root 是叶子节点，则把它装入到 list 中，并且给上一级返回 null，表示自己被删除
     * 如果 root 不是叶子节点，则递归 root 的左子节点和右子节点，并返回 root 给上一级，表明自己没有被删除
     *
     * @param root
     * @param list
     * @return
     */
    private TreeNode recur(TreeNode root, List<Integer> list) {
        if (root == null) {
            return null;
        }
        if (root.left == null && root.right == null) {
            list.add(root.val);
            return null;
        }
        root.left = recur(root.left, list);
        root.right = recur(root.right, list);
        return root;
    }

    public static void main(String[] args) {
        String s = "101010";
        String t = "00011";
        char[] c = s.toCharArray();
        char[] c2 = t.toCharArray();


        int[] c1 = {0,0,1,0,0};
        for (int i = 0; i < c1.length; i++) {
            c1[i] ^= 1;
        }
        System.out.println(Arrays.toString(c1));
        TreeNode t5 = new TreeNode(5);
        TreeNode t4 = new TreeNode(4);
        TreeNode t3 = new TreeNode(3);
        TreeNode t2 = new TreeNode(2, t4, t5);
        TreeNode t1 = new TreeNode(1, t2, t3);
        System.out.println(new Solution().findLeaves(t1));

    }
}

// song list
class RandomizedSet {
    List<Integer> list;
    Random rd;
    Map<Integer, Integer> map;
    /** Initialize your data structure here. */
    public RandomizedSet() {
        this.list = new ArrayList<>();
        this.rd = new Random();
        this.map = new HashMap<>(); //<val, index>
    }

    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if (map.containsKey(val)) return false;
        int idx = list.size();
        map.put(val, idx);
        list.add(val);
        return true;
    }

    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {

        if (!map.containsKey(val)) return false;
        int idx = map.get(val);
        int last = list.size() - 1; // last idx
        if (last != idx) {
            map.put(list.get(last), idx);
            list.set(idx, list.get(last));
        }
        list.remove(last);
        map.remove(val);
        return true;
    }

    /** Get a random element from the set. */
    public int getRandom() {
        return list.get(rd.nextInt(list.size()));
    }

    static class Song {
        int[] songs;
        Map<Integer, Integer> map; // song, idx
        Random rd;
        int range;
        int idx;
        int k;

        private Song(int[] songs, int k) {
            this.songs = songs;
            this.range = songs.length;
            this.idx = 0;
            this.k = k;
            this.rd = new Random();
        }

        private int play() {
            int i = rd.nextInt(range);
            if (range > songs.length - k + 1) range--;
            swap(i, songs.length - 1 - idx);
            idx = (idx + 1) % (k - 1);
            return songs[i];
        }

        private void swap(int i, int j) {
            int temp = songs[i];
            songs[i] = songs[j];
            songs[j] = temp;
        }

        public static boolean compare(String s, String t) {
            int[] sc = new int[26];
            for (char c : s.toCharArray()) {
                sc[c-'a']++;
            }

            int[] tc = new int[26];
            for (char c : t.toCharArray()) {
                tc[c-'a']++;
            }
            int count = 0;
            System.out.println(Arrays.toString(sc));
            System.out.println(Arrays.toString(tc));
            for (int i = 0; i < 26; i++) {
                if (sc[i] != 0 && tc[i] != 0) {
                    continue;
                }
                if (sc[i] != 0 || tc[i] != 0) count++;
            }
            System.out.println(count);
            return count == 1;
        }

        public static void main(String[] args) {
//            int[] songs = {0,1,2,3,4,5,6,7,8,9};
//            Song s = new Song(songs, 5);
//            for (int i = 0; i < 50; i++) {
//                s.play();
//            }
            System.out.println(compare("abc", "ad"));

        }
    }
}