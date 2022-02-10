package com.company.linkedin;

import com.company.TreeNode;
import com.sun.org.apache.bcel.internal.generic.INEG;

import javax.smartcardio.ATR;
import java.math.BigInteger;
import java.util.*;

public class LinkedIn {
    //lc 367
    public boolean isPerfectSquare(int num) {
        if (num < 0) return false;
        if (num < 2) return true;
        int lo = 1;
        int hi = num / 2;

        while (lo <= hi) {
            long mid = lo + (hi - lo) / 2;
            if (num == mid * mid) {
                return true;
            } else if (mid * mid > num) {
                hi = (int) mid - 1;
            } else {
                lo = (int) mid + 1;
            }
        }
        return false;
    }

    //lc243 lint924
    public int shortestDistance(String[] words, String word1, String word2) {
        int p1 = -1;
        int p2 = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1)) {
                p1 = i;
            } else if (words[i].equals(word2)) {
                p2 = i;
            }
            if (p1 != -1 && p2 != -1) {
                min = Math.min(min, Math.abs(p1 - p2));
            }
        }
        return min;
    }

    //lc245 lint926
    public int shortestWordDistance(String[] words, String word1, String word2) {
        int p1 = -1;
        int p2 = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1)) {
                p1 = i;
            }
            if (words[i].equals(word2)) {
                if (word1.equals(word2)) {
                    p1 = p2;
                }
                p2 = i;
            }
            if (p1 != -1 && p2 != -1) {
                min = Math.min(min, Math.abs(p1 - p2));
            }
        }
        return min;
    }

    //lc339 lint551 Nested List Weight Sum I - BFS/DFS
    //lc364 lint905 Nested List Weight Sum II

    //lc1611 Minimum One Bit Operations to Make Integers Zero - hard - dp, bit operation

    //lc380 lc381 Insert Delete GetRandom O(1) - with/without Duplicates

    //lc432. All O`one Data Structure

    //lc256 lint515 · Paint House
    public int minCost(int[][] costs) {
        if (costs == null || costs.length == 0) return 0;

        int p0 = costs[0][0];
        int p1 = costs[0][1];
        int p2 = costs[0][2];

        for (int i = 1; i < costs.length; i++) {
            int t0 = Math.min(p1, p2);
            int t1 = Math.min(p0, p2);
            int t2 = Math.min(p0, p1);
            p0 = t0 + costs[i][0];
            p1 = t1 + costs[i][1];
            p2 = t2 + costs[i][2];
        }

        int res = Math.min(p1, p2);
        res = Math.min(res, p0);
        return res;
    }

    //lc432 lc297
    // lc200 return number of island with city,  water - 0, island - 1, city - 2
    public int numIslands(int[][] grid) {
        if (grid == null) return 0;
        int m = grid.length;
        int n = grid[0].length;
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    count++;
                    dfsHelper(grid, i , j, m, n);
                }
            }
        }
        return count;
    }

    private void dfsHelper(int[][] grid, int i, int j, int m, int n) {
        if (i < 0 || j < 0 || i >= m || j >= n || grid[i][j] == 0) return;
        grid[i][j] = 0;
        dfsHelper(grid, i+1 , j, m, n);
        dfsHelper(grid, i , j+1, m, n);
        dfsHelper(grid, i , j-1, m, n);
        dfsHelper(grid, i-1 , j, m, n);

    }

    //lc366 lint650 · Find Leaves of Binary Tree
    public List<List<Integer>> findLeaves(TreeNode root) {
        // write your code here
        List<List<Integer>> res = new ArrayList<>();
        Map<Integer, List<Integer>> map = new HashMap<>();
        int depth = dfs(root, map);
        for (int i = 1; i <= depth; i++) {
            res.add(map.get(i));
        }
        System.out.println(map);
        return res;
    }

    private int dfs(TreeNode root, Map<Integer, List<Integer>> map) {
        if (root == null) return 0;
        int d = Math.max(dfs(root.left, map), dfs(root.right, map)) + 1;
        List<Integer> cur = map.getOrDefault(d, new ArrayList<>());
        cur.add(root.val);
        map.put(d, cur);
        return d;
    }

    //lc605
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        for (int i = 0; i < flowerbed.length; i++) {
            if (flowerbed[i] == 1) continue;
            if ((i == 0 || flowerbed[i - 1] == 0) &&
                    (i == flowerbed.length - 1 || flowerbed[i + 1] == 0)) {
                n--;
                flowerbed[i] = 1;
            }
        }
        return n <= 0;
    }

    //lc706 implement hashmap

    //lc149 Max Points on a Line
    public int maxPoints(int[][] points) {
        if (points == null || points.length == 0) return 0;
        if (points.length <= 2) return points.length;

        int res = 0;

        for (int i = 0; i < points.length; i++) {
            int count = 0;
            Map<String, Integer> map = new HashMap<>();
            for (int j = i + 1; j < points.length; j++) {
                int a = points[i][0] - points[j][0];
                int b = points[i][1] - points[j][1];
                int k = gcd(a, b);
                String key = (a / k) + "-" + (b / k);
                map.put(key, map.getOrDefault(key, 0) + 1);
                count = Math.max(count, map.get(key));
            }
            res = Math.max(res, count + 1);
        }
        return res;
//        if (points == null || points.length == 0) return points.length;
//        if (points.length <= 2) return points.length;
//        int res = 0;
//        int n = points.length;
//        for (int i = 0; i < n; i++) {
//            for (int j = i+1; j < n; j++) {
//                int count = 2;
//                for (int k = j+1; k < n; k++) {
//                    int[] a = points[i];
//                    int[] b = points[j];
//                    int[] c = points[k];
//                    int x = (a[1] - b[1]) * (a[0] - c[0]);
//                    int y = (a[1] - c[1]) * (a[0] - b[0]);
//                    if (x == y) count++;
//                }
//                res = Math.max(res, count);
//            }
//        }
//        return res;
    }

    //lc33 [4,5,6,7,0,1,2]
    public int search(int[] nums, int target) {
        int lo = 0;
        int hi = nums.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (nums[mid] == target) return mid;
            if (nums[lo] <= nums[mid]) {
                if (nums[lo] <= target && target < nums[mid]) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            }
            if (nums[mid] <= nums[hi]) {
                if (nums[mid] < target && target <= nums[hi]) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
        }

        return -1;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    //lc152
    public int maxProduct(int[] nums) {
        int max = 1;
        int min = 1;
        int res = Integer.MIN_VALUE;

        for (int i = 0; i < nums.length; i++) {
            int mx = max;
            int mi = min;
            max = Math.max(Math.max(mi * nums[i], mx * nums[i]), nums[i]);
            min = Math.min(Math.min(mi * nums[i], mx * nums[i]), nums[i]);
            res = Math.max(res, max);
        }

        return res;
    }

    //lc698 k sub-array with equal sum
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for (int n : nums) {
            sum += n;
        }

        if (sum % k != 0) return false;

        return backtrack(k, nums, new boolean[nums.length], 0, sum / k, 0);
    }

    private boolean backtrack(int k, int[] nums, boolean[] used, int index, int target, int cur) {
        if (k == 0) return true;

        if (cur == target) {
            return backtrack(k - 1, nums, used, 0, target, 0);
        }

        for (int i = index; i < nums.length; i++) {
            if (used[i] || nums[i] + cur > target) continue;
            used[i] = true;
            if (backtrack(k, nums, used, i + 1, target, cur + nums[i])) return true;
            used[i] = false;
        }

        return false;
    }

    //lc215
    public int findKthLargest(int[] nums, int k) {
        return qs(nums, k, 0, nums.length - 1);
    }

    private int qs(int[] nums, int k, int lo, int hi) {
        int n = partition(nums, lo, hi);
        System.out.println(Arrays.toString(nums));
        System.out.println(n + " " + nums[n]);
        if (n + 1 == k) return nums[n];
        else if (n + 1 > k) return qs(nums, k, lo, n - 1);
        return qs(nums, k, n + 1, hi);
    }

    private int partition(int[] nums, int lo, int hi) {
        int mid = lo + (hi - lo) / 2;
        swap(nums, lo, mid);

        int pivot = nums[lo];
        int p1 = lo;
        int p2 = hi;

        while (p1 < p2) {
            while (p1 < p2 && nums[p2] <= pivot) p2--;
            while (p1 < p2 && nums[p1] >= pivot) p1++;
            swap(nums, p1, p2);
        }
        swap(nums, lo, p1);

        return p1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    //lc516
    public int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        char[] c = s.toCharArray();
//        dp[i][j] = dp[i+1][j-1] + 2  || dp[i][j] = max(dp[i+1][j], dp[i][j-1])
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (c[i] == c[j]) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }

    //lc65 vail number
    public boolean isNumber(String s) {
        s = s.trim().toLowerCase();
        if (s == null || s.length() == 0) return false;

        boolean dotSeen = false, eSeen = false, numBeforeE = false, numberAfterE = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                if (eSeen) numberAfterE = true;
                if (!eSeen) numBeforeE = true;
            } else if (c == '+' || c == '-') {
                if (i != 0 && s.charAt(i - 1) != 'e') return false;
            } else if (c == '.') {
                if (dotSeen || eSeen) return false;
                dotSeen = true;
            } else if (c == 'e') {
                if (eSeen) return false;
                eSeen = true;
            } else {
                return false;
            }
        }

        return eSeen ? (numBeforeE && numberAfterE) : numBeforeE;
    }

    //lc 249 lintcode 922
    public List<List<String>> groupStrings(String[] strings) {
        if (strings == null || strings.length == 0) {
            return new LinkedList<>();
        }
        List<List<String>> res = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for (String s : strings) {
            String key = getKey(s);
            List<String> list = map.getOrDefault(key, new ArrayList<>());
            list.add(s);
            map.put(key, list);
        }
        res.addAll(map.values());
        return res;
    }

    private String getKey(String s) {
        String res = "";
        int diff = s.charAt(0) - 'a';
        for (int i = 0; i < s.length(); i++) {
            char c = (char) (s.charAt(i) - diff);
            if (c < 'a') {
                c += 26;
            }
            res += c;
        }
        return res;
    }

    //lc 205
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) return false;
        Map<Character, Character> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char a = s.charAt(i);
            char b = t.charAt(i);
            if (map.containsKey(a) && map.get(a) != b) {
                return false;
            }
            if (!map.containsKey(a)) {
                if (map.containsValue(b)) {
                    return false;
                }

                map.put(a, b);
            }
        }

        return true;
    }

    //lc 156 lintcode 649
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null || root.left == null) return root;

        TreeNode node = upsideDownBinaryTree(root.left);
        root.left.left = root.right;
        root.left.right = root;
        root.left = null;
        root.right = null;
        return node;
    }

    //lc 1197 Minimum Knight Moves
    public int minKnightMoves(int x, int y) {
        if (x == 0 && y == 0) return 0;
        // board is symmetric
        x = Math.abs(x);
        y = Math.abs(y);
        int[] dx = {2,2,-2,-2,1,1,-1,-1};
        int[] dy = {1,-1,1,-1,2,-2,2,-2};
        Set<String> vis = new HashSet<>();
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] {0, 0});
        vis.add(0 + "-" + 0);

        int steps = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int k = 0; k < size; k++) {
                int[] cur = q.poll();
                if (cur[0] == x && cur[1] == y) return steps;
                for (int i = 0; i < dx.length; i++) {
                    int r = cur[0] + dx[i];
                    int c = cur[1] + dy[i];
                    if (r < 0 || c < 0 || r >= 310 || c >= 310) continue;
                    if (!vis.contains(r + "-" + c)) {
                        q.add(new int[] {r, c});
                        vis.add(r + "-" + c);
                    }
                }
            }
            steps++;
        }

        return -1;
    }

    //lc 761
    public String makeLargestSpecial(String s) {
        //1.找出特殊二进制序列中的特殊子串
        if (s.length() <= 2) return s;//结束条件
        List<String> list = new ArrayList<>();//用来存储连续的字串，并且符合要求的子串必须是1开头0结尾
        StringBuilder sb = new StringBuilder();
        int start = 0;//用来声明下一个特殊字串开始的位置
        int count = 0;//计数器，通过记录子1和0的数量判断是否为一个特殊子串
        for (int i = 0; i < s.length(); i++) {
            count += s.charAt(i) == '1' ? 1 : -1;//每逢1则计数器+1，逢0则减一
            if (count == 0) {//当count等于0时，说明得到一个特殊的二进制序列
                String str = s.substring(start + 1, i);//获得去除首尾后的子序列，形成递归
                String result = makeLargestSpecial(str);//对子序列求字典序排列
                list.add("1" + result + "0");//对子序列添加之前去除的首尾1和0，并存储到list集合中
                start = i + 1;//start为下一个特殊子串开始的位置
            }
        }
        //2.将特殊子串按照字典序排列
        //toArray()方法会返回List中所有元素构成的数组
        //toArray[T[] a]方法会返回List中所有元素构成的指定类型的数组
        String[] arr = list.toArray(new String[list.size()]);
        // System.out.println(list);
        quickSort(arr, 0, arr.length - 1);//对数组中的特殊子串进行快速排序
        //由于快排后为从小到大，所以再进行逆序
        for (int i = arr.length - 1; i >= 0; i--) {
            sb.append(arr[i]);
        }
        // System.out.println("ans " + sb.toString());
        return sb.toString();//返回排序后的字符串
    }

    //快速排序
    public void quickSort(String[] arr, int low, int high) {
        if (low >= high) return;
        int p1 = low;
        int p2 = high;
        String base = arr[low];
        while (p1 < p2) {
            while (p1 < p2 && arr[p2].compareTo(base) >= 0) {
                p2--;
            }
            while (p1 < p2 && arr[p1].compareTo(base) <= 0) {
                p1++;
            }

            swap(arr, p1, p2);
        }
        swap(arr, low, p1);
        quickSort(arr, low, p1 - 1);
        quickSort(arr, p1 + 1, high);
    }

    private void swap(String[] arr, int i, int j) {
        String t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    //lc 53
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int res = nums[0];
        for (int i = 1; i < nums.length; i++) {
            nums[i] = Math.max(nums[i], nums[i] + nums[i-1]); //maximum subarray ending with A[i]
            res = Math.max(res, nums[i]);
        }

        return res;
    }

    public static void main(String[] args) {
        LinkedIn phoneScreening = new LinkedIn();
        int[][] mat = {
                {1,2,1,0,0,0},
                {1,1,0,0,0,0},
                {0,0,0,0,1,0},
                {1,2,1,1,2,1}
        };
        System.out.println(phoneScreening.numIslands(mat));

    }
}

