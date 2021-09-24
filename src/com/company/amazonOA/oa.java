package com.company.amazonOA;

import java.util.*;

class PairString {
    String first;
    String second;

    PairString(String first, String second) {
        this.first = first;
        this.second = second;
    }
}

public class OA {
    // music pair
    public static List<Integer> findSongs(int rideDuration, List<Integer> songDurations) {
        List<Integer> res = new LinkedList<>();
        int k1 = 0, k2 = 0;
        int v1 = -1, v2 = -1;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < songDurations.size(); i++) {
            int cur = songDurations.get(i);
            if (map.containsKey(rideDuration - 30 - cur)) {
                int value = map.get(rideDuration - 30 - cur);
                if (Math.max(k1, k2) < Math.max(cur, rideDuration - 30 - cur)) {
                    k1 = cur;
                    k2 = rideDuration - 30 - cur;
                    v1 = value;
                    v2 = i;
                }
            }
            map.put(cur, i);
        }
        res.add(v1);
        res.add(v2);
        return res;
    }

    // Amazon air prime
    public static List<List<Integer>> primeAirTime(List<List<Integer>> l1, List<List<Integer>> l2, int k) {
        List<List<Integer>> res = new ArrayList<>();
        if (k == 0 || l1 == null || l1.size() == 0 || l2 == null || l2.size() == 0) {
            res.add(new ArrayList<>());
            return res;
        }

        Collections.sort(l1, Comparator.comparingInt(a -> a.get(1)));
        Collections.sort(l2, (a, b) -> a.get(1) - b.get(1));

        int dist = 0;
        for (int i = 0; i < l1.size(); i++) {
            for (int j = 0; j < l2.size(); j++) {
                int sum = l1.get(i).get(1) + l2.get(j).get(1);
                if (sum > k) {
                    break;
                } else if (sum == dist) {
                    List<Integer> cur = new ArrayList<>();
                    cur.add(l1.get(i).get(0));
                    cur.add(l2.get(j).get(0));
                    res.add(cur);
                    dist = sum;
                } else if (sum > dist) {
                    res.clear();
                    List<Integer> cur = new ArrayList<>();
                    cur.add(l1.get(i).get(0));
                    cur.add(l2.get(j).get(0));
                    res.add(cur);
                    dist = sum;
                } else if (sum == dist) {
                    List<Integer> cur = new ArrayList<>();
                    cur.add(l1.get(i).get(0));
                    cur.add(l2.get(j).get(0));
                    res.add(cur);
                }
            }
        }
        return res;
    }

    // longest path in matrix lc 329

    //shopping option - 4 sum
    public static long getNumberOfOptions(List<Integer> pricesOfJeans, List<Integer> priceOfShoes,
                                          List<Integer> pricesOfSkirts, List<Integer> priceOfTops,
                                          int dollars) {
        long res = 0;
        List<Integer> sums = new ArrayList<>();
        for (int i = 0; i < pricesOfJeans.size(); i++) {
            for (int j = 0; j < priceOfShoes.size(); j++) {
                sums.add(pricesOfJeans.get(i) + priceOfShoes.get(j));
            }
        }

        Collections.sort(sums);

        for (int i = 0; i < pricesOfSkirts.size(); i++) {
            for (int j = 0; j < priceOfTops.size(); j++) {
                int target = dollars - pricesOfSkirts.get(i) - priceOfTops.get(j);
                int index = binarySearch(sums, target);

                if (index > -1) {
                    res += index + 1;
                }
            }
        }

        return res;
/*      //tle
        long res = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < pricesOfJeans.size(); i++) {
            for (int j = 0; j < priceOfShoes.size(); j++) {
                int sum = pricesOfJeans.get(i) + priceOfShoes.get(j);
                map.put(sum, map.getOrDefault(sum, 0) + 1);
            }
        }

        for (int i = 0; i < pricesOfSkirts.size(); i++) {
            for (int j = 0; j < priceOfTops.size(); j++) {
                int sum = pricesOfSkirts.get(i) + priceOfTops.get(j);
                int target = dollars - sum;
                for (int k = 0; k <= target; k++) {
                    res += map.getOrDefault(k, 0);
                }
            }
        }
        return res;*/
    }

    private static int binarySearch(List<Integer> sums, int target) {
        int lo = 0;
        int hi = sums.size() - 1;
        int idx = -1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (sums.get(mid) > target) {
                hi = mid - 1;
            } else if (target >= sums.get(mid)) {
                lo = mid + 1;
                idx = mid;
            }
        }
        return idx;
    }

    /*
    Demolition Robot
    Given a matrix with values 0 (trenches) , 1 (flat) , and 9 (obstacle) you have to find minimum distance to reach 9 (obstacle). If not possible then return -1.
    The demolition robot must start at the top left corner of the matrix, which is always flat, and can move on block up, down, right, left.
    The demolition robot cannot enter 0 trenches and cannot leave the matrix.
    Sample Input :
    [1, 0, 0],
    [1, 0, 0],
    [1, 9, 1]]
    Sample Output :
    3
    Approach: BFS traversal
*/
    public static int minimumDistance(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return -1;

        int m = matrix.length;
        int n = m == 0 ? 0 : matrix[0].length;
        int[] dir = {0, 1, 0, -1, 0};
        boolean[] vis = new boolean[m * n];
        Queue<Integer> q = new LinkedList<>();
        q.add(0);
        vis[0] = true;
        int distance = 0;
        // index = r * n + c
        while (!q.isEmpty()) {
            int size = q.size();
            distance++;
            for (int i = 0; i < size; i++) {
                int idx = q.poll();
                for (int j = 0; j < 4; j++) {
                    int r = idx / n + dir[j];
                    int c = idx % n + dir[j + 1];
                    if (r < 0 || c < 0 || r >= m || c >= n || matrix[r][c] == 0 || vis[r * n + c]) continue;
                    else if (matrix[r][c] == 9) return distance;
                    else {
                        q.offer(r * n + c);
                        vis[r * n + c] = true;
                    }
                }
            }
        }
        return -1;
    }

    // five star seller https://leetcode.com/playground/2d2z2dJ6 nlogk - k = # of products
    public static int fiveStarReviews(int[][] productRatings, int ratingsThreshold) {
        int count = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> myCompare(a, b, productRatings));
        double rate = 0.0;
        int n = productRatings.length;
        for (int i = 0; i < n; i++) {
            double cur = 1.0 * productRatings[i][0] / productRatings[i][1];
            if (cur != 1) {
                pq.add(i);
            }
            rate += cur;
        }

        rate /= n;

        while (rate < ratingsThreshold / 100.0) {
            count++;
            int idx = pq.poll();
            rate += getImproveRate(idx, productRatings) / n;
            productRatings[idx][0] += 1;
            productRatings[idx][1] += 1;
            pq.offer(idx);
        }

        return count;
    }

    private static int myCompare(int a, int b, int[][] productRatings) {
        double o1 = getImproveRate(a, productRatings);
        double o2 = getImproveRate(b, productRatings);

        return Double.compare(o2, o1);

    }

    private static double getImproveRate(int i, int[][] productRatings) {
        double pre = 1.0 * productRatings[i][0] / productRatings[i][1];
        double cur = (1.0 * productRatings[i][0] + 1) / (productRatings[i][1] + 1);
        return cur - pre;
    }

    //Upgrading Junction Boxes
    public String[] reorderLogFiles(String[] logs) {
        Arrays.sort(logs, (s1, s2) -> {
            String l1 = s1.substring(s1.indexOf(" ") + 1);
            String l2 = s2.substring(s2.indexOf(" ") + 1);

            if (Character.isDigit(l1.charAt(0))) {
                if (Character.isDigit(l2.charAt(0))) {
                    return 0;
                }
                return 1;
            } else {
                if (Character.isDigit(l2.charAt(0))) {
                    return -1;
                }
                int content = l1.compareTo(l2);
                if (content == 0) {
                    return s1.substring(0, s1.indexOf(" ")).compareTo(s2.substring(0, s2.indexOf(" ")));
                }
                return content;
            }


        });
        return logs;
    }

    // fill the truck
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        if (boxTypes == null || truckSize <= 0) {
            return 0;
        }
        int max = 0;
        Arrays.sort(boxTypes, (a, b) -> b[1] - a[1]);

        for (int i = 0; i < boxTypes.length; i++) {
            if (boxTypes[i][0] >= truckSize) {
                max += truckSize * boxTypes[i][1];
                truckSize = 0;
            } else {
                max += boxTypes[i][0] * boxTypes[i][1];
                truckSize -= boxTypes[i][0];
            }

            if (truckSize <= 0) {
                break;
            }
        }

        return max;
    }

    //lc 1167 lintcode 1872
    public static int minimumCost(List<Integer> sticks) {
        if (sticks == null || sticks.size() == 0) return 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(sticks);
        int cost = 0;
        while (pq.size() > 1) {
            int x = pq.poll();
            int y = pq.poll();
            cost += x + y;
            pq.offer(x + y);
        }
        return cost;
    }

    //lintcode 1338
    public int ParkingDilemma(int[] cars, int k) {
        // write your code here
        Arrays.sort(cars);
        int len = Integer.MAX_VALUE;
        for (int i = k - 1; i < cars.length; i++) {
            len = Math.min(len, cars[i] - cars[i - k + 1] + 1);
        }
        return len;
    }

    // analogous Arrays
    public static int countAnalogousArrays(List<Integer> consecutiveDifference, int lowerBound, int upperBound) {
        if (upperBound < lowerBound || consecutiveDifference == null || consecutiveDifference.size() == 0) {
            return 0;
        }
        int cur = 0;
        int max = 0;
        int min = 0;

        for (int i = 0; i < consecutiveDifference.size(); i++) {
            cur = cur - consecutiveDifference.get(i);
            max = Math.max(cur, max);
            min = Math.min(cur, min);
        }
        System.out.println(max + " " + min);
        return (upperBound - max) - (lowerBound - min) + 1;
    }

    //lc221
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int m = matrix.length;
        int n = matrix[0].length;

        int[][] dp = new int[m + 1][n + 1];
        int len = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (matrix[i - 1][j - 1] == '1') {
                    dp[i][j] = 1 + Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]);
                    len = Math.max(len, dp[i][j]);
                }
            }
        }

        return len * len;
    }

    //lc435
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length < 1) {
            return 0;
        }
        int count = 0;
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        int preEnd = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            if (preEnd > intervals[i][0]) {
                count++;
                preEnd = Math.min(preEnd, intervals[i][1]);
            } else {
                preEnd = intervals[i][1];
            }
        }
        return count;
    }

    //lc1465 cake
    public int maxArea(int h, int w, int[] horizontalCuts, int[] verticalCuts) {
        if (horizontalCuts == null && verticalCuts == null) return h * w;
        Arrays.sort(horizontalCuts);
        Arrays.sort(verticalCuts);

        int maxW = Math.max(verticalCuts[0], w - verticalCuts[verticalCuts.length - 1]);
        int maxH = Math.max(horizontalCuts[0], h - horizontalCuts[horizontalCuts.length - 1]);

        for (int i = 1; i < verticalCuts.length; i++) {
            maxW = Math.max(maxW, verticalCuts[i] - verticalCuts[i - 1]);
        }

        for (int i = 1; i < horizontalCuts.length; i++) {
            maxH = Math.max(maxH, horizontalCuts[i] - horizontalCuts[i - 1]);
        }
        return (int) (1l * maxH * maxW % 1000000007);
    }

    // book
    public static List<String> largestItemAssociation(List<PairString> itemAssociation) {
        LinkedHashMap<String, LinkedHashSet<String>> map = new LinkedHashMap<>();
        for (PairString pairs : itemAssociation) {
            if (pairs.first.equals(pairs.second)) continue;
            map.computeIfAbsent(pairs.first, val -> new LinkedHashSet<>()).add(pairs.second);
            map.computeIfAbsent(pairs.second, val -> new LinkedHashSet<>()).add(pairs.first);
        }
        List<String> result = new ArrayList<>();
        for (String name : map.keySet()) {
            Set<String> visited = new HashSet<>();
            List<String> cur = dfs(name, map, visited);
            if (cur.size() > result.size()) {
                result = cur;
            }
        }
        return result;
    }

    private static List<String> dfs(String name, Map<String, LinkedHashSet<String>> map, Set<String> visited) {
        if (!visited.add(name)) return new ArrayList<>();
        List<String> cur = new ArrayList<>();
        for (String dep : map.get(name)) {
            List<String> next = dfs(dep, map, visited);
            if (next.size() > cur.size()) {
                cur = next;
            }
        }
        visited.remove(name);
        cur.add(0, name);
        return cur;
    }

    //"|**|*|*", new int[] {1, 1}, new int[] {5, 6} -> [2,3]
    public static int[] itemInContainer(String s, int[] start, int[] end) {
        int n = s.length();

        int[] prefixSum = new int[n];
        int[] res = new int[start.length];

        prefixSum[0] = 0;
        int count = 0;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == '|') {
                prefixSum[i] = count;
            } else {
                count++;
                prefixSum[i] = prefixSum[i - 1];
            }
        }

        for (int i = 0; i < start.length; i++) {
            res[i] = prefixSum[end[i] - 1] - prefixSum[start[i] - 1];
        }

        return res;
    }

    //1761
    public static int minTrioDegree(int n, int[][] edges) {
        int min = Integer.MAX_VALUE;
        //node, degree
        Map<Integer, Integer> map = new HashMap<>();
        boolean[][] connected = new boolean[n + 1][n + 1];
        for (int[] e : edges) {
            map.put(e[0], map.getOrDefault(e[0], 0) + 1);
            map.put(e[1], map.getOrDefault(e[1], 0) + 1);
            connected[e[0]][e[1]] = true;
            connected[e[1]][e[0]] = true;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = i + 1; j <= n; j++) {
                for (int k = j + 1; k <= n; k++) {
                    if (connected[i][j] && connected[j][k] && connected[i][k]) {
                        int cur = map.get(i) + map.get(j) + map.get(k) - 6;
                        min = Math.min(cur, min);
                    }
                }
            }
        }
        return min == Integer.MAX_VALUE ? -1 : min;
    }

    // spilt arr into leftSum > rightSum
    public static int spiltIntoTwo(List<Integer> arr) {
        int n = arr.size();
        int count = 0;
        int[] sum = new int[n];

        sum[0] = arr.get(0);
        for (int i = 1; i < n; i++) {
            sum[i] = sum[i - 1] + arr.get(i);
        }

        for (int i = 1; i < n - 1; i++) {
            if (sum[i] > sum[n - 1] - sum[i + 1]) {
                count++;
            }
        }
        return count;
    }

    public static String decodeString(int n, String encodedString) {
        int len = encodedString.length();
        int col = len / n;
        char[][] chars = new char[n][col];
        StringBuilder sb = new StringBuilder();
        int idx = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < col; j++) {
                chars[i][j] = encodedString.charAt(idx++);
            }
        }

        int r = 0;
        int c = 0;
        for (; c < col; c++) {
            int x = c;
            while (r < n && x < col) {
                if (chars[r][x] != '_')
                    sb.append(chars[r][x]);
                else sb.append(" ");
                r++;
                x++;
            }
            r = 0;
        }

        return sb.toString();
    }

    //lc 45 jump game
    public static int jump(int[] nums) {
        //[2,3,1,1,4]
/** //O(n^2)
 if (nums.length <= 1) return 0;
 int n = nums.length;
 int steps = 0;
 boolean[] vis = new boolean[n];
 Queue<Integer> q = new LinkedList<>();
 q.add(0);
 vis[0] = true;

 while (!q.isEmpty()) {
 steps++;
 int size = q.size();
 for (int i = 0; i < size; i++) {
 int idx = q.poll();
 for (int j = 1; j <= nums[idx]; j++) {
 if (idx + j >= n - 1) return steps;
 if (vis[idx + j]) continue;
 q.add(idx + j);
 vis[idx + j] = true;
 }
 }
 }

 return -1;
 **/
        // O(n)
        int n = nums.length;
        int curMax = 0;
        int canReach = 0;
        int steps = 0;

        for (int i = 0; i < n - 1; i++) {
            canReach = Math.max(canReach, i + nums[i]);
            if (i == curMax) {
                steps++;
                curMax = canReach;
            }
        }
        return steps;
    }

    //lc 200 follow up 305
    public int numIslands(char[][] grid, List<int[]> island) {
        int m = grid.length;
        int n = m == 0 ? 0 : grid[0].length;
        int count = 0;
        char target = 'a';
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfs(grid, i, j, m, n, target++);
                }
            }
        }

        for (char[] c : grid) System.out.println(Arrays.toString(c));
        System.out.println(count);

        for (int[] i : island) {
            int r = i[0];
            int c = i[1];
            int check = helper(grid, r, c);
            if (check == 0) {
                count++;
            } else if (check > 1) {
                count = count - check + 1;
            }
        }
        System.out.println("---------");
        for (char[] c : grid) System.out.println(Arrays.toString(c));

        return count;
    }

    private int helper(char[][] grid, int r, int c) {
        if (grid[r][c] != '0') return -1;
        grid[r][c] = 'x';
        Set<Character> set = new HashSet<>();
        int[] dir = {0, 1, 0, -1, 0};
        for (int i = 0; i < 4; i++) {
            int x = r + dir[i];
            int y = c + dir[i + 1];
            if (x < 0 || y < 0 || x >= grid.length || y > grid[0].length) continue;
            if (grid[x][y] != '0') {
                set.add(grid[x][y]);
            }
        }
        return set.size();
    }

    private void dfs(char[][] grid, int r, int c, int m, int n, char target) {
        if (r < 0 || c < 0 || r >= m || c >= n) return;
        if (grid[r][c] != '1') return;

        grid[r][c] = target;
        dfs(grid, r, c + 1, m, n, target);
        dfs(grid, r + 1, c, m, n, target);
        dfs(grid, r - 1, c, m, n, target);
        dfs(grid, r, c - 1, m, n, target);
    }

    //lc1306 jump game III
    public boolean canReach(int[] arr, int start) {
        int n = arr.length;
        boolean[] vis = new boolean[n];
        Queue<Integer> q = new LinkedList<>();
        q.add(start);
        vis[start] = true;

        while (!q.isEmpty()) {
            int size = q.size();
            for (int k = 0; k < size; k++) {
                int idx = q.poll();
                if (arr[idx] == 0) return true;
                if (idx + arr[idx] < n && !vis[idx + arr[idx]]) {
                    q.add(idx + arr[idx]);
                    vis[idx + arr[idx]] = true;
                }
                if (idx - arr[idx] >= 0 && !vis[idx - arr[idx]]) {
                    q.add(idx - arr[idx]);
                    vis[idx - arr[idx]] = true;
                }
            }
        }

        return false;
    }

    //lc 739 daily temperatures
    public static int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] res = new int[n];
        Deque<Integer> st = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && temperatures[i] > temperatures[st.peekLast()]) {
                int idx = st.pollLast();
                res[idx] = i - idx;
            }
            st.addLast(i);
        }
        System.out.println(Arrays.toString(res));
        return res;
    }

    //lc 871 minimum number of refueling stops
    public static int minRefuelStops(int target, int startFuel, int[][] stations) {
        if (startFuel >= target) return 0;

        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        int maxDis = startFuel;
        int stops = 0;
        int i = 0;

        while (maxDis < target) {
            while (i < stations.length && maxDis >= stations[i][0]) {
                pq.offer(stations[i++][1]);
            }
            if (pq.isEmpty()) return -1; //can not reach to next station, station[i][0]
            stops++;
            maxDis += pq.poll();
        }

        return stops;
    }

    //lc 1345 jump game IV
    public int minJumps(int[] arr) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            List<Integer> list = map.getOrDefault(arr[i], new ArrayList<>());
            list.add(i);
            map.put(arr[i], list);
        }

        Queue<Integer> q = new LinkedList<>();
        boolean[] vis = new boolean[n];
        q.add(0);
        vis[0] = true;
        int steps = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int idx = q.poll();
                if (idx == n - 1) return steps;
                for (int child : map.get(arr[idx])) {
                    if (vis[child]) continue;
                    q.add(child);
                    vis[child] = true;
                }
                map.get(arr[idx]).clear();

                if (idx - 1 > 0 && !vis[idx-1]) {
                    q.add(idx-1);
                    vis[idx-1] = true;
                }

                if (idx + 1 < n && !vis[idx+1]) {
                    q.add(idx+1);
                    vis[idx+1] = true;
                }
            }
            steps++;
        }

        return -1;
    }

    //lc 695 max island area
    public int maxAreaOfIsland(int[][] grid) {
        int m = grid.length;
        int n = m == 0 ? 0 : grid[0].length;
        int area = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    int cur = dfs(grid, i, j, m, n);
                    area = Math.max(area, cur);
                }
            }
        }

        return area;
    }

    public int dfs(int[][] grid, int i, int j, int m, int n) {
        if (i < 0 || j < 0 || i >= m || j >= n) return 0;
        if (grid[i][j] == 0) return 0;

        grid[i][j] = 0;
        return 1 + dfs(grid, i+1, j, m, n) + dfs(grid, i, j+1, m, n) +
                dfs(grid, i-1, j, m, n) + dfs(grid, i, j-1, m, n);

    }
    //lc 273
    private final String[] LESS_THAN_20 = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    private final String[] TENS = {"", "Ten", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};
    private final String[] THOUSANDS = {"", "Thousand", "Million", "Billion"};
    public String numberToWords(int num) {
        if (num == 0) return "Zero";

        int i = 0;
        String res = "";

        while (num > 0) {
            if (num % 1000 != 0) {
                res = helper(num%1000) + THOUSANDS[i] + " " + res;
            }
            i++;
            num /= 1000;
        }

        return res.trim();
    }

    private String helper(int num) {
        if (num == 0) {
          return "";
        } else if (num < 20) {
            return LESS_THAN_20[num] + " ";
        } else if (num < 100) {
            return TENS[num/10] + " " + helper(num%10);
        } else {
            return LESS_THAN_20[num/100] + " Hundred " + helper(num%100);
        }
    }

    public List<Integer> getList(List<Integer> nums) {
        if (nums == null || nums.size() == 0) return new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        int lo = 0;
        int len = 0;
        int start = 0;

        for (int hi = 0; hi < nums.size(); hi++) {
            int n = nums.get(hi);
            if (map.getOrDefault(n, -1) >= lo) {
                lo = map.get(n) + 1;
            }
            if (len < hi - lo + 1) {
                len = hi - lo + 1;
                start = lo;
            }

            map.put(n, hi);
        }

        List<Integer> res = new ArrayList<>();
        for (int i = start; i < start+len; i++) {
            res.add(nums.get(i));
        }
        return res;
    }

    public static void main(String[] args) {
        // 315 547 355
//        System.out.println(findSongs(90, Arrays.asList(1, 10, 25, 35, 60, 59, 59)));
        int[][] mat = {{1, 2}, {1, 3}, {3, 2}, {4, 1}, {5, 2}, {3, 6}};
        List<Integer> l = Arrays.asList(1,1,2,3,4,5,6,7,9,7,8,9,10,11,12,13,1,2,3,4,5,6,7,8);
        System.out.println(new OA().getList(l));
//        System.out.println(spiltIntoTwo(l));
//        System.out.println(minTrioDegree(6, mat));
//        System.out.println(decodeString(3, "mnes__ya_____mi"));
//        System.out.println(Arrays.toString(itemInContainer("|**|*|*", new int[] {1, 1}, new int[] {5, 6})));
//        List<List<Integer>> l1 = Arrays.asList(Arrays.asList(1, 2000), Arrays.asList(2, 4000), Arrays.asList(3, 6000));
//        List<List<Integer>> l2 = Arrays.asList(Arrays.asList(1, 2000));
//        System.out.println(primeAirTime(l1, l2, 6000));
//        System.out.println(primeAirTime());
//        System.out.println(minimumDistance(mat));

//        List<Integer> l1 = Arrays.asList(2,3,4,5,6,7,9);
//        List<Integer> l2 = Arrays.asList(4,7,9,12,13,14);
//        List<Integer> l3 = Arrays.asList(2,3);
//        List<Integer> l4 = Arrays.asList(1,2);
//
//        System.out.println(getNumberOfOptions(l1, l2, l3, l4, 25));
        char[][] chars = {{'A', 'B', 'C', 'E', '0'},
                {'S', 'F', 'C', 'S', '0'},
                {'A', 'D', 'E', 'E', '1'},
                {'1', '0', '0', '0', 'X'}};
//        List<int[]> list = Arrays.asList(new int[] {2,2});
        System.out.println(new OA().numberToWords(123));

    }
}
//lc 353
class SnakeGame {
    Set<String> snake;
    Queue<int[]> q;
    int[][] food;
    int eaten;
    int m;
    int n;
    int x;
    int y;
    public SnakeGame(int width, int height, int[][] food) {
        int x = 0;
        int y = 0;
        this.snake = new HashSet<>();
        snake.add(x + "-" + y);
        this.q = new LinkedList<>();
        q.offer(new int[] {x, y});
        this.eaten = 0;
        this.m = height;
        this.n = width;
        this.food = food;
    }

