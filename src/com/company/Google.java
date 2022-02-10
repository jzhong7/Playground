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
class Main {
    static long triplets(long t, List<Integer> d) {
        Collections.sort(d);
        long count = 0;

        for (int i = 0; i < d.size() - 2; i++) {
            int lo = i + 1;
            int hi = d.size() - 1;
            while (lo < hi) {
                if (d.get(lo) + d.get(hi) + d.get(i) > t) {
                    hi--;
                } else {
                    count += (hi - lo);
                    lo++;
                }
            }
        }

        return count;
    }

    public static String compressString(String s) {
        if (s == null || s.length() <= 1) return s;
        int count = 1;
        StringBuilder sb = new StringBuilder();
        char pre = ' ';
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (i == 0) {
                pre = c;
            } else  {
                if (c == pre) {
                    count++;
                } else {
                    sb.append(pre);
                    if (count != 1) sb.append(count);
                    pre = c;
                    count = 1;
                }
            }
        }

        sb.append(pre);
        if (count != 1) sb.append(count);

        return sb.toString();
    }

    public static int circularArray(int n, List<Integer> endNode) {
        int size = endNode.size();
        Map<Integer, Integer> map = new HashMap<>();
        int start = endNode.get(0);
        int end = endNode.get(size-1);

        for (int i = 0; i < size; i++) {
            map.put(endNode.get(i), map.getOrDefault(endNode.get(i), 0) + 1);
        }
        System.out.println(map);
        for (Map.Entry e : map.entrySet()) {
            if ((int) e.getKey() >= end) {
                map.put((int) (e.getKey()), map.get((int) (e.getKey())) - 1);
            }
            if ((int) e.getKey() > start) {
                map.put((int) (e.getKey()), map.get((int) (e.getKey())) + 1);
            }
        }

        int max = 0;
        int res = 0;

        for (Map.Entry e : map.entrySet()) {
            if (max < (int) (e.getValue())
                    || (max == (int) (e.getValue()) && res > (int) (e.getKey()))) {
                max = (int) e.getValue();
                res = (int) e.getKey();
            }
        }
        System.out.println(map);
        return res;
    }

    //skyline - sweep line
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<int[]> list = new ArrayList<>();
        for (int[] b : buildings) {
            list.add(new int[] {b[0], b[2]});
            list.add(new int[] {b[1], -b[2]});
        }

        Collections.sort(list, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);

        Queue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        pq.add(0);
        List<List<Integer>> res = new ArrayList<>();
        for (int[] l : list) {
            int x = l[0];
            int h = l[1];

            if (h > 0) {
                if (h > pq.peek()) {
                    res.add(Arrays.asList(x, h));
                }
                pq.add(h);
            } else {
                pq.remove(-h);
                if (-h > pq.peek()) {
                    res.add(Arrays.asList(x, pq.peek()));
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        List<List<Integer>> edges = List.of(List.of(0, 1), List.of(1,2), List.of(2,3));
//        System.out.println(solve(new int[] {1,2,3,4}, edges));
        System.out.println(new Main().calculate("(2+6*3+5-(3*14/7 + 2)*5)+3"));
//        System.out.println(new Main().maxExcitements(new int[] {1,2,3,4}, edges));

        String[] d = {"0201","0101","0102","1212","2002"};
//        System.out.println(new Main().openLock(d, "0202"));
    }

    //given list of cities with excitements, return max excitements of 4 cities
    public int maxExcitements(int[] excitements, List<List<Integer>> edges) {
        if (excitements == null || edges == null) return 0;

        Map<Integer, List<int[]>> graph = new HashMap<>(); // <city, <city, excitement>>
        for (List<Integer> e : edges) {
            List<int[]> l1 = graph.getOrDefault(e.get(0), new ArrayList<>());
            l1.add(new int[] {e.get(1), excitements[e.get(1)]});
            graph.put(e.get(0), l1);

            List<int[]> l2 = graph.getOrDefault(e.get(1), new ArrayList<>());
            l2.add(new int[] {e.get(0), excitements[e.get(0)]});
            graph.put(e.get(1), l2);
        }

        for (Integer key : graph.keySet()) {
            graph.put(key, topK(graph.get(key), 3));
        }
        int res = 0;
        for (List<Integer> e : edges) {
            Set<Integer> set = new HashSet<>();
            set.add(e.get(0));
            set.add(e.get(1));
            for (int[] u : graph.get(e.get(0))) {
                if (set.contains(u[0])) continue;
                for (int[] v : graph.get(e.get(1))) {
                    if (set.contains(v[0])) continue;
                    if (u[0] != v[0]) {
                        int cur = excitements[e.get(0)] + excitements[e.get(1)] +  v[1] + u[1];
                        res = Math.max(res, cur);
                    }
                }
            }
        }

        return res;
    }

    private List<int[]> topK(List<int[]> list, int k) {
        if (list == null || list.size() <= k) return list;
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        for (int[] l : list) {
            if (!pq.isEmpty() && pq.peek()[1] < l[1]) {
                pq.poll();
            }
            pq.add(l);
        }
        return list;
    }

    //ring lock
    public int openLock(String[] deadends, String target) {
        if (target == "0000") return 0;

        Map<String, Integer>  map = new HashMap<>();// 0 -> un-visited, 1 -> up, -1 -> down, 2 -> dead end
        for (String d : deadends) {
            map.put(d, 2);
        }

        if (map.containsKey(target) || map.containsKey("0000")) return -1;

        int dir = 1;
        Queue<String> forward = new LinkedList<>();
        Queue<String> backward = new LinkedList<>();
        forward.add("0000");
        backward.add(target);
        map.put("0000", 1);
        map.put(target, -1);
        int count = 1;

        while (!forward.isEmpty() && !backward.isEmpty()) {
            if (forward.size() > backward.size()) {
                dir = -dir;
                Queue<String> temp = backward;
                backward = forward;
                forward = temp;
            }

            int size = forward.size();
            for (int i = 0; i < size; i++) {
                String cur = forward.poll();
                for (int k = 0; k < 4; k++) {
                    char c = cur.charAt(k);
                    String up = cur.substring(0, k) + (c == '9' ? 0 : c - '0' + 1) + cur.substring(k + 1);
                    String down = cur.substring(0, k) + (c == '0' ? 9 : c - '0' - 1) + cur.substring(k + 1);

                    if (map.getOrDefault(up, 0) == -dir || map.getOrDefault(down, 0) == -dir) {
                        return count;
                    }

                    if (!map.containsKey(up)) {
                        map.put(up, dir);
                        forward.add(up);
                    }
                    if (!map.containsKey(down)) {
                        map.put(down, dir);
                        forward.add(down);
                    }

                }

            }
            count++;
        }
        return -1;
    }

    private int index = 0;
    public int calculate(String s) {
        char[] ch = s.toCharArray();
        return cal(ch);
    }

    private int cal(char[] ch) {
        Stack<Integer> st = new Stack<>();
        int num = 0;
        char sign = '+';
        for (; index < ch.length; index++) {
            char c = ch[index];
            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            }

            if (c == '(') {
                index++;
                num = cal(ch);
                System.out.println(num);
            }

            if ((!Character.isDigit(c) && c != ' ') || index == ch.length - 1) { // sign
                if (sign == '+') {
                    st.push(num);
                } else if (sign == '-') {
                    st.push(-num);
                } else if (sign == '*') {
                    int pre = st.pop();
                    st.push(pre * num);
                } else if (sign == '/') {
                    int pre = st.pop();
                    st.push(pre / num);
                }

                sign  = c;
                num = 0;
            }

            if (c == ')') {
                break;
            }
        }

        int res = 0;
        while (!st.isEmpty()) {
            res += st.pop();
        }
        return res;
    }
}