//lc 380
class RandomizedSet {
    List<Integer> list;
    Random rd;
    Map<Integer, Integer> map;

    /**
     * Initialize your data structure here.
     */
    public RandomizedSet() {
        this.list = new ArrayList<>();
        this.rd = new Random();
        this.map = new HashMap<>(); //<val, index>
    }

    /**
     * Inserts a value to the set. Returns true if the set did not already contain the specified element.
     */
    public boolean insert(int val) {
        if (map.containsKey(val)) return false;
        int idx = list.size();
        map.put(val, idx);
        list.add(val);
        return true;
    }

    /**
     * Removes a value from the set. Returns true if the set contained the specified element.
     */
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

    /**
     * Get a random element from the set.
     */
    public int getRandom() {
        return list.get(rd.nextInt(list.size()));
    }
}

//716 max stack lintcode 859
class MaxStack {
    Stack<Integer> st;
    Stack<Integer> maxSt;
    public MaxStack() {
        st = new Stack<>();
        maxSt = new Stack<>();
    }

    public void push(int x) {
        int max = maxSt.isEmpty() ? x : Math.max(x, peekMax());
        maxSt.push(max);
        st.push(x);
    }

    public int pop() {
        maxSt.pop();
        return st.pop();
    }

    public int top() {
        return st.peek();
    }

