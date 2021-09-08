package com.company.linkedin;

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

    //lc716 lint859 Max Stack
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

    //lc432 lc297 lc200

    //lc366 lint650 · Find Leaves of Binary Tree

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

    //lc149
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

    //lc698
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
        return qs(nums, k, 0, nums.length-1);
    }

    private int qs(int[] nums, int k, int lo, int hi) {
        int n = partition(nums, lo, hi);
        System.out.println(Arrays.toString(nums));
        System.out.println(n + " " + nums[n]);
        if (n + 1 == k) return nums[n];
        else if (n + 1 > k) return qs(nums, k, lo, n-1);
        return qs(nums, k, n+1, hi);
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
            swap(nums,p1,p2);
        }
        swap(nums,lo,p1);

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
        for (int i = n-1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i+1; j < n; j++) {
                if (c[i] == c[j]) {
                    dp[i][j] = dp[i+1][j-1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i+1][j], dp[i][j-1]);
                }
            }
        }
        return dp[0][n-1];
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
                if (i != 0 && s.charAt(i-1) != 'e') return false;
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



    public static void main(String[] args) {
        LinkedIn phoneScreening = new LinkedIn();
        System.out.println(phoneScreening.isNumber("ASDe12E33"));
    }
}