//basic cal
class Solutiona {
    private int index = 0;
    public int calculate(String s) {
        char[] ch = s.toCharArray();
        return cal(ch);
    }
    private int cal(char[] ch){
        Deque<Integer> stack = new ArrayDeque<>();
        int num = 0;
        char sign = '+';
        for(; index < ch.length; index++){
            char c = ch[index];
            if(Character.isDigit(c)){
                num = num*10 + (c-'0');
            }
            if(c == '('){
                index++;//index指针指到下一个字符
                num = cal(ch);
            }
            //当遇到了新的运算符，就要对上一个运算符sign和累计的数字num作运算
            //空格直接无视，i继续前进
            //遇到字符串末尾，肯定是要结算的
            if(!Character.isDigit(c)&& c != ' ' || index == ch.length-1){
                int pre = 0;
                switch(sign){

                    case '+':
                        stack.push(num);
                        break;
                    case '-':
                        stack.push(-num);
                        break;
                    case '*':
                        pre = stack.pop();
                        stack.push(pre * num);
                        break;
                    case '/':
                        pre = stack.pop();
                        stack.push(pre / num);
                        break;
                }
                sign = c;
                num = 0;//计数归位
            }
            if(c == ')') break;//阶段，后面开始计算局部结果，返回
        }

        int res = 0;
        while(!stack.isEmpty()){
            res += stack.pop();
        }
        return res;
    }
}