    public int peekMax() {
        // write your code here
        return maxSt.peek();
    }

    public int popMax() {
        // write your code here
        int max = peekMax();
        Stack<Integer> temp = new Stack<>();
        while (st.peek() != max) {
            temp.push(pop());
        }
        pop();
        while (!temp.isEmpty()) {
            push(temp.pop());
        }
        return max;
    }
}

//lc 642 autocomplete
class AutocompleteSystem {
    class TrieNode {
        public boolean isLeaf;
        public List<String> cands;
        HashMap<Character, TrieNode> children;
        public TrieNode() {
            isLeaf = false;
            children = new HashMap<Character, TrieNode>();
            cands = new LinkedList<String>();
        }
    }
    class Trie {
        private TrieNode root;
        public Trie() {
            root = new TrieNode();
        }
        // Inserts a word into the trie.
        public void insert(String word) {
            TrieNode node = root;
            for (int i = 0; i < word.length(); i ++) {
                HashMap<Character, TrieNode> children = node.children;
                char c = word.charAt(i);
                if (!children.containsKey(c)) {
                    children.put(c, new TrieNode());
                }
                children.get(c).cands.add(word);
                if (i == word.length() - 1) {
                    children.get(c).isLeaf = true;
                }
                node = node.children.get(c);
            }
        }
        private TrieNode searchNode(String pre) {
            HashMap<Character, TrieNode> children = root.children;
            TrieNode node = root;
            for (int i = 0; i < pre.length(); i ++) {
                if (!children.containsKey(pre.charAt(i))) {
                    return null;
                }
                node = children.get(pre.charAt(i));
                children = node.children;
            }
            return node;
        }
    }
    HashMap<String, Integer> count = new HashMap<String, Integer>();
    Trie trie = new Trie();
    String curr = "";
    public AutocompleteSystem(String[] sentences, int[] times) {
        for (int i = 0; i < sentences.length; i ++) {
            count.put(sentences[i], times[i]);
            trie.insert(sentences[i]);
        }
    }

