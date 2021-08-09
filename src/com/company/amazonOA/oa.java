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

public class oa {
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
            sum[i] = sum[i-1] + arr.get(i);
        }

        for (int i = 1; i < n-1; i++) {
            if (sum[i] > sum[n-1] - sum[i+1]) {
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

    public static void main(String[] args) {
        // 315 547 355
//        System.out.println(findSongs(90, Arrays.asList(1, 10, 25, 35, 60, 59, 59)));
        int[][] mat = {{1, 2}, {1, 3}, {3, 2}, {4, 1}, {5, 2}, {3, 6}};
        List<Integer> l = Arrays.asList(10, 4, -8, 7);
//        System.out.println(spiltIntoTwo(l));
//        System.out.println(minTrioDegree(6, mat));
        System.out.println(decodeString(3, "mnes__ya_____mi"));
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
    }
}