class Event {
    int x;
    String s;
    int start;

    public Event(int x, String s, int start) {
        this.x = x;
        this.s = s;
        this.start = start;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + s + ", " + start + "]";
    }
}
class Annotation {
    int x;
    int y;
    String annotation;

    public Annotation(int x, int y, String annotation) {
        this.x = x;
        this.y = y;
        this.annotation = annotation;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + annotation + "]";
    }

    //intervals
    public static List<Annotation> getIntervals(List<Annotation> list) {
        if (list == null || list.size() <= 1) return list;
        List<Annotation> res = new ArrayList<>();

        List<Event> events = new ArrayList<>();
        for (Annotation a : list) {
            events.add(new Event(a.x, a.annotation, 1));
            events.add((new Event(a.y, a.annotation, -1)));
        }

        Collections.sort(events, (a, b) -> a.x == b.x ? b.start - a.start : a.x - b.x);
        System.out.println(events);

        int pre = -1;
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < events.size(); i++) {
            Event e = events.get(i);
            int lo = e.x;
            String s = e.s;
            int start = e.start;

            if (i == 0) {
                pre = lo;
                ans.add(s);
            } else {
                res.add(new Annotation(pre, lo, String.join("", ans)));
                if (start == 1) {
                    ans.add(s);
                } else {
                    ans.remove(s);
                }
                pre = lo;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        List<Annotation> list = Arrays.asList(new Annotation(0, 30, "A"),
                new Annotation(5, 10, "B"),
                new Annotation(15, 20, "C"),
                new Annotation(12, 28, "D"));

        System.out.println(getIntervals(list));


    }

}

class ArrayTree {
    public static void remove(List<Node> nodes, int idx) {
        List<Integer>[] list = new List[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            list[i] = new ArrayList<>();
        }

        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).parent == -1) continue;
            list[nodes.get(i).parent].add(i);
        }

        if (idx < 0 || idx >= nodes.size()) return;

        if (list[idx] != null && list[idx].size() != 0) {
            int p = nodes.get(idx).parent;
            for (int next : list[idx]) {
//                nodes.get(next).parent = p;
                remove(nodes, next);
            }
        }

        nodes.get(idx).val = -1;
        nodes.get(idx).parent = -1;


    }


    public static void main(String[] args) {
        List<Node> arr = Arrays.asList(new Node(1, -1),
                new Node(2, 0),
                new Node(3, 0),
                new Node(4, 1),
                new Node(5, 1));
        remove(arr, 0);
        System.out.println(arr);
    }

    static class Node {
        int val;
        int parent;

        public Node(int val, int parent) {
            this.val = val;
            this.parent = parent;
        }

        @Override
        public String toString() {
            return "[" + val +
                    " " + parent +
                    ']';
        }
    }
}

//waitlist - LRU implement
class WaitList {
    int largeCap;
    int smallCap;
    Map<Integer, Party> map; // <partyId, party>
    int largeTaken;
    int smallTaken;
    Party largeHead;
    Party smallHead;
    Party largeTail;
    Party smallTail;

    public WaitList(int largeCap, int smallCap) {
        this.largeCap = largeCap;
        this.smallCap = smallCap;
        this.largeTaken = 0;
        this.smallTaken = 0;
        this.largeHead = new Party(-1, -1);
        this.largeTail = new Party(-1, -1);
        largeHead.next = largeTail;
        largeTail.pre = largeHead;

        this.smallHead = new Party(-1, -1);
        this.smallTail = new Party(-1, -1);
        smallHead.next = smallTail;
        smallTail.pre = smallHead;

        this.map = new HashMap<>();

    }