    public List<String> input(char c) {
        List<String> res = new LinkedList<String>();
        if (c == '#') {
            if (!count.containsKey(curr)) {
                trie.insert(curr);
                count.put(curr, 1);
            }
            else {
                count.put(curr, count.get(curr) + 1);
            }
            curr = "";
        }
        else {
            curr += c;
            res = getSuggestions();
        }

        return res;
    }
    private List<String> getSuggestions() {
        List<String> res = new LinkedList<String>();
        TrieNode node = trie.searchNode(curr);
        if (node == null) {
            return res;
        }
        List<String> cands = node.cands;
        Collections.sort(cands, new Comparator<String>(){
            public int compare(String s1, String s2) {
                if (count.get(s1) != count.get(s2)) {
                    return count.get(s2) - count.get(s1);
                }
                return s1.compareTo(s2);
            }
        });
        int added = 0;
        for (String s:cands) {
            res.add(s);
            added ++;
            if (added > 2) {
                break;
            }
        }
        return res;
    }

    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        int[][] cost = new int[n][n];
        for (int[] c : cost) Arrays.fill(c, -1);
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int[] f : flights) {
            cost[f[0]][f[1]] = f[2];
            List<Integer> list = map.getOrDefault(f[0], new ArrayList<>());
            list.add(f[1]);
            map.put(f[0], list);
        }

        // Queue<int[]> pq = new LinkedList<>();
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.add(new int[] {src, 0, 0});
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int x = cur[0];
            int c = cur[1];
            int stops = cur[2];
            if (x == dst) {
                return c;
            }
            if (stops > k) continue;
            for (Integer y : map.getOrDefault(x, new ArrayList<>())) {
                pq.add(new int[] {y, c+cost[x][y], stops+1});
            }
        }
        return -1;
    }


    public static void main(String[] args) {
        int[][] mat = {{0,1,100}, {1,2,100},{0,2,500}};
//        System.out.println(findCheapestPrice(3,mat,0,2,1));
    }
}