    public int move(String direction) {
        if (direction.equals("U")) {
            x--;
        } else if (direction.equals("D")) {
            x++;
        } else if (direction.equals("L")) {
            y--;
        } else if (direction.equals("R")) {
            y++;
        }

        if (x < 0 || y < 0 || x >= m || y >= n) return -1;

        return moveTo(x,y);
    }

    private int moveTo(int x, int y) {
        if (eaten == food.length) {
            snake.remove(q.peek()[0] + "-" + q.peek()[1]);
            q.poll();
        } else if (x == food[eaten][0] && y == food[eaten][1]) {
            eaten++;
        } else {
            snake.remove(q.peek()[0] + "-" + q.peek()[1]);
            q.poll();
        }
        if (snake.contains(x + "-" + y)) return -1;

        q.offer(new int[] {x, y});
        snake.add(x + "-" + y);

        return eaten;
    }

    //lc 207 course schedule
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int[] in = new int[numCourses];
        List<Integer>[] out = new List[numCourses];

        for (int i = 0; i < numCourses; i++) {
            out[i] = new ArrayList<>();
        }

        for(int[] p : prerequisites) {
            in[p[0]]++; //number of requirement
            out[p[1]].add(p[0]); // next-class-list
        }

        Stack<Integer> st = new Stack<>();
        for (int i = 0; i < numCourses; i++) {
            if (in[i] == 0) {
                st.push(i);
            }
        }
        List<Integer> res = new LinkedList<>();
        while (!st.isEmpty()) {
            int idx = st.pop();
            res.add(idx);
            for (int n : out[idx]) {
                in[n]--;
                if (in[n] == 0) {
                    st.push(n);
                }
            }
        }
        return res.size() == numCourses;
    }

    //lc 210 course schedule II
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<Integer> res = new ArrayList<>();
        int[] degree = new int[numCourses];
        List<Integer>[] sub = new List[numCourses];
        for (int i = 0; i < numCourses; i++) {
            sub[i] = new ArrayList<>();
        }

        for (int[] p : prerequisites) {
            degree[p[0]]++;
            sub[p[1]].add(p[0]);
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (degree[i] == 0) {
                q.offer(i);
            }
        }

        while (!q.isEmpty()) {
            int idx = q.poll();
            res.add(idx);
            for (int next : sub[idx]) {
                degree[next]--;
                if (degree[next] == 0) {
                    q.offer(next);
                }
            }
        }
        if (res.size() < numCourses) return null;

        int[] arr = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            arr[i] = res.get(i);
        }
        return arr;

    }

    public static void main(String[] args) {
        int[][] food = {{1,2},{0,1}};
        SnakeGame snake = new SnakeGame(3, 2, food);
        //0,0,1,1,2,-1
        System.out.println(snake.move("R"));
        System.out.println(snake.move("D"));
        System.out.println(snake.move("R"));
        System.out.println(snake.move("U"));
        System.out.println(snake.move("L"));
        System.out.println(snake.move("U"));

    }
}