    public boolean join(Party p) {
        if (largeTaken + smallTaken >= largeCap + smallCap) return false;
        int size = p.size;
        if (size == 2) {
            if (largeTaken >= largeCap) return false;
            takeLarge(p);
            map.put(p.id, p);
        } else {
            if (smallTaken >= smallCap) {
                takeLarge(p);
                map.put(p.id, p);
            } else {
                takeSmall(p);
                map.put(p.id, p);
            }
        }
        return true;
    }

    private void takeSmall(Party p) {
        p.taken = 1;
        smallHead.next.pre = p;
        p.next = smallHead.next;
        p.pre = smallHead;
        smallHead.next = p;
        smallTaken++;
    }

    private void takeLarge(Party p) {
        p.taken = 2;
        largeHead.next.pre = p;
        p.next = largeHead.next;
        p.pre = largeHead;
        largeHead.next = p;
        largeTaken++;
    }

    public boolean remove(int id) {
        if (!map.containsKey(id)) return false;
        Party p = map.get(id);
        int size = p.taken;
        map.remove(id);
        if (size == 2) {
//            removeLarge(p);
            remove(p, true);
        } else {
//            removeSmall(p);
            remove(p, false);
        }
        return true;
    }

//    private void removeSmall(Party p) {
//        p.next.pre = p.pre;
//        p.pre.next = p.next;
//        p =  null;
//        smallTaken--;
//    }
//
//    private void removeLarge(Party p) {
//        p.next.pre = p.pre;
//        p.pre.next = p.next;
//        p =  null;
//        largeTaken--;
//    }

    private void remove(Party p, boolean isLarge) {
        p.next.pre = p.pre;
        p.pre.next = p.next;
        p =  null;
        if (isLarge) {
            largeTaken--;
        } else {
            smallTaken--;
        }
    }

    public boolean popLast(int size) {
        if (size == 2) {
            if (largeTaken == 0) return false;
            pop(true);
        } else {
            if (smallTaken == 0) return false;
            pop(false);
        }

        return true;
    }

    private void pop(boolean isLarge) {
        Party p;
        if (isLarge) {
            p = largeTail.pre;
        } else {
            p = smallTail.pre;
        }
        map.remove(p.id);
        remove(p, isLarge);
    }

    public static void main(String[] args) {
        Party p1 = new Party(1, 1);
        Party p2 = new Party(2, 1);
        Party p3 = new Party(3, 2);
        Party p4 = new Party(4, 2);
        Party p5 = new Party(5, 1);

        WaitList w = new WaitList(3, 2);

        w.join(p1);
        w.join(p2);
        w.join(p3);
        w.join(p4);
        w.join(p5);
        System.out.println(w.largeHead);
        System.out.println(w.smallHead);
//        w.popLast(1);
//        System.out.println(w.largeHead);
//        System.out.println(w.smallHead);
        w.remove(4);
        System.out.println(w.largeHead);
        System.out.println(w.smallHead);
    }
}

class Party {
    int id;
    int size; // small -> 1, larger -> 2
    int taken; // taken table size
    Party pre;
    Party next;

    public Party(int id, int size) {
        this.id = id;
        this.size = size;
    }

    @Override
    public String toString() {
        return "[ " + id + ", " + size + ", " + taken + ", " + next + "]";
    }
}

//friend or ememy, relation list
class Relation {
    static class Node {
        int id;
        List<Integer> path;
        int idx;

        public Node(int id, List<Integer> path, int idx) {
            this.id = id;
            this.path = path;
            this.idx = idx;
        }
    }
    public static List<List<Integer>> bfs(List<int[]> relations, String s, int start, int end) {
        if (s == null || s.length() == 0) return new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        Map<Integer, List<Integer>> friends = new HashMap<>();
        Map<Integer, List<Integer>> enemies = new HashMap<>();
        for (int[] r : relations) {
            int x = r[0];
            int y = r[1];
            int z = r[2];
            if (z == 1) {
                List<Integer> l1 = friends.getOrDefault(x, new ArrayList<>());
                l1.add(y);
                friends.put(x, l1);
                List<Integer> l2 = friends.getOrDefault(y, new ArrayList<>());
                l2.add(x);
                friends.put(y, l2);
            } else {
                List<Integer> l1 = enemies.getOrDefault(x, new ArrayList<>());
                l1.add(y);
                enemies.put(x, l1);
                List<Integer> l2 = enemies.getOrDefault(y, new ArrayList<>());
                l2.add(x);
                enemies.put(y, l2);
            }
        }

        Queue<Node> q = new LinkedList<>();
        List<Integer> l =new ArrayList<>();
        l.add(start);
        q.add(new Node(start, l, 0));
        Set<Integer> vis = new HashSet<>();
        vis.add(start);

        while (!q.isEmpty()) {
            Node cur = q.poll();
            int id = cur.id;
            int idx = cur.idx;
            List<Integer> path = cur.path;
            if (id == end && idx == s.length()) {
                res.add(path);
                continue;
            }
            if (idx >= s.length()) continue;
            List<Integer> nextList;
            if (s.charAt(idx) == 'F') {
                nextList = friends.getOrDefault(id, new ArrayList<>());
            } else {
                nextList = enemies.getOrDefault(id, new ArrayList<>());
            }
            for (int next : nextList) {
                if (path.contains(next)) continue;
                List<Integer> newP = new ArrayList<>(path);
                newP.add(next);
                vis.add(next);
                q.add(new Node(next, newP, idx + 1));
            }
        }

        return res;
    }

    public static void main(String[] args) {
        //1 -> friend, 0 -> enemy
        List<int[]> list = Arrays.asList(
                new int[] {0, 1, 1},
                new int[] {0, 2, 1},
                new int[] {0, 3, 1},
                new int[] {1, 2, 0},
                new int[] {2, 3, 0},
                new int[] {1, 3, 0},
                new int[] {3, 4, 0},
                new int[] {0, 5, 0},
                new int[] {5, 1, 0},
                new int[] {5, 2, 0});
        System.out.println(dfs(list, "FEE", 0, 1));

        List<String> l = Arrays.asList("a","banana","app","appl","ap","apply","apple");
        Collections.sort(l);
    }

    private static List<List<Integer>> dfs(List<int[]> relations, String s, int start, int end) {
        if (s == null || s.length() == 0) return new ArrayList<>();
        List<List<Integer>> res = new ArrayList<>();
        Map<Integer, List<Integer>> friends = new HashMap<>();
        Map<Integer, List<Integer>> enemies = new HashMap<>();
        for (int[] r : relations) {
            int x = r[0];
            int y = r[1];
            int z = r[2];
            if (z == 1) {
                List<Integer> l1 = friends.getOrDefault(x, new ArrayList<>());
                l1.add(y);
                friends.put(x, l1);
                List<Integer> l2 = friends.getOrDefault(y, new ArrayList<>());
                l2.add(x);
                friends.put(y, l2);
            } else {
                List<Integer> l1 = enemies.getOrDefault(x, new ArrayList<>());
                l1.add(y);
                enemies.put(x, l1);
                List<Integer> l2 = enemies.getOrDefault(y, new ArrayList<>());
                l2.add(x);
                enemies.put(y, l2);
            }
        }
        List<Integer> cur = new ArrayList<>();
        cur.add(start);
        dfs(friends, enemies, s, 0, start, end, cur, res);
        return res;
    }

    private static void dfs(Map<Integer, List<Integer>> friends, Map<Integer, List<Integer>> enemies, String s,
                            int idx, int start, int end, List<Integer> cur, List<List<Integer>> res) {

        if (start == end && idx == s.length()) {
            res.add(new ArrayList<>(cur));
            return;
        }
        if (idx >= s.length()) return;

        List<Integer> next;
        if (s.charAt(idx) == 'F') {
            next = friends.getOrDefault(start, new ArrayList<>());
        } else {
            next = enemies.getOrDefault(start, new ArrayList<>());
        }

        for (int id : next) {
            if (cur.contains(id)) continue;
            cur.add(id);
            dfs(friends, enemies, s, idx + 1, id, end, cur, res);
            cur.remove(cur.size() - 1);
        }
    }

}
