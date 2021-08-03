package com.company;

import javafx.util.Pair;

import java.util.*;

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }
}

class Solution {
    //lc 221
    public int maximalSquare(char[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] dp = new int[row][col];
        int len = 0;

        for (int i = 0; i < row; i++) {
            dp[i][0] = matrix[i][0] == '1' ? 1 : 0;
        }
        for (int i = 0; i < col; i++) {
            dp[0][i] = matrix[0][i] == '1' ? 1 : 0;
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (matrix[i][j] == '1') {
                    dp[i][j] = Math.min(Math.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                    len = Math.max(len, dp[i][j]);
                }
            }
        }
        return len * len;
    }

    public List<Integer> mostVisited(int n, int[] rounds) {

        //1312
        List<Integer> ans = new ArrayList<>();
        if (rounds[0] >= rounds[rounds.length - 1]) {
            for (int i = rounds[0]; i <= rounds[rounds.length - 1]; i++) {
                ans.add(i);
            }
        } else {
            for (int i = 1; i <= rounds[rounds.length - 1]; i++) {
                ans.add(i);
            }

            for (int i = rounds[0]; i <= n; i++) {
                ans.add(i);
            }
        }
        return ans;
    }

    public int maxCoins(int[] piles) {
        Arrays.sort(piles);
        int n = piles.length / 3;
        int max = 0;

        for (int i = piles.length - 2; i >= 0; ) {
            max += piles[i];
            n--;
            i = i - 2;
            if (n == 0) {
                break;
            }
        }

        return max;
    }

    public int findLatestStep(int[] arr, int m) {
        if (m == arr.length) {
            return m;
        }
        int[] dp = new int[arr.length];
        int maxSteps = Integer.MIN_VALUE;
        Arrays.fill(dp, 0);

        for (int i = 0; i < arr.length; i++) {
            dp[arr[i]] = 1;
            findOnes(dp, arr[i], m);
        }
        return -1;
    }

    private int findOnes(int[] dp, int index, int m) {
        int left = index, right = index;

        while (dp[left] == 1) {
            if (left > 0) {
                left--;
            } else {
                break;
            }
        }
        while (dp[right] == 1) {
            if (right < dp.length) {
                right++;
            } else {
                break;
            }
        }
        return -1;
    }

    public int divide(int dividend, int divisor) {
        long res = 0;
        long m = Math.abs((long) dividend);
        long n = Math.abs((long) divisor);

        while (m >= n) {
            m -= n;
            res++;

            long newN = n + n;
            long count = 2;

            while (m >= newN) {
                m -= newN;
                res += count;
                newN += newN;
                count += count;
            }
        }

        if ((dividend > 0 && divisor > 0) || (dividend < 0 && divisor < 0)) {
            // return (int)Math.min(Math.max(-2147483648, res), 2147483647);
            return res > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) res;

        }
        return -res < Integer.MIN_VALUE ? Integer.MIN_VALUE : (int) -res;

    }

    public boolean containsPattern(int[] arr, int m, int k) {

        int count = 1;
        int i = 0;
        while (i < arr.length - m) {
            int[] sub = Arrays.copyOfRange(arr, i, i + m);
            if (i + m + m <= arr.length
                    && Arrays.equals(sub, Arrays.copyOfRange(arr, i + m, i + m + m))) {
                System.out.println(Arrays.toString(sub) + " is sub!");
                System.out.println(i + " i is");
                count++;
                if (count == k) {
                    return true;
                }
                i += m;
                continue;
            }
            i++;
            count = 1;
        }
        return false;
    }


    //    Input: nums = [-1,-2,-3,0,1]
//Output: 4
//    Input: nums = [1,1,-2,-3,-4]
//Output: 3
    public int getMaxLen(int[] nums) {
        int[] pos = new int[nums.length];
        int[] neg = new int[nums.length];

        if (nums[0] > 0) {
            pos[0] = 1;
        } else if (nums[0] < 0) {
            neg[0] = 1;
        }

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > 0) {
                pos[i] = pos[i - 1] + 1;
                neg[i] = neg[i - 1] > 0 ? neg[i - 1] + 1 : 0;
            } else if (nums[i] < 0) {
                pos[i] = neg[i - 1] > 0 ? neg[i - 1] + 1 : 0;
                neg[i] = pos[i - 1] + 1;
            }
        }
        return pos[pos.length - 1];
    }

    List<List<Integer>> times = new ArrayList<>();

    public String largestTimeFromDigits(int[] A) {
        Arrays.sort(A);
        if (A[0] >= 3) {
            return "";
        }

        if (A[3] == 0) {
            return "00:00";
        }

        boolean[] used = new boolean[4];
        List<Integer> track = new ArrayList<>();
        backTrack(A, track, used);
        return getTime(times);
    }

    private String getTime(List<List<Integer>> times) {
        List<Integer> temp = new ArrayList<>();
        for (List<Integer> row : times) {
            if (timeToInt(row) <= 1439 && timeToInt(row) > timeToInt(temp)) {
                temp = row;
            }
        }
        return temp.size() == 0 ? "" : "" + temp.get(0) + temp.get(1) + ":" + temp.get(2) + temp.get(3);
    }

    private int timeToInt(List<Integer> row) {
        int[] ratio = {600, 60, 10, 1};
        int time = 0;
        for (int i = 0; i < row.size(); i++) {
            time += row.get(i) * ratio[i];
        }
        return time;
    }

    private void backTrack(int[] A, List<Integer> track, boolean[] used) {
        if (track.size() == A.length && track.get(2) != 6) {
            times.add(new ArrayList<>(track));
        }

        for (int i = 0; i < A.length; i++) {
            if (!used[i]) {
                track.add(A[i]);
                used[i] = true;
                backTrack(A, track, used);
                track.remove(track.size() - 1);
                used[i] = false;
            }
        }
    }

    public List<Integer> partitionLabels(String S) {
        // two pointers, [start ... end] -> increase end when last index of inner char > end
        List<Integer> list = new ArrayList<>();
        int end = 0;
        int start = 0;
        for (int i = 0; i < S.length(); i++) {
            end = Math.max(S.lastIndexOf(S.charAt(i)), end);

            if (i == end) {
                list.add(i - start + 1);
                start = i + 1;
            }
        }
        return list;
    }

    public int[] sortedSquares(int[] A) {
        int p1 = 0;
        int p2 = A.length - 1;
        int index = A.length - 1;
        int[] ans = new int[A.length];

        while (p1 <= p2) {
            int l = A[p1] * A[p1];
            int r = A[p2] * A[p2];

            if (l > r) {
                ans[index--] = l;
                p1++;
            } else {
                ans[index--] = r;
                p2--;
            }
        }
        return ans;
    }

    public int findMin(int[] nums) {
        int l = 0;
        int r = nums.length - 1;

        while (l <= r) {
            if (nums[l] < nums[r] || (l == r && nums[l] == nums[r])) {
                return nums[l];
            }

            int m = l + (r - l) / 2;
            if (nums[m] == nums[l] && nums[l] == nums[r]) {
                l++;
                r--;
            } else if (nums[l] <= nums[m]) {
                l = m + 1;
            } else {
                r = m;
            }
        }

        return nums[l];
    }

    public int divisibleOfStrings(String s, String t) {

        if (s.length() % t.length() != 0) {
            return -1;
        }

        int n = t.length();
        int times = s.length() / n;

        for (int i = 0; i < times; i++) {
            if (!s.substring(n * i, n * (i + 1)).equals(t)) {
                return -1;
            }
        }

        int min = 1;

        while (min <= n / 2) {
            if (n % min == 0) {
                String sub = t.substring(0, min);
                //can be concatenated to create string t
                boolean flag = true;

                for (int i = 1; i < n / min; i++) {
                    if (!t.substring(min * i, min * (i + 1)).equals(sub)) {
                        flag = false;
                        break;
                    }
                }

                if (flag) {
                    return min;
                }

            }

            min++;
        }

        return n;

    }

    public int countingPair(int[] nums, int k) {
        // a + k = b
        // map for pre-sorted array
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            int c = map.getOrDefault(n, 0) + 1;
            map.put(n, c);
        }
        int count = 0;

        for (Map.Entry entry : map.entrySet()) {
            if (k == 0) {
                if ((int) entry.getValue() > 1) {
                    count++;
                }
            } else {
                if (map.containsKey((int) entry.getKey() + k)) {
                    count++;
                }
            }
        }
        return count;
    }

    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> l1 = new ArrayList<>();
        List<Integer> l2 = new ArrayList<>();
        getList(root1, l1);
        getList(root2, l2);
        return mergeList(l1, l2);

    }

    private List<Integer> mergeList(List<Integer> l1, List<Integer> l2) {
        int p1 = 0, p2 = 0;
        List<Integer> ans = new ArrayList<>();
        while (p1 < l1.size() && p2 < l2.size()) {
            if (l1.get(p1) < l2.get(p2)) {
                ans.add(l1.get(p1++));
            } else {
                ans.add(l2.get(p2++));
            }
        }
        while (p1 < l1.size()) {
            ans.add(l1.get(p1++));
        }
        while (p2 < l2.size()) {
            ans.add(l2.get(p2++));
        }
        return ans;
    }

    private void getList(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        getList(root.left, list);
        list.add(root.val);
        getList(root.right, list);
    }

    private boolean isVowels(char c) {
        return (c == 'A' || c == 'a' || c == 'e' || c == 'E' || c == 'i' ||
                c == 'I' || c == 'o' || c == 'O' || c == 'u' || c == 'U');
    }

    public String reverseVowels(String s) {
        int p1 = 0;
        int p2 = s.length() - 1;
        char[] c = s.toCharArray();


        while (p1 <= p2) {
            if (!isVowels(c[p1])) {
                p1++;
            }
            if (!isVowels(c[p2])) {
                p2--;
            }
            if (isVowels(c[p1]) && isVowels(c[p2])) {
                char t = c[p1];
                c[p1] = c[p2];
                c[p2] = t;
                p1++;
                p2--;
            }
        }
        return new String(c);
    }

    // weekly 205
    public String modifyString(String s) {
        //??yw?ipkj? 97-122 63 34 59
        char[] c = s.toCharArray();
        int start = 0;
        int i = 34;

        while (start < c.length) {
            while (start < c.length && c[start] != 63) {
                start++;
            }
            if (start < c.length) {
                c[start] += i;
                if (start - 1 >= 0 && c[start] == c[start - 1]) {
                    c[start] += 10;
                }

                if (start + 1 < c.length && c[start] == c[start + 1]) {
                    c[start] += 5;
                }
                if (i == 59) {
                    i = 34;
                }
            }
        }
        return new String(c);
    }

    public int numTriplets(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int count = 0;
        count += findTri(nums1, nums2);
        count += findTri(nums2, nums1);
        return count;
    }

    private int findTri(int[] nums1, int[] nums2) {
        int count = 0;
        int len1 = nums1.length;

        for (int i = 0; i < len1; i++) {
            long k = (long) nums1[i] * (long) nums1[i];
            count += helper(nums2, k);
        }
        return count;
    }

    private int helper(int[] nums, long k) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((long) nums[i] * (long) nums[j] == k) {
                    count++;
                }
            }
        }
        return count;
    }

    public int firstUniqChar(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            int val = 1;
            if (map.containsKey(s.charAt(i))) {
                val = map.get(s.charAt(i)) + 1;
            }
            map.put(s.charAt(i), val);
        }

        for (int i = 0; i < s.length(); i++) {
            if (map.get(s.charAt(i)) == 1) {
                return i;
            }
        }
        return -1;
    }

    //    List<List<Integer>> ans = new ArrayList<>();
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> ans = new ArrayList<>();
        combinationSum2(candidates, target, 0, 0, new ArrayList<>(), ans);
        return ans;
    }

    private void combinationSum2(int[] candidates, int target, int index, int sum, ArrayList<Object> list, List<List<Integer>> ans) {
        if (sum > target || (sum < target && index >= candidates.length)) {
            return;
        }
        if (sum == target) {
            if (!ans.contains(list)) {
                ans.add(new ArrayList(list));
            }
        }

        for (int i = index; i < candidates.length; i++) {
            //if (i - 1 >= 0 && candidates[i] == candidates[i-1]) continue;
            list.add(candidates[i]);
            combinationSum2(candidates, target, i + 1, sum + candidates[i], list, ans);
            list.remove(list.size() - 1);

        }
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums.length < 3) return ans;
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> list = Arrays.asList(nums[i], nums[j], nums[k]);
                        if (!ans.contains(list)) {
                            ans.add(list);
                        }
                    }
                }
            }
        }
        return ans;
    }

    public boolean wordPattern(String pattern, String str) {
        String[] s = str.trim().split("//s+");
        if (pattern.length() != s.length) {
            return false;
        }
        int a = 1;
        int b = 1;
        for (int i = 1; i < pattern.length(); i++) {
            if (pattern.charAt(i - 1) == pattern.charAt(i)) {
                if (s[i] != s[i - 1]) return false;
            } else {
                if (s[i] == s[i - 1]) return false;
            }
        }

        return true;

    }

    public int findPeakElement(int[] nums) {
        int l = 0;
        int r = nums.length - 1;

        while (l <= r) {
            int m = l + (r - l) / 2;

            if (((m + 1 < nums.length && nums[m] > nums[m + 1]) || m + 1 > nums.length - 1) &&
                    ((m - 1 >= 0 && nums[m] > nums[m - 1]) || m - 1 < 0)) {
                return m;
            }

            if ((m + 1 < nums.length - 1 && nums[m] > nums[m + 1]) || m + 1 > nums.length - 1) {
                r = m - 1;
            } else {
                l = m + 1;
            }

        }
        return l;
    }

    public int minSubArrayLen(int s, int[] nums) {
        int left = 0, right = 0, sum = 0;
        int len = Integer.MAX_VALUE;

        while (right < nums.length) {
            sum += nums[right];

            while (sum >= s) {
                len = Math.min(len, right - left + 1);
                sum -= nums[left];
                left++;
            }
            right++;
        }
        return len == Integer.MAX_VALUE ? 0 : len;
    }

    public int sumRootToLeaf(TreeNode root) {
        if (root == null) return 0;
        List<List<Integer>> paths = new ArrayList<>();
        getPath(root, paths, new ArrayList());
        System.out.println(paths);
        return getSum(paths);
    }

    private int getSum(List<List<Integer>> paths) {
        int sum = 0;
        for (List<Integer> l : paths) {
            int temp = 0;
            int count = 0;
            for (int i = l.size() - 1; i >= 0; i--) {
                temp += l.get(i) * Math.pow(2, count++);
            }
            sum += temp;
        }
        return sum;
    }

    private void getPath(TreeNode root, List<List<Integer>> paths, List<Integer> list) {
        if (root == null) return;
        list.add(root.val);
        if (root.left == null && root.right == null) {
            paths.add(new ArrayList<>(list));
            return;
        }
        getPath(root.left, paths, list);
        if (root.left != null)
            list.remove(list.size() - 1);
        getPath(root.right, paths, list);
        if (root.right != null)
            list.remove(list.size() - 1);
    }

    public boolean isAnagram(String s, String t) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }

        for (int i = 0; i < t.length(); i++) {
            if (map.containsKey(t.charAt(i)) && map.get(t.charAt(i)) > 0) {
                map.put(t.charAt(i), map.get(t.charAt(i)) - 1);
                if (map.get(t.charAt(i)) == 0)
                    map.remove(t.charAt(i));
            } else {
                return false;
            }
        }
        return map.isEmpty();
    }

    public int kthSmallest(TreeNode root, int k) {
        List<Integer> list = new ArrayList<>();
        kthSmallest(root, k, list);
        System.out.println(list);
        return list.get(k - 1);

    }

    private void kthSmallest(TreeNode root, int k, List<Integer> list) {

        if (root == null) return;

        kthSmallest(root.left, k, list);
        list.add(root.val);
        kthSmallest(root.right, k, list);
    }

    public String getHint(String secret, String guess) {
        int a = 0, b = 0;

        int[] nums = new int[10]; // all digits
        for (int i = 0; i < guess.length(); i++) {
            if (secret.charAt(i) == guess.charAt(i)) {
                a++;
            } else {
                if (nums[secret.charAt(i) - '0']++ < 0) {
                    b++;
                }
                if (nums[guess.charAt(i) - '0']-- > 0) {
                    b++;
                }
            }
        }
        return a + "A" + b + "B";
    }

    public int maxProduct(int[] nums) {
        int[] pos = new int[nums.length + 1];
        int[] neg = new int[nums.length + 1];
        int ans = 0;


        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                pos[i + 1] = Math.max(pos[i] * nums[i], nums[i]);
                neg[i + 1] = Math.min(neg[i] * nums[i], nums[i]);
            } else if (nums[i] < 0) {
                pos[i + 1] = Math.max(neg[i] * nums[i], nums[i]);
                neg[i + 1] = Math.min(pos[i] * nums[i], nums[i]);
            } else {
                pos[i + 1] = Math.max(pos[i] * nums[i], nums[i]);
                neg[i + 1] = Math.min(neg[i] * nums[i], nums[i]);
            }
            ans = Math.max(ans, pos[i + 1]);
        }
        System.out.println(Arrays.toString(pos));
        return ans;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int row = matrix.length;
        int col = row == 0 ? 0 : matrix[0].length;
        return searchMatrix(matrix, target, 0, 0, row - 1, col - 1);
    }

    private boolean searchMatrix(int[][] matrix, int target, int lRow, int lCol, int hRow, int hCol) {
        int row = matrix.length;
        int col = row == 0 ? 0 : matrix[0].length;
        if ((lCol + lRow > hCol + hRow) ||
                lRow >= row || lCol >= col || hRow < 0 || hCol < 0) {
            return false;
        }
        int mRow = lRow + (hRow - lRow) / 2;
        int mCol = lCol + (hCol - lCol) / 2;

        if (matrix[mRow][mCol] == target ||
                matrix[lRow][lCol] == target ||
                matrix[hRow][hCol] == target) {
            return true;
        } else if (matrix[mRow][mCol] > target) {
            return searchMatrix(matrix, target, lRow, lCol, hRow, mCol - 1) ||
                    searchMatrix(matrix, target, lRow, mCol, mRow - 1, hCol);
        } else if (matrix[mRow][mCol] < target) {
            return searchMatrix(matrix, target, lRow, mCol + 1, mRow, hCol) ||
                    searchMatrix(matrix, target, mRow + 1, lCol, hRow, hCol);
        }
        return false;
    }

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> lists = new ArrayList<>();
        combinationSum3(k, n, lists, new ArrayList<>(), 1);
        return lists;
    }

    private void combinationSum3(int k, int n, List<List<Integer>> lists, ArrayList<Integer> list, int index) {
        if (list.size() == k && n == 0) {
            lists.add(new ArrayList<>(list)); // deep copy
            return;
        } else if (n < 0 || list.size() == k) {
            return;
        }

        for (int i = index; i <= 9; i++) {
            list.add(i);
            System.out.println(list);
            combinationSum3(k, n - i, lists, list, i + 1);
            list.remove(list.size() - 1);
        }
    }

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        if (root == null) return new ArrayList<>();

        List<List<Integer>> ans = new ArrayList<>();
        Deque<TreeNode> deque = new LinkedList<>();
        deque.add(root);

        while (!deque.isEmpty()) {
            List<Integer> list = new LinkedList<>();
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = deque.pollFirst();
                if (node.left != null) deque.addLast(node.left);
                if (node.right != null) deque.addLast(node.right);
                list.add(node.val);
            }
            // bottom to top with index 0
//            ans.add(0, list);
            ans.add(list);
        }
        return ans;
    }

    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
//        if (root == null) return null;
//
//        Deque<TreeNode> deque = new LinkedList<>();
//        deque.add(root);
//
//        while (!deque.isEmpty()) {
//            TreeNode node = deque.pollFirst();
//            if (node.left != null) deque.add(node.left);
//            if (node.right != null) deque.add(node.right);
//            TreeNode temp = node.left;
//            node.left = node.right;
//            node.right = temp;
//
//        }
//        return root;
    }

    public int numSpecial(int[][] mat) {
//        int count = 0;
//        int row = mat.length;
//        int col = row == 0 ? 0 : mat[0].length;
//
//        for (int i = 0; i < row; i++) {
//            for (int j = 0; j < col; j++) {
//
//                if (mat[i][j] == 1 && valid(mat, i, j, row, col)) {
//                    count++;
//                }
//
//            }
//        }
//        return count;
        int count = 0;
        int row = mat.length;
        int col = row == 0 ? 0 : mat[0].length;
        for (int i = 1; i < row; i++) {
            mat[i][0] += mat[i - 1][0];
        }
        for (int i = 1; i < col; i++) {
            mat[0][i] += mat[0][i];
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                mat[i][j] += mat[i - 1][j] + mat[i][j - 1];
            }
        }
        for (int i = 0; i < row; i++) {
            if (mat[row - 1][i] == 1) count++;
        }
        System.out.println(Arrays.toString(mat[0]));
        return count;
    }

    private boolean valid(int[][] mat, int i, int j, int row, int col) {
        int sum = 1;
        int r = i;
        while (r + 1 < row) {
            sum += mat[r + 1][j];
            r++;
        }
        int c = j;
        while (c + 1 < col) {
            sum += mat[i][c + 1];
            c++;
        }
        r = i;
        while (r - 1 >= 0) {
            sum += mat[r - 1][j];
            r--;
        }
        c = j;
        while (c - 1 >= 0) {
            sum += mat[i][c - 1];
            c--;
        }

        return sum == 1;
    }

    public int unhappyFriends(int n, int[][] preferences, int[][] pairs) {
        int count = 0;
        for (int i = 0; i < n / 2; i++) {
//            if (preferences[pairs[i][0]][0] != pairs[i][1]) count++;
//            if (preferences[pairs[i][1]][0] != pairs[i][0]) count++;
//            int x = pairs[i][0];
//            int y = pairs[i][1];
//            if (preferences[x][0] != y && preferences[preferences[x][0]][0])
            if (preferences[pairs[i][0]][0] != pairs[i][1] && preferences[preferences[pairs[i][0]][0]][0] == pairs[i][0])
                count++;
            if (preferences[pairs[i][1]][0] != pairs[i][0] && preferences[preferences[pairs[i][1]][0]][0] == pairs[i][1])
                count++;
        }
        return count;
    }

    public int minCostConnectPoints(int[][] points) {
        int ans = 0;
        boolean[][] isConnected = new boolean[points.length][points.length];

        if (points.length <= 1) return 0;
        for (int i = 0; i < points.length; i++) {
            ans += getMinDist(points, i, isConnected);
//            System.out.println(ans);
        }
        return ans;
    }

    private int getMinDist(int[][] points, int i, boolean[][] isConnected) {
        int index = -1;
        int dist = Integer.MAX_VALUE;
        for (int j = 0; j < points.length; j++) {
            if (j != i) {
                if (!isConnected[i][j]) {
                    int temp = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                    if (dist > temp) {
                        dist = temp;
                        index = j;
                    }
                }
            }
        }
        System.out.println(dist);
        isConnected[i][index] = true;
        isConnected[index][i] = true;
        return dist;
    }

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> lists = new ArrayList<>();
        pathSum(lists, root, sum, new ArrayList<>());
        return lists;
    }

    private void pathSum(List<List<Integer>> lists, TreeNode root, int sum, ArrayList<Integer> list) {
        if (root == null) return;
        if (sum == root.val && root.left == null && root.right == null) {
            list.add(root.val);
            lists.add(new ArrayList<>(list));
            return;
        }

        list.add(root.val);
        pathSum(lists, root.left, sum - root.val, list);
        pathSum(lists, root.right, sum - root.val, list);
        list.remove(list.size() - 1);
    }

    public int rob(int[] nums) {
//        int[] dp = new int[nums.length + 2];
//
//        for (int i = nums.length - 1; i >= 0 ; i--) {
//            dp[i] = Math.max(nums[i] + dp[i+2], dp[i+1]);
//        }
//        return dp[0];

        return 0;
    }

    public boolean isRobotBounded(String instructions) {
//        int[][] direction = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}}; // L++ steps != 0 &&
        int[] x = {0, -1, 0, 1};
        int[] y = {1, 0, -1, 0};
        int x0 = 0, y0 = 0;
        int steps = 0;
        for (int i = 0; i < 2 * instructions.length(); i++) {
            if (instructions.charAt(i) == 'L') {
                steps++;
            } else if (instructions.charAt(i) == 'R') {
                steps += 3;
            } else {
                x0 += x[steps % 4];
                y0 += y[steps % 4];
            }
        }
        return (x0 == 0 && y0 == 0) || (steps % 4 != 0);
    }

    public String reorderSpaces(String text) {
        if (text.length() <= 1) return text;
        StringBuilder sb = new StringBuilder();
        int size = text.length();

        char[] c = text.toCharArray();
        int count = 0;
        int index = 0;
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            if (c[i] == ' ') {
                if (sb.length() != 0) {
                    map.put(index++, sb.toString());
                    sb = new StringBuilder();
                }
                count++;
                continue;
            }
            sb.append(c[i]);
        }
        if (sb.length() != 0) map.put(index++, sb.toString());

        sb = new StringBuilder();
        System.out.println(count);
        System.out.println(map);

        if (map.size() == 1) {
            sb.append(map.get(0));
            while (count > 0) {
                sb.append(" ");
                count--;
            }
            return sb.toString();
        }

        int need = count / (map.size() - 1);
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < need; i++) {
            space.append(" ");
        }
//        for (String  s : map.values()) {
//            sb.append(s);
//            sb.append(space);
//        }

        for (int i = 0; i < map.size() - 1; i++) {
            sb.append(map.get(i));
            sb.append(space);
        }
        sb.append(map.get(index - 1));
        int k = count % (map.size() - 1);
        while (k != 0) {
            sb.append(" ");
            k--;
        }


//        String[] arr = text.split(" ");
//        System.out.println(Arrays.toString(arr));
        return sb.toString();
    }

    public int maxProductPath(int[][] grid) {
//        List<List<Integer>> list = new ArrayList<>();
//        allPath(grid, list, 0, 0, new ArrayList<>());
////        System.out.println(list);
//        return (int) (getMaxProduct(list) % (Math.pow(10,9) + 7));

        int row = grid.length;
        int col = grid[0].length;
        int[][] pos = new int[row][col];
        int[][] neg = new int[row][col];
        pos[0][0] = grid[0][0];
        neg[0][0] = grid[0][0];

        for (int i = 1; i < row; i++) {
            pos[i][0] = grid[i - 1][0] * grid[i][0];
            neg[i][0] = pos[i][0] = grid[i - 1][0] * grid[i][0];
        }
        for (int i = 1; i < col; i++) {
            pos[0][i] = grid[0][i - 1] * grid[0][i];
            neg[0][i] = pos[0][i] = grid[0][i - 1] * grid[0][i];
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {

//                pos[i][j] = Math.max(Math.max(pos[i-1][j]*grid[i][j],neg[i-1][j]*grid[i][j] ),Math.max(pos[i][j-1]*grid[i][j],neg[i][j-1]*grid[i][j] ));
//
//                neg[i][j] = Math.min(Math.min(pos[i-1][j]*grid[i][j],neg[i-1][j]*grid[i][j] ),Math.min(pos[i][j-1]*grid[i][j],neg[i][j-1]*grid[i][j] ));
                pos[i][j] = Math.max(Math.max(pos[i][j - 1] * grid[i][j], pos[i - 1][j] * grid[i][j]), Math.max(neg[i][j - 1] * neg[i][j], neg[i - 1][j] * neg[i][j]));
                System.out.println(pos[i][j]);
                neg[i][j] = Math.min(Math.min(pos[i][j - 1] * grid[i][j], pos[i - 1][j] * grid[i][j]), Math.min(neg[i][j - 1] * neg[i][j], neg[i - 1][j] * neg[i][j]));
            }
        }
        for (int i = 0; i < row; i++) {
            System.out.println(Arrays.toString(pos[i]));
        }
        return pos[row - 1][col - 1] < 0 ? -1 : (int) (pos[row - 1][col - 1] % 1000000007L);
    }

    private int getMaxProduct(List<List<Integer>> list) {
        int max = Integer.MIN_VALUE;
        List<Integer> ans = new ArrayList<>();
        for (List<Integer> l : list) {
            int t = 1;
            for (int i = 0; i < l.size(); i++) {
                if (l.get(i) == 0) {
                    t = 0;
                    break;
                }
                t = t * l.get(i);
            }
            ans = max > t ? ans : l;
            max = Math.max(max, t);
        }
        System.out.println(ans);
        return max < 0 ? -1 : max;
    }


    private void allPath(int[][] grid, List<List<Integer>> list, int r, int c, ArrayList<Integer> way) {
        if (r >= grid.length) return;
        if (c >= grid[0].length) return;
        way.add(grid[r][c]);
        if (r == grid.length - 1 && grid[0].length - 1 == c) {
            list.add(new ArrayList(way));
        }

        allPath(grid, list, r + 1, c, way);
        allPath(grid, list, r, c + 1, way);
        way.remove(way.size() - 1);

    }

    public List<Integer> majorityElement(int[] nums) {
        List<Integer> ans = new ArrayList<>();
        int k = nums.length / 3;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            if (map.get(nums[i]) > k && !ans.contains(nums[i])) ans.add(nums[i]);
            if (ans.size() == 2) break;
        }
        return ans;
    }

    public int canCompleteCircuit(int[] gas, int[] cost) {
        // solution exits when totalGas >= totalCost => totalGas - totalCost >=0
        // i is not the solution when gas[i] - cost[i] < 0
        int totalDiff = 0;
        int currentDiff = 0;
        int pos = 0;
        for (int i = 0; i < gas.length; i++) {
            int diff = gas[i] - cost[i];
            currentDiff += diff;
            totalDiff += diff;
            if (currentDiff < 0) {
                currentDiff = 0;
                pos = i + 1;
            }
        }
        return totalDiff < 0 ? -1 : pos;
    }

    public char findTheDifference(String s, String t) {
//        Map<Character, Integer> map = new HashMap<>();
//        for (int i = 0; i < s.length(); i++) {
//            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
//        }
//
//        for (int i = 0; i < t.length(); i++) {
//            char c = t.charAt(i);
//            if (map.containsKey(c)) {
//                map.put(c, map.get(c) - 1);
//            } else {
//                return c;
//            }
//            if (map.get(c) == 0) map.remove(c);
//        }
//        return 'a';

//        int sum1 = 0;
//        int sum2 = 0;
//        for (int i = 0; i < s.length(); i++) {
//            sum1 += s.charAt(i);
//            sum2 += t.charAt(i);
//        }
//        sum2 += t.charAt(t.length() - 1);
//        return (char) (sum2 - sum1);

        char ans = t.charAt(t.length() - 1);
        for (int i = 0; i < s.length(); i++) {
            ans ^= s.charAt(i);
            ans ^= t.charAt(i);
        }
        return ans;
    }

    public String largestNumber(int[] nums) {
        List<String> str = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            str.add(String.valueOf(nums[i]));
        }
        System.out.println(str);
//        Arrays.sort(str, new Comparator<String>() {
//            @Override
//            public int compare(String a, String b) {
//                return (a+b).compareTo(b+a);
//            }
//        });
        str.sort((a, b) -> (a + b).compareTo(b + a));
        System.out.println(str);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = nums.length - 1; i >= 0; i--) {
            stringBuilder.append(str.get(i));
        }
        if (stringBuilder.charAt(0) == '0') return "0";
        return stringBuilder.toString();
    }

    public int findPoisonedDuration(int[] timeSeries, int duration) {
        int time = duration * timeSeries.length;
        for (int i = 1; i < timeSeries.length; i++) {
            int diff = timeSeries[i] - timeSeries[i - 1];
            if (diff < duration) {
                time -= duration;
                time += diff;
            }
        }
        return time;
    }

    public int minOperations(String[] logs) {
//        Deque<Integer> deque = new ArrayDeque<>();
//
//        for (int i = 0; i < logs.length; i++) {
//            if (logs[i] == "../") {
//                if (!deque.isEmpty())
//                    deque.pollLast();
//            } else if (logs[i] == "./") {
//                continue;
//            } else {
//                deque.addLast(1);
//            }
//        }
//        return deque.size();
        int ans = 0;

        for (int i = 0; i < logs.length; i++) {
            if (logs[i].equals("../")) {
                if (ans != 0) {
                    ans--;
                }
            } else if (logs[i].equals("./")) {
                continue;
            } else {
                ans++;
            }
        }
        return ans;

    }

    public int minOperationsMaxProfit(int[] customers, int boardingCost, int runningCost) {
        int profit = 0;
        int wait = 0;
        int rotate = 0;
        Pair<Integer, Integer> p = new Pair<>(profit, rotate);

        for (int i = 0; i < customers.length; i++) {
            wait += customers[i];
            if (wait >= 4) {
                profit += 4 * boardingCost - runningCost;
                wait -= 4;
                rotate++;
            } else {
                profit += wait * boardingCost - runningCost;
                wait = 0;
                rotate++;
            }
            if (p.getKey() < profit) {
                p = new Pair<>(profit, rotate);
            }
//            System.out.println(profit);
        }
        while (wait != 0) {
            if (wait >= 4) {
                profit += 4 * boardingCost - runningCost;
                wait -= 4;
                rotate++;
            } else {
                profit += wait * boardingCost - runningCost;
                wait = 0;
                rotate++;
            }
            if (p.getKey() < profit) {
                p = new Pair<>(profit, rotate);
            }
            System.out.println(p);
//            System.out.println(profit);
        }
        return p.getKey() >= 0 ? p.getValue() : -1;
    }

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Map<String, Double>> map = getGraph(equations, values);

        double[] ans = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            ans[i] = getWeightPath(queries.get(i).get(0), queries.get(i).get(1), new HashSet<>(), map);
        }
        return ans;
    }

    private double getWeightPath(String a, String b, HashSet<Object> visited, Map<String, Map<String, Double>> map) {
        if (!map.containsKey(a)) return -1.0;
        if (map.get(a).containsKey(b)) return map.get(a).get(b);

        visited.add(a);
        for (Map.Entry<String, Double> bridge : map.get(a).entrySet()) {
            if (!visited.contains(bridge.getKey())) {
                double temp = getWeightPath(bridge.getKey(), b, visited, map);
                if (temp != -1.0) {
                    return temp * bridge.getValue();
                }
            }
        }
        return -1.0;
    }

    private Map<String, Map<String, Double>> getGraph(List<List<String>> equations, double[] values) {
        Map<String, Map<String, Double>> map = new HashMap<>();
        for (int i = 0; i < equations.size(); i++) {
            String u = equations.get(i).get(0);
            String v = equations.get(i).get(1);
            map.put(u, new HashMap<>());
            map.get(u).putIfAbsent(v, values[i]);
            System.out.println(map);
            map.put(v, new HashMap<>());
            map.get(v).putIfAbsent(u, 1 / values[i]);
            System.out.println(map);
        }
        return map;
    }

    public int specialArray(int[] nums) {
        Arrays.sort(nums);
        int len = nums.length;
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= len) {
                if (i >= 1 && nums[i - 1] >= len) {
                    continue;
                }
                return len;
            }
            len--;
        }
        return -1;
    }

    public boolean isEvenOddTree(TreeNode root) {
        List<List<Integer>> lists = levelOrderBottom(root);
        return evenOdd(lists);
    }

    private boolean evenOdd(List<List<Integer>> lists) {
        int level = 0;
        for (List<Integer> l : lists) {
            if (level % 2 == 0) {
                for (int i = 0; i < l.size(); i++) {
                    if (l.get(i) % 2 != 1) return false;
                    if (i >= 1 && l.get(i - 1) >= l.get(i)) return false;
                }
            } else {
                for (int i = 0; i < l.size(); i++) {
                    if (l.get(i) % 2 != 0) return false;
                    if (i >= 1 && l.get(i - 1) <= l.get(i)) return false;
                }
            }
            level++;
        }
        return true;
    }

    public int visiblePoints(List<List<Integer>> points, int angle, List<Integer> location) {
        List<Double> slope = getSlope(points, location);
        int i = 0;
        int j = slope.size();
        int ans = slope.size();
        while (i <= j) {
            if (Math.abs(slope.get(i) - slope.get(j)) <= angle) {
                return ans;
            }
        }
        return ans;
    }

    private List<Double> getSlope(List<List<Integer>> points, List<Integer> location) {
        List<Double> ans = new ArrayList<>();
        int x = location.get(0);
        int y = location.get(1);
        for (List<Integer> l : points) {
            int x1 = l.get(0);
            int y1 = l.get(1);
            double s = (y - y1) / (x - x1);
            ans.add(Math.atan(s));
        }
        return ans;
    }

    public int maxDepth(String s) {
//        String[] str = s.split("\\+");
//        System.out.println(Arrays.toString(str));
//        int ans = 0;
//        for (int i = 0; i < str.length; i++) {
//
//        }
        int count = 0;
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(')
                count++;
            if (s.charAt(i) == ')')
                count--;
            ans = Math.max(count, ans);
        }
        return count;
    }

    public int maximalNetworkRank(int n, int[][] roads) {
        Map<int[], Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int count = 0;
                for (int[] A : roads) {
                    if (A[0] == i || A[1] == j ||
                            A[1] == i || A[0] == j) {
                        count++;
                    }
                }
                map.put(new int[]{i, j}, count);
            }
        }
        int p1 = 0;
        int p2 = 0;
        for (int i : map.values()) {
            if (i > p1) {
                p2 = p1;
                p1 = i;
            }
        }
        return p1 + p2;
    }

    public boolean checkPalindromeFormation(String a, String b) {
        if (isPalin(a) || isPalin(b)) return true;
        return helperP(a, b, 0) || helperP(b, a, 0);
    }

    private boolean helperP(String a, String b, int index) {
// b = "jizalu"  ulacfd",
//        for (int i = 0; i < a.length(); i++) {
//            for (int j = b.length()-1; j >=0; j--) {
//                if (a.charAt(i) == b.charAt(j)) {
//
//                }
//            }
//        }
//        return false;
        for (int i = index; i < a.length(); i++) {
            if ((a.length() - index) % 2 == 0) {
                if (a.charAt(i) == b.charAt(b.length() - 1 - i))
                    return true;
            } else {
                if (a.charAt(i) == b.charAt(i))
                    return helperP(a, b, i + 1);
            }
        }
        return false;
    }

    private boolean isPalin(String a) {
        int p1 = 0;
        int p2 = a.length() - 1;
        while (p1 <= p2) {
            if (a.charAt(p1) != a.charAt(p2))
                return false;
            p1++;
            p2--;
        }
        return true;
    }

    public int maxLengthBetweenEqualCharacters(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int len = -1;
        for (int i = 0; i < s.length(); i++) {
            int t = -1;
            char c = s.charAt(i);
            if (map.containsKey(s.charAt(i))) {
                t = i - map.get(s.charAt(i)) - 1;
            } else {
                map.put(s.charAt(i), i);
            }
            len = Math.max(t, len);
        }
        return len;
    }

    public String findLexSmallestString(String s, int a, int b) {
        char[] arr = s.toCharArray();

        if (b % 2 == 0) {

        }

        return "";
    }

    public int bestTeamScore(int[] scores, int[] ages) {
        int[][] p = new int[scores.length][2];
        for (int i = 0; i < scores.length; i++) {
            p[i] = new int[]{scores[i], ages[i]};
        }

        Arrays.sort(p, (a, b) -> a[1] == b[1] ? a[0] - b[0] : a[1] - b[1]);
        for (int[] i : p) {
            System.out.println(Arrays.toString(i));
        }


        int ans = Integer.MIN_VALUE;
        int[] dp = new int[p.length];

        for (int i = 0; i < dp.length; i++) {
            int sum = 0;
            int age = 0;
            int sMin = -1;

            for (int k = 0; k < i; k++) {
                if (p[i][0] >= p[k][0])
                    sum += p[k][0];
            }

            for (int j = i; j < p.length; j++) {

                if (p[j][1] == age) {
                    sum += p[j][0];
                    sMin = Math.max(sMin, p[j][0]);
                } else if (p[j][1] > age && p[j][0] >= sMin) {
                    sum += p[j][0];
                    age = p[j][1];
                    sMin = p[j][0];
                }
            }

            dp[i] = sum;
            ans = Math.max(ans, dp[i]);
        }
        System.out.println(Arrays.toString(dp));
        return ans;
    }

    public int bagOfTokensScore(int[] tokens, int P) {
        // terminate when you play all the conin or tokens[0] > P
        // O(n)

        Arrays.sort(tokens);
        int p1 = 0;
        int p2 = tokens.length - 1;
        int pt = 0;
        int ans = 0;

        while (p1 <= p2) {
            if (P >= tokens[p1]) {
                pt++;
                P -= tokens[p1++];
                ans = Math.max(ans, pt);
            } else if (P > 0) {
                pt--;
                P += tokens[p2--];
            } else {
                // p < tokens[0]
                break;
            }
        }
        return ans;
    }

    public char slowestKey(int[] releaseTimes, String keysPressed) {
        Pair<Integer, Character> ans = new Pair<>(0, ' ');
        for (int i = 0; i < releaseTimes.length; i++) {
            if (i == 0) {
                ans = new Pair<>(releaseTimes[i], keysPressed.charAt(i));
            } else {
                int temp = releaseTimes[i] - releaseTimes[i - 1];
                if (temp >= ans.getKey()) {
                    ans = new Pair<>(temp, keysPressed.charAt(i));
                }
            }
        }
        return ans.getValue();
    }

    public List<Boolean> checkArithmeticSubarrays(int[] nums, int[] l, int[] r) {
        List<Boolean> ans = new ArrayList<>();

        for (int i = 0; i < l.length; i++) {
            int[] temp = Arrays.copyOfRange(nums, l[i], r[i] + 1);
            System.out.println(Arrays.toString(temp));
            ans.add(check(temp));
        }
        return ans;
    }

    private Boolean check(int[] nums) {
        Arrays.sort(nums);
        int diff = nums[1] - nums[0];
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i + 1] - nums[i] != diff)
                return false;
        }
        return true;
    }

    public int[] moveZero(int[] nums) {

        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[count++] = nums[i];
            }
        }

        while (count < nums.length) {
            nums[count++] = 0;
        }
        System.out.println(Arrays.toString(nums));
        return nums;
    }

    public boolean addString(String a, String b) {
        String ans = "";
        int l1 = a.length() - 1;
        int l2 = b.length() - 1;
        int carry = 0;

        while (l1 >= 0 || l2 >= 0) {
            int sum = 0;
            if (l1 >= 0) sum += a.charAt(l1--) - '0';
            if (l2 >= 0) sum += b.charAt(l2--) - '0';
            sum += carry;

            carry = sum / 10;
            ans = (char) ((sum % 10) + '0') + ans;
//            stringBuilder.append((char) ((sum % 10) + '0'));
        }

        if (carry != 0) {
            ans = "1" + ans;
        }
        System.out.println(ans);
        return Integer.parseInt(ans) == Integer.parseInt(a) + Integer.parseInt(b);
    }

    public char firstNonRepeated(String s) {


        HashMap<Character, Integer> map = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }


        for (int i = 0; i < s.length(); i++) {
            if (map.get(s.charAt(i)) == 1) {
                return s.charAt(i);
            }
        }
        return ' ';

    }

    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> map = new HashMap<>();

        for (String s : words) {
            map.put(s, map.getOrDefault(map.get(s), 0) + 1);
        }

        System.out.println(map.toString());

        PriorityQueue<String> q = new PriorityQueue<>((a, b) -> map.get(b) - map.get(a));
        List<String> ans = new ArrayList<>();

        for (String str : map.keySet()) {
            q.add(str);
        }
        System.out.println(q);
        for (int i = 1; i <= k; i++) {
            ans.add(q.poll());
        }
        return ans;
    }

    public int furthestBuilding(int[] heights, int bricks, int ladders) {

        PriorityQueue<Integer> q = new PriorityQueue<>();

        for (int i = 1; i < heights.length; i++) {
            int diff = heights[i] - heights[i - 1];
            if (diff > 0) {
                if (q.size() == ladders) {
                    q.offer(diff);
                    bricks -= q.poll();
                    if (bricks < 0) return i - 1;
                } else {
                    q.offer(diff);
                }
            }
        }

        return 0;
    }

    public String kthSmallestPath(int[] destination, int k) {
        int numsOfH = destination[1];
        int numsOfV = destination[0];

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < numsOfH; i++) {
            sb.append("H");
        }

        return sb.toString();
    }

    public int minDeletions(String s) {
        //ceabaacb
        //a3b2c2e1
        //aaabbbcc
        //a3b3c2

//        "bbcebab"
//          a1b4c1e1

        Set<Integer> set = new HashSet<>();

        char[] c = s.toCharArray();
        Arrays.sort(c);
        int ans = 0;
        int count = 1;

        for (int i = 1; i < c.length; i++) {
            if (c[i - 1] == c[i]) {
                count++;
            } else {
                while (!set.add(count)) {
                    if (count == 0) break;
                    count--;
                    ans++;
                }
                count = 1;
            }
        }

        while (!set.add(count)) {
            if (count == 0) break;

            count--;
            ans++;
        }

        return ans;
    }

    public int maxProfit(int[] inventory, int orders) {
        PriorityQueue<Integer> q = new PriorityQueue<>((a, b) -> b - a);

        for (int i = 0; i < inventory.length; i++) {
            q.offer(inventory[i]);
        }

        long profit = 0;
        long a = 1000000000;


        if (q.size() == 1) {

        }

        while (orders > 0) {
            long temp = q.poll();
            if (!q.isEmpty() && orders > temp - q.peek()) {
                long next = q.peek();
                profit += (temp + (temp - next)) * (temp - next) / 2;
                System.out.println(profit);
                orders -= (temp - next);
                q.offer((int) (temp - next));
            } else {
                profit += (temp + (temp - orders + 1)) * orders / 2;
                orders = -1;
            }
        }


        return (int) (profit % (1000000000 + 7));
    }

    public String decodeString(String s) {
        Deque<Character> d = new LinkedList<>();
        for (char c : s.toCharArray())
            d.offer(c);
        return helper(d);
    }

    private String helper(Deque<Character> d) {
        StringBuilder sb = new StringBuilder();
        int num = 0;
        while (!d.isEmpty()) {
            char c = d.poll();
            // 2 or more digits number
            if (c >= '0' && c <= '9') {
                num = num * 10 + c - '0';
            } else if (c == '[') {
                String sub = helper(d);
                for (int i = num; i > 0; i--) {
                    sb.append(sub);
                }
                num = 0;
            } else if (c == ']') {
                //break the while loop and return the sub.
                break;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public int minOperations(int[] nums, int x) {
        // targetSum is the sum to make x = 0; the middle part
        int targetSum = -x;
        for (int n : nums) targetSum += n;

        if (targetSum < 0) return -1;
        if (targetSum == 0) return nums.length;

        int start = 0;
        int end = 0;

        int sum = 0;
        int ans = -1;
        while (end < nums.length) {
            while (end < nums.length && sum < targetSum) {
                sum += nums[end++];
            }

            if (sum == targetSum) {
                ans = Math.max(ans, end - start);
            }

            sum -= nums[start++];
        }
        return ans == -1 ? -1 : nums.length - ans;

    }

    public String getSmallestString(int n, int k) {
//        String str = "0abcdefghijklmnopqrstuvwxyz";

        char[] c = new char[n];
        Arrays.fill(c, 'a');

        int target = k - n;
//        System.out.println(target);

        for (int i = n - 1; i >= 0; i--) {
            if (target >= 1) {
                if (target > 25) {
                    c[i] = 'z';
                    target -= 25;
                } else {
                    c[i] = (char) (target + 'a');
                    break;
                }
            }
        }

//        System.out.println(new String(c));

        return new String(c);
    }

    public int waysToMakeFair(int[] nums) {
        int count = 0;
        int even = 0;
        int odd = 0;

        for (int i = 0; i < nums.length; i++) {
            if (i % 2 == 0) {
                even += nums[i];
            } else {
                odd += nums[i];
            }
        }

        int preEven = 0;
        int preOdd = 0;
        for (int i = 0; i < nums.length; i++) {
            int temp = odd;
            odd = even - nums[i];
            even = temp;

            if (i % 2 != 0) {
                if (odd + preEven == even + preOdd) count++;
            } else {
                if (odd + preOdd == even + preEven) count++;
            }

            if (i % 2 == 0) {
                preEven += nums[i];
            } else {
                preOdd += nums[i];
            }
        }

        return count;
/*        4,1,1,2,5,1,5,4 /// 111
e   o   ne  no
15  8   0   0
8   11  4   0
11  7   4   1
7   10
//
2   1   0   0
4,1,2,5,1,5,4
11 11

 */

    }

    public int[] mostCompetitive(int[] nums, int k) {
        Stack<Integer> st = new Stack<>();

        int len = nums.length;

        for (int i = 0; i < len; i++) {
            while (!st.isEmpty() && nums[i] < st.peek() && len - i + st.size() - 1 >= k) {
                st.pop();
            }

            if (st.size() < k) st.push(nums[i]);

        }

        int[] ans = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            ans[i] = st.pop();
        }
        return ans;
    }

    public int[] merge(int[] a, int[] b) {
        int[] ans = new int[a.length + b.length];
        int idx = 0;

        for (int n : a) {
            ans[idx++] = n;
        }

        for (int n : b) {
            ans[idx++] = n;
        }

        return ans;
    }

    public List<Integer> getElement(List<Integer> list, Map<String, String> map) {

        for (String str : map.values()) {
            Integer i = Integer.valueOf(str);
            while (list.contains(i))
                list.remove(i);
        }
        return list;

    }

    public int minPartitions(String n) {
        char[] chars = n.toCharArray();
        int max = Integer.MIN_VALUE;
        for (char c : chars) {
            if (c - '0' > max) max = c - '0';
        }
        return max;
    }

    int a = 0;
    int b = 0;

    public int stoneGameVII(int[] stones) {
        int sum = 0;
        for (int i : stones) sum += i;
//
//        int a = 0;
//        int b = 0;

        helper(0, stones.length - 1, stones, sum, 0);

        return a - b;
    }

    private void helper(int left, int right, int[] nums, int sum, int steps) {
        if (left >= right) return;

        if (steps % 2 == 0) {
            if (nums[left] > nums[right]) {
                a += sum - nums[right];
                sum -= nums[right--];
            } else {
                a += sum - nums[left];
                sum -= nums[left++];
            }
            helper(left, right, nums, sum, steps + 1);
        } else {
            if (right - left == 1) {
                b += Math.max(nums[left], nums[right]);
                helper(left + 1, right, nums, sum, steps + 1);
            } else {
                if (nums[left] > nums[right]) {
                    b += sum - nums[left];
                    sum -= nums[left++];
                } else {
                    a += sum - nums[right];
                    sum -= nums[right--];
                }
                helper(left, right, nums, sum, steps + 1);
            }
        }
    }

    public String reformatNumber(String number) {
        int count = 0;
        String str = "";
        char[] c = number.toCharArray();
        List<Character> list = new ArrayList<>();

        for (char ch : c) {
            if (Character.isDigit(ch)) {
                list.add(ch);
            }
        }

        while (count < list.size()) {
            str += list.get(count);
            count++;
            if (count % 3 == 0 && count < list.size()) str += "-";
        }

//        System.out.println(str.charAt(str.length() - 2));
        if (str.charAt(str.length() - 2) == '-') {
            char[] s = str.toCharArray();
            s[str.length() - 2] = s[str.length() - 3];
            s[str.length() - 3] = '-';
            str = "";
            for (char c1 : s) {
                str += c1;
            }
        }

        return str;
    }

    public int maximumUniqueSubarray(int[] nums) {

        int max = Integer.MIN_VALUE;
        int sum = 0;
        int l = 0, r = 0;
        int[] count = new int[100001];


        while (r < nums.length) {
            sum += nums[r];
            count[nums[r]]++;

            if (count[nums[r]] > 1) {
                sum -= nums[l];
                count[nums[l]]--;
                l++;
            }

            max = Math.max(max, sum);
            r++;
        }

        return max;
    }

    // dp + monotonic queue
    public int maxResult(int[] nums, int k) {
//        [1,-1,-2,4,-7,3]
        int[] dp = new int[nums.length];
        dp[nums.length - 1] = nums[nums.length - 1];

        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(nums.length - 1);


        for (int i = nums.length - 2; i >= 0; i--) {
//            System.out.println("dp "  + Arrays.toString(dp));
//            System.out.println(dp[deque.peekFirst()]+ "\n");
            dp[i] = nums[i] + dp[deque.peekFirst()];

            while (!deque.isEmpty() && dp[i] > dp[deque.peekLast()]) {
                deque.pollLast();
            }

            // for the next loop
            while (!deque.isEmpty() && deque.peekFirst() - i >= k) {
                deque.pollFirst();
            }

            deque.addLast(i);
        }

        return dp[0];
    }

    // monotonic queue
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] max = new int[nums.length - k + 1];

        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < nums.length; i++) {

            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                deque.pollLast();
            }

            deque.offer(i);
            if (i - k + 1 >= 0) max[i - k + 1] = nums[deque.peekFirst()];

            while (!deque.isEmpty() && i - deque.peekFirst() + 1 >= k) {
                deque.pollFirst();
            }
        }

        return max;
    }

    public String decodeAtIndex(String S, int K) {
        long N = 0;
        int i = 0;

        while (N < K) {
            N = Character.isDigit(S.charAt(i)) ? N * (S.charAt(i) - '0') : N + 1;
            i++;
        }

        System.out.println(i);

        while (i-- > 0) {
            if (Character.isDigit(S.charAt(i))) {
                N /= (S.charAt(i) - '0');
                K %= N;
            } else if (K % N-- == 0) {
                return String.valueOf(S.charAt(i));
            }
        }

        return "";
    }

    public int minJumps(int[] arr) {

        Map<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            // map with same value, j
            map.computeIfAbsent(arr[i], v -> new ArrayList<>()).add(i);
        }

        List<Integer> cur = new ArrayList<>();
        cur.add(0);
        int count = 0;
        Set<Integer> visited = new HashSet<>();

        while (!cur.isEmpty()) {
            List<Integer> next = new ArrayList<>();

            for (int index : cur) {
                // bfs, first reach == minimum steps
                if (index == arr.length - 1) return count;

                for (int child : map.get(arr[index])) {
                    if (!visited.contains(child)) {
                        next.add(child);
                        visited.add(child);
                    }
                }

                map.get(arr[index]).clear();

                // i-1
                if (index - 1 >= 0 && !visited.contains(index - 1)) {
                    next.add(index - 1);
                    visited.add(index - 1);
                }

                // i+1
                if (index + 1 < arr.length && !visited.contains(index + 1)) {
                    next.add(index + 1);
                    visited.add(index + 1);
                }
            }

            cur = next;
            count++;
        }

        return -1;
    }

    int count;

    public int pseudoPalindromicPaths(TreeNode root) {
        boolean[] isOdd = new boolean[9];
        dfsHelper(root, isOdd, 0);
        return count;
    }

    private void dfsHelper(TreeNode root, boolean[] isOdd, int oddCount) {
//        if (root == null) {
//            if (oddCount <= 1) count++;
//            return;
//        }


        if (root != null) {
            isOdd[root.val - 1] = !isOdd[root.val - 1];
            oddCount = isOdd[root.val - 1] ? oddCount + 1 : oddCount - 1;

            if (root.left == null && root.right == null) {
                if (oddCount <= 1) count++;
            }

            dfsHelper(root.left, isOdd, oddCount);
            // backtrack
            dfsHelper(root.right, isOdd, oddCount);
            isOdd[root.val - 1] = !isOdd[root.val - 1];
        }
    }


    public int largestRectangleArea(int[] heights) {
        int i = 0;
        int len = heights.length;
        int max = 0;

        Stack<Integer> stack = new Stack<>();

        while (i < len) {
            while (!stack.isEmpty() && heights[i] < heights[stack.peek()]) {
                max = Math.max(max, heights[i] * (i - stack.pop() + 1));
                System.out.println(max + " " + i);
            }
            stack.push(i++);
        }

        return max;
    }

    public int maximumUnits(int[][] boxTypes, int truckSize) {
        Arrays.sort(boxTypes, (a, b) -> b[1] - a[1]);
        int max = 0;

        for (int i = 0; i < boxTypes.length; i++) {
            if (boxTypes[i][0] <= truckSize) {
                max += boxTypes[i][0] * boxTypes[i][1];
                truckSize -= boxTypes[i][0];
            } else {
                max += truckSize * boxTypes[i][1];
                truckSize = 0;
            }

            if (truckSize == 0) break;
        }

        return max;
    }

    public int countPairs(int[] deliciousness) {
        int count = 0;
        int even = 0;
        int odd = 0;
        int mod = 1000000007;

        for (int i = 0; i < deliciousness.length; i++) {
            if (isPowerOfTwo(deliciousness[i])) {
                even++;
            } else {
                odd++;
            }
        }

        return count;
    }

    private boolean isPowerOfTwo(int n) {
        if (n == 0)
            return false;

        return (int) (Math.ceil((Math.log(n) / Math.log(2)))) ==
                (int) (Math.floor(((Math.log(n) / Math.log(2)))));
    }

    public int waysToSplit(int[] nums) {
        int count = 0;
        int mod = 1000000007;

        int[] prefix = new int[nums.length];
        prefix[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            prefix[i] = nums[i] + prefix[i - 1];
        }

        int start = 1;
        int end = nums.length - 2;

        for (int i = start; i <= end; i++) {
            int left = binarySearch(prefix, i, true);
            int right = binarySearch(prefix, i, false);

            if (left == -1 || right == -1) continue;

            count += right - left + 1;
            count %= mod;

        }

        return count;
    }

    private int binarySearch(int[] prefix, int start, boolean searchLeft) {

        int lo = start;
        int hi = prefix.length - 2;
//        int res = -1;

        while (lo <= hi) {
            int mid = (hi - lo) / 2 + lo;
            int left = prefix[start - 1];
            int midSum = prefix[mid] - left;
            int right = prefix[prefix.length - 1] - prefix[mid];

            if (left <= midSum && midSum <= right) {
//                res = mid;
                if (searchLeft) {
                    hi = mid - 1;
                } else {
                    lo = mid + 1;
                }
            } else if (left > midSum) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

//        return res;

        if (searchLeft) {
            return lo >= start && lo <= prefix.length - 2 ? lo : -1;
        }
        return hi >= start && hi <= prefix.length - 2 ? hi : -1;
    }

    public int minEatingSpeed(int[] piles, int H) {
        int lo = 1;
        int hi = 1000000000;
        int res = 0;

        while (lo <= hi) {
            int mid = (hi - lo) / 2 + lo;
            if (finish(piles, mid, H)) {
                res = mid;
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }

        return res;
    }

    private boolean finish(int[] piles, int k, int h) {
        for (int n : piles) {
            if (k >= n) {
                h--;
            } else {
                h -= n / k + (n % k == 0 ? 0 : 1);
            }
        }
        return h >= 0;
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) return 0;
        int count = 2;

        Queue<String> queue = new LinkedList<>();
        boolean[] visited = new boolean[wordList.size()];

        for (int i = 0; i < wordList.size(); i++) {
            if (getDiff(wordList.get(i), beginWord) == 1) {
                queue.add(wordList.get(i));
                visited[i] = true;
            }
        }


        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {

                String target = queue.poll();

                if (target.equals(endWord)) return count;

                for (int j = 0; j < wordList.size(); j++) {
                    String next = wordList.get(j);
                    if (!visited[j] && getDiff(next, target) == 1) {
                        queue.add(wordList.get(j));
                        visited[j] = true;
                    }
                }
            }
            count++;
        }

        return 0;
    }

    private int getDiff(String s1, String s2) {
        int count = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i))
                count++;
        }
        return count;
    }

    public int[] decode(int[] encoded, int first) {
        int[] res = new int[encoded.length + 1];
        res[0] = first;

        for (int i = 0; i < encoded.length; i++) {
            res[i + 1] = res[i] ^ encoded[i];
        }
        return res;
    }

    public ListNode swapNodes(ListNode head, int k) {
        int size = 0;
        ListNode temp = head;

        while (temp != null) {
            size++;
            temp = temp.next;
        }

        if (size == 1) return head;

        int tail = size - k + 1;

        ListNode front = new ListNode(-1);
        ListNode dummy = front;
        front.next = head;
        front = front.next;
        ListNode pf = new ListNode();

        for (int i = 0; i < k; i++) {
            pf = front;
            front = front.next;
        }

        ListNode rear = new ListNode(-2);
        rear.next = head;
        rear = rear.next;
        ListNode rf = new ListNode();
        for (int i = 0; i < tail; i++) {
            rf = rear;
            rear = rear.next;
        }

        System.out.println(pf.val + " " + front.val + " " + rf.val + " " + rear.val);

//        if (front.next = rear)

        return dummy.next;
    }

    public int countGoodRectangles(int[][] rectangles) {
        int max = -1;
        Map<Integer, Integer> map = new HashMap<>();

        for (int[] a : rectangles) {
            int side = Math.min(a[1], a[0]);
            max = Math.max(max, side);
            map.put(side, map.getOrDefault(side, 0) + 1);
        }

        return map.get(max);
    }

    public int tupleSameProduct(int[] nums) {
        Arrays.sort(nums);

        Map<Integer, Set<Pair<Integer, Integer>>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                int product = nums[i] * nums[j];
                Set<Pair<Integer, Integer>> set = map.getOrDefault(product, new HashSet<>());
                set.add(new Pair<>(nums[i], nums[j]));

                map.put(product, set);
            }
        }


        for (Map.Entry<Integer, Set<Pair<Integer, Integer>>> e : map.entrySet()) {
            if (e.getValue().size() > 1)
                System.out.println(e);
        }

        int count = 0;

        for (Map.Entry<Integer, Set<Pair<Integer, Integer>>> e : map.entrySet()) {
            Set<Pair<Integer, Integer>> s = e.getValue();
            if (s.size() >= 2) {
                int s1 = s.size();
                count += choose(s.size(), 2) * 8;
            }

        }

        return count;
//        [2,3,4,6,12]

//        int lo = 0, hi = nums.length - 1, count = 0;

//        while (lo < hi) {
//            int product = nums[lo] * nums[hi];
//            int l = lo + 1, h = hi - 1;
//            int times = 1;
//            while (l < h) {
//                int inner = nums[l] * nums[h];
//                if (inner == product) {
//                    times++;
//                    l++;
//                    h--;
//                } else {
//                    // inner > product || inner < product
//                    break;
//                }
//            }
//            count += 8 * choose(times, 2);
//            hi--;
//        }

//        return -1;
    }

    private int choose(int n, int r) {
        if (n < r) return 0;
        if (n == r) return 1;
        return fact(n) / (fact(r) * fact(n - r));
    }

    private int fact(int n) {
        if (n == 1) return n;
        return n * fact(n - 1);
    }


    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0 || lists == null) return null;

        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        PriorityQueue<ListNode> pq = new PriorityQueue<>(lists.length, (a, b) -> a.val - b.val);

        for (ListNode l : lists) {
            if (l != null)
                pq.add(l);
        }

        while (!pq.isEmpty()) {
            cur.next = pq.poll();
            cur = cur.next;

            if (cur.next != null) {
                pq.add(cur.next);
            }
        }

        return dummy.next;
    }

    public int[][] diagonalSort(int[][] mat) {
        int row = mat.length;
        int col = row == 0 ? 0 : mat[0].length;

        Map<Integer, PriorityQueue<Integer>> map = new HashMap<>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map.getOrDefault(i - j, new PriorityQueue<>());
                map.get(i - j).add(mat[i][j]);
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                mat[i][j] = map.get(i - j).poll();
            }
        }

        return mat;
    }

    public boolean check1(int[] nums) {
        if (nums.length <= 1) return true;

        int lo = 1;

        while (lo < nums.length && nums[lo] >= nums[lo - 1]) {
            lo++;
        }

        if (lo == nums.length) return true;

        int hi = lo;

        while (hi + 1 < nums.length && nums[hi] <= nums[hi + 1]) {
            hi++;
        }

        return hi == nums.length - 1 && nums[hi] <= nums[0];
    }

    public int maximumScore(int a, int b, int c) {
        int[] arr = new int[]{a, b, c};
        Arrays.sort(arr);

        int res = 0;

        res += arr[0];

        int count = arr[0];

        int diff = arr[2] - arr[1];

        if (count <= diff) {
            return res + Math.min(arr[1], arr[2]);
        }

        count -= diff;


        return count % 2 != 0 ? res + arr[1] - (count + 1) / 2 : res + arr[1] - count / 2;
    }

    public String largestMerge(String word1, String word2) {
        List<Character> res = new ArrayList<>();
        int i = 0, j = 0;
        for (; i < word1.length() && j < word2.length(); ) {
            char a = word1.charAt(i);
            char b = word2.charAt(j);

            if (a > b) {
                res.add(a);
                i++;
            } else if (b > a) {
                res.add(b);
                j++;
            } else {
                int k = 0;
                for (; k < Math.min(word1.length(), word2.length()); k++) {
                    char c = word1.charAt(k);
                    char d = word2.charAt(k);
                    if (c == d) {
                        res.add(c);
                    } else {
                        if (c > d) {
                            res.add(c);
                            i += k;
                        } else {
                            res.add(d);
                            j += k;
                        }
                        break;
                    }
                }
            }
        }

        while (i < word1.length()) {
            res.add(word1.charAt(i++));
        }
        while (j < word2.length()) {
            res.add(word2.charAt(j++));
        }

        return res.toString();
    }

    public int shortestPathBinaryMatrix(int[][] grid) {
        if (grid[0][0] != 0) return -1;

        int n = grid.length;
        boolean[][] visited = new boolean[n][n];
        visited[0][0] = true;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{0, 0});
        int[] dir = new int[]{1, 0, -1, 0, 1, 1, -1, -1, 1};
        int level = 1;

        while (!q.isEmpty()) {
            int size = q.size();

            for (int i = 0; i < size; i++) {
                int[] cur = q.poll();
                int x = cur[0];
                int y = cur[1];
                if (x == y && x == n - 1) return level;

                for (int j = 0; j < 8; j++) {
                    int row = x + dir[j];
                    int col = y + dir[j + 1];

                    if (row >= 0 && col >= 0 &&
                            row < n && col < n &&
                            !visited[row][col] &&
                            grid[row][col] == 0) {
                        q.add(new int[]{row, col});
                        visited[row][col] = true;
                    }
                }
            }

            level++;
        }


        return -1;
    }

    public int[] kWeakestRows(int[][] mat, int k) {
        int[] res = new int[k];

        Map<Integer, Integer> map = new HashMap<>();
        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>((a, b) ->
                a.getValue() != b.getValue() ? a.getValue() - b.getValue() : a.getKey() - b.getKey());

        for (int i = 0; i < mat.length; i++) {
            int ones = 0;
            for (int j = 0; j < mat[i].length; j++) {
                if (mat[i][j] == 1) {
                    ones++;
                } else {
                    break;
                }
            }
            map.put(i, ones);
        }

        System.out.println(map);
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            pq.add(e);
        }
        System.out.println(pq);
        for (int i = 0; i < k; i++) {
            res[i] = pq.poll().getKey();
        }

        return res;
    }

    public int countHomogenous(String s) {
        int mod = 1000000000 + 7;
        long res = 0;

        char[] c = s.toCharArray();

        long lo = 0;
        long pre = -1;
        while (lo < c.length) {
            while (lo + 1 < c.length && c[(int) lo] == c[(int) lo + 1]) {
                lo++;
            }
            res += (lo - pre) * (lo - pre + 1) / 2 % mod;

            pre = lo;
            lo++;
        }
        return (int) res;
    }

    public String mergeAlternately(String word1, String word2) {
        int p1 = 0;
        int p2 = 0;
        String res = "";

        while (p1 < word1.length() && p2 < word2.length()) {
            res += word1.charAt(p1++);
            res += word2.charAt(p2++);
        }

        while (p1 < word1.length()) {
            res += word1.charAt(p1++);
        }

        while (p2 < word2.length()) {
            res += word2.charAt(p2++);
        }
        return res;
    }

    public int[] minOperations(String boxes) {
        char[] c = boxes.toCharArray();
//        Map<Integer, Integer> map = new HashMap<>(); // index, count;

        List<Integer> ones = new LinkedList<>();

        for (int i = 0; i < c.length; i++) {
            if (c[i] == '1') {
                ones.add(i);
            }
        }
        System.out.println(ones);

        int[] res = new int[c.length];

        for (int i = 0; i < c.length; i++) {
            int count = 0;
            for (int n : ones) {
                count += Math.abs(n - i);
            }
            res[i] = count;
        }
        return res;
    }

    // top-down with memo
    public int maximumScore(int[] nums, int[] multipliers) {
        int n = nums.length;
        int m = multipliers.length;
        Integer[][] dp = new Integer[n][m];

        helper(nums, multipliers, 0, 0, dp);

        return dp[0][0];
    }

    private int helper(int[] nums, int[] multipliers, int i, int m, Integer[][] dp) {
        if (m == multipliers.length) return 0;
        if (dp[i][m] != null) return dp[i][m];

        int left = nums[i] * multipliers[m] + helper(nums, multipliers, i + 1, m + 1, dp);
        int right = nums[nums.length - 1 - (m - i)] * multipliers[m] + helper(nums, multipliers, i, m + 1, dp);
        dp[i][m] = Math.max(left, right);
        return dp[i][m];
    }

    public int maxDiff(int[] p) {
        int diff = 0;
        int lowest = Integer.MAX_VALUE;

        for (int n : p) {
            lowest = Math.min(n, lowest);
            int cur = n - lowest;
            diff = Math.max(cur, diff);
        }

        return diff == 0 ? -1 : diff;
    }

    public boolean onePeak(int[] nums) {
        int lo = 0;
        int hi = nums.length - 1;

        while (lo + 1 < nums.length && nums[lo + 1] > nums[lo]) lo++;
        if (lo == nums.length - 1) return false;

        while (hi - 1 >= 0 && nums[hi - 1] > nums[hi]) hi--;
        if (hi == 0) return false;

        return hi == lo;
    }

    public int threeSumClosest(int[] nums, int target) {
        if (nums.length == 3) return nums[0] + nums[1] + nums[2];

        int res = nums[0] + nums[1] + nums[2];
        int len = nums.length;

//        Arrays.sort(nums);

        System.out.println(Arrays.toString(nums));

        for (int i = 0; i < len - 2; i++) {
            for (int j = i + 1; j < len - 1; j++) {
                for (int k = j + 1; k < len; k++) {
                    int sum = nums[i] + nums[j] + nums[k];
                    if (Math.abs(target - res) > Math.abs(target - sum))
                        res = sum;
                }
            }
        }


        return res;
    }

    int minSti = Integer.MAX_VALUE;

    public int minStickers(String[] stickers, String target) {
        backtracking(stickers, target, 0, new HashMap<Character, Integer>(), 0);
        return minSti == Integer.MAX_VALUE ? -1 : minSti;
    }

    private void backtracking(String[] stickers, String target, int index, HashMap<Character, Integer> availableChars, int count) {

        if (count >= minSti) return;
        if (index == target.length()) {
            minSti = Math.min(minSti, count);
            return;
        }

        char c = target.charAt(index);

        if (availableChars.containsKey(c) && availableChars.get(c) > 0) {
            availableChars.put(c, availableChars.get(c) - 1);
            backtracking(stickers, target, index + 1, availableChars, count);
            availableChars.put(c, availableChars.get(c) + 1);
        } else {

            for (int i = 0; i < stickers.length; i++) {
                if (stickers[i].indexOf(c) == -1) continue;

                for (char ch : stickers[i].toCharArray()) {
                    availableChars.put(ch, availableChars.getOrDefault(ch, 0) + 1);
                }

                backtracking(stickers, target, index, availableChars, count + 1);

                for (char ch : stickers[i].toCharArray()) {
                    availableChars.put(ch, availableChars.getOrDefault(ch, 0) - 1);
                }

                // backtracking(stickers, target, index, availableChars, count);
            }
        }
    }

    public int wiggleMaxLength(int[] nums) {
        if (nums.length < 2) return nums.length;

        int N = nums.length;
        int[] up = new int[N];
        int[] down = new int[N];

        for (int i = 1; i < N; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[i]) {
                    down[i] = Math.max(down[j], up[i] + 1);
                } else if (nums[j] < nums[i]) {
                    up[i] = Math.max(up[j], down[i] + 1);
                }
            }
        }

        return Math.max(up[N - 1], down[N - 1]) + 1;
    }

    //    public int[] advantageCount(int[] A, int[] B) {
//        int[] res = new int[A.length];
//
//
//        return res;
//    }
    public int[] findSiblings(int input1, int[] input2, int input3) {

        if (input2[0] == input3) {
            return new int[]{-1};
        }

        int pos = 0;

        for (int i = 0; i < input1; i++) {
            if (input2[i] == input3) {
                pos = i + 1;
            }
        }

        int start = Integer.highestOneBit(pos);
        int end = Math.min(start * 2 - 1, input1);

        int[] res = new int[end - start];
        int index = 0;
        for (int i = start - 1; i < end; i++) {
            if (input2[i] != input3) {
                res[index] = input2[i];
                index++;
            }
        }
        return res;
    }

    public List<String> wordSubsets(String[] A, String[] B) {
        List<String> res = new ArrayList<>();

        for (String s : A) {
            if (subsetCheck(s, B)) {
                res.add(s);
            }
        }

        return res;
    }

    private boolean subsetCheck(String s, String[] b) {
        int[] needed = new int[26];

        for (String str : b) {
            for (int i = 0; i < str.length(); i++) {
                needed[str.charAt(i) - 'a']++;
            }
        }
        System.out.println(Arrays.toString(needed));
        for (int i = 0; i < s.length(); i++) {
            needed[s.charAt(i) - 'a']--;
        }
        System.out.println(Arrays.toString(needed));
        System.out.println("-------");
        for (int n : needed) {
            if (n > 0) return false;
        }
        return true;
    }

    public int efficientJanitor(double[] arr) {
        int count = 0;
        Arrays.sort(arr);
        int lo = 0;
        for (int hi = arr.length - 1; hi >= 0; hi--) {
            if (arr[hi] < 2) {
                if (arr[hi] + arr[lo] <= 3) {
                    lo++;
                }
            }
            count++;
            if (lo >= hi) break;
        }
        return count;
    }

    public int numDifferentIntegers(String word) {
        Set<String> set = new HashSet<>();
        int last = 0;

        char[] c = word.toCharArray();
        String sub = "";
        for (int i = 0; i < c.length; i++) {
            if (Character.isDigit(c[i])) {
//                if (sub.length() <=0 && c[i] == '0') continue;
                sub += c[i];

            }

            if (i == c.length - 1 || !Character.isDigit(c[i])) {
                if (sub.length() > 0) {
                    set.add(sub);
                }

                sub = "";
            }
        }
        System.out.println(set);
        return set.size(); //a123bc34d8ef34
    }

    public int reinitializePermutation(int n) {
        int count = 0;
        int[] perm = new int[n];
        int[] target = new int[n];
        for (int i = 0; i < n; i++) {
            perm[i] = i;
            target[i] = i;
        }

        int[] arr = new int[n];

        while (true) {
            if (Arrays.equals(target, arr) && count != 0) {
                return count;
            }
            arr = new int[n];
            for (int i = 0; i < n; i++) {
                if (i % 2 == 0) {
                    arr[i] = perm[i / 2];
                } else {
                    arr[i] = perm[n / 2 + (i - 1) / 2];
                }
            }

            perm = arr;
            count++;

        }

    }

    public String evaluate(String s, List<List<String>> knowledge) {
        Map<String, String> map = new HashMap<>();
        for (List<String> list : knowledge) {
            map.put(list.get(0), list.get(1));
        }

        String res = "";
        boolean flag = false;
        int start = -1;
        int end = start;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                flag = true;
                start = i + 1;
            } else if (c == ')') {
                end = i;
                String sub = s.substring(start, end);
                res += map.getOrDefault(sub, "?");
                flag = false;
                continue;
            }

            if (!flag) res += c;

        }
        return res;
    }

    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];

        for (String s : strs) {
            int[] count = count(s);
            if (count[0] > m || count[1] > n) continue;

            int zeros = count[0];
            int ones = count[1];

            for (int i = m; i >= zeros; i--) {
                for (int j = n; j >= ones; j--) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - zeros][j - ones] + 1);
                }
            }

            for (int[] i : dp) {
                System.out.println(Arrays.toString(i));
            }
            System.out.println();

        }

        return dp[m][n];
    }

    private int[] count(String str) {
        int zeros = 0;
        int ones = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '1') {
                ones++;
            } else {
                zeros++;
            }
        }

        return new int[]{zeros, ones};
    }

    public String truncateSentence(String s, int k) {
        String[] str = s.split("\\s+");

        String res = "";
        for (int i = 0; i < k; i++) {
            res += str[i];
            if (i + 1 == k) break;
            res += " ";
        }

        return res;
    }


    public int[] findingUsersActiveMinutes(int[][] logs, int k) {
        int[] ans = new int[k];

        Map<Integer, Set<Integer>> map = new HashMap<>();


        for (int[] log : logs) {
            int id = log[0];
            int time = log[1];
            Set<Integer> set = map.getOrDefault(id, new HashSet<>());
            set.add(time);
            map.put(id, set);
        }

        for (Set<Integer> set : map.values()) {
            ans[set.size() - 1]++;
        }

        return ans;
    }

    public int minAbsoluteSumDiff(int[] nums1, int[] nums2) {
        int mod = 1000000007;
        TreeSet<Integer> treeSet = new TreeSet<>();
        int sum = 0;

        for (int i = 0; i < nums1.length; i++) {
            treeSet.add(nums1[i]);
            sum = (sum + Math.abs(nums1[i] - nums2[i])) % mod;
        }

        if (sum == 0) return sum;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < nums1.length; i++) {
            if (nums1[i] == nums2[i]) continue;

            Integer lower = treeSet.floor(nums2[i]);
            Integer higher = treeSet.ceiling(nums2[i]);
            lower = lower == null ? nums1[i] : lower;
            higher = higher == null ? nums1[i] : higher;

            int diff = Math.min(Math.abs(nums2[i] - lower), Math.abs(nums2[i] - higher));
            int temp = (sum - Math.abs(nums1[i] - nums2[i])) % mod;
            temp = (temp + diff) % mod;

            min = Math.min(min, temp);
        }

        return min;
    }

    int max = Integer.MIN_VALUE;
    List<Integer> res = new LinkedList<>();

    public int knapsack(int[] weight, int[] value, int capacity) {
        knapsackBacktrack(weight, value, 0, new LinkedList<>(), capacity, 0, 0);
        System.out.println(res);
        return max;
    }

    private void knapsackBacktrack(int[] weight, int[] value, int index, List<Integer> list,
                                   int capacity, int curValue, int curWeight) {
        if (index >= weight.length) {
            if (max < curValue) {
                max = curValue;
                res = new LinkedList<>(list);
            }
            return;
        }

        if (curWeight + weight[index] <= capacity) {
            curValue += value[index];
            curWeight += weight[index];
            list.add(index);
            knapsackBacktrack(weight, value, index + 1, list, capacity, curValue, curWeight);
            curValue -= value[index];
            curWeight -= weight[index];
            list.remove(list.size() - 1);
        }

        knapsackBacktrack(weight, value, index + 1, list, capacity, curValue, curWeight);
    }

    public int knapsackDP(int[] weight, int[] value, int capacity) {
        int[] dp = new int[capacity + 1];

        for (int i = 0; i < weight.length; i++) {
            for (int j = capacity; j >= weight[i]; j--) {
//            for (int j = weight[i]; j <= capacity; j++) {
                dp[j] = Math.max(dp[j], dp[j - weight[i]] + value[i]);
            }
        }
        return dp[capacity];
    }

    public String frequencySort(String s) {
        StringBuilder sb = new StringBuilder();
        // char, freq.
        Map<Character, Integer> map = new HashMap<>();
        PriorityQueue<Character> priorityQueue = new PriorityQueue<>((a, b) -> map.get(b) - map.get(a));

        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }

        priorityQueue.addAll(map.keySet());

        while (!priorityQueue.isEmpty()) {
            char ch = priorityQueue.poll();
            for (int i = 0; i < map.get(ch); i++) {
                sb.append(ch);
            }
        }

        return sb.toString();
    }

    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        List<List<Integer>> res = new LinkedList<>();
        if (matrix.length == 0) return res;

        int row = matrix.length;
        int col = matrix[0].length;
        int[] dir = {0, 1, 0, -1, 0};
        boolean[][] pacific = new boolean[row][col];
        boolean[][] atlantic = new boolean[row][col];

        for (int i = 0; i < row; i++) {
            dfs(matrix, i, 0, pacific, dir);
            dfs(matrix, i, col - 1, atlantic, dir);
        }

        for (int i = 0; i < col; i++) {
            dfs(matrix, 0, i, pacific, dir);
            dfs(matrix, row - 1, i, atlantic, dir);
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (pacific[i][j] && atlantic[i][j]) {
                    res.add(Arrays.asList(i, j));
                }
            }
        }

        return res;
    }

    private void dfs(int[][] matrix, int r, int c, boolean[][] reachable, int[] dir) {
        reachable[r][c] = true;

        for (int i = 0; i < 4; i++) {
            int row = r + dir[i];
            int col = c + dir[i + 1];

            if (row < 0 || col < 0 || row >= matrix.length || col >= matrix[0].length)
                continue;

            if (reachable[row][col])
                continue;

            if (matrix[r][c] > matrix[row][col])
                continue;

            dfs(matrix, row, col, reachable, dir);
        }
    }


    public int[][] meanGroups(int[][] a) {


        return new int[][]{};
    }

    public int findTheWinner(int n, int k) {
        List<Integer> list = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            list.add(i);
        }

        int index = 0;
        while (list.size() > 1) {
            index = (index + k - 1) % list.size();
            list.remove(index);
            System.out.println(list);
        }

        return list.get(0);
    }

    public int minSideJumps(int[] obstacles) {
        //lane, index
        int[][] ob = new int[3][obstacles.length];
        int[] dp = new int[obstacles.length];


        for (int i = 1; i < obstacles.length - 1; i++) {
            int pos = obstacles[i] - 1;
            ob[pos][i] = -1;
        }
        for (int i = 0; i < 3; i++) {
            System.out.println(Arrays.toString(ob[i]));
        }
        System.out.println();

        for (int i = obstacles.length - 1; i > 1; i--) {
            int pos = obstacles[i - 1] - 1;
//            if (pos < 0) continue;
            int a = (pos + 1) % 3;
            int b = (pos + 2) % 3;

            ob[pos][i] = Math.min(ob[a][i], ob[b][i]) + 1;
        }

        for (int i = 0; i < 3; i++) {
            System.out.println(Arrays.toString(ob[i]));
        }
        return dp[0];
    }


    public int[] getOrder(int[][] tasks) {
//        Arrays.sort(tasks, Comparator.comparingInt(a -> a[0]));
//        [[1,2],[2,4],[3,2],[4,1]]
        int[][] sortedTasks = Arrays.stream(tasks).map(int[]::clone).toArray(int[][]::new);
        Arrays.sort(sortedTasks, Comparator.comparingInt(a -> a[0]));

        PriorityQueue<Integer> sortedT = new PriorityQueue<>((a, b) -> tasks[a][0] != tasks[b][0] ? tasks[a][0] - tasks[b][0] : a - b);
        for (int i = 0; i < tasks.length; i++) {
            sortedT.add(i);
        }

        PriorityQueue<Integer> t = new PriorityQueue<>((a, b) -> tasks[a][1] != tasks[b][1] ? tasks[a][1] - tasks[b][1] : a - b);
        int n = 0;
        int[] res = new int[tasks.length];
        t.add(sortedT.peek());
        int time = tasks[sortedT.poll()][0];


        while (n < tasks.length) {
            while (!sortedT.isEmpty() && time >= tasks[sortedT.peek()][0]) {
                t.add(sortedT.poll());

            }
            int index = t.poll();
            res[n++] = index;
            time += tasks[index][1];

            if (t.isEmpty() && !sortedT.isEmpty()) {
                t.add(sortedT.poll());
            }
        }
        return res;
    }

    public int getXORSum(int[] arr1, int[] arr2) {
        int a = arr1.length;
        int b = arr2.length;
        int[] sum = new int[a * b];
        int index = 0;

        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                sum[index++] = arr1[i] & arr2[j];
            }
        }

        int res = 0;

        for (int n : sum) {
            res ^= n;
        }
        return res;
    }

    public boolean checkSubarraySum(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>() {{
            put(0, -1);
        }};
        ;
        int runningSum = 0;
        for (int i = 0; i < nums.length; i++) {
            runningSum += nums[i];
            if (k != 0) runningSum %= k;
            Integer prev = map.get(runningSum);
            if (prev != null) {
                if (i - prev > 1) return true;//这是为了确保子数组的长度至少为2
            } else map.put(runningSum, i);
        }
        return false;
    }

    public Map<String, Object> cmdParse(String cmd) {
        Map<String, Object> res = new HashMap<>();
        String[] strs = cmd.split(" ");

        res.put("name", strs[0]);

        int index = 0;
        Map<String, String> opt = new HashMap<>();
        for (int i = 1; i < strs.length; i++) {
            if (strs[i].substring(0, 2).equals("--")) {
                String temp = strs[i].substring(2, strs[i].length());
                opt.put(temp, "true");
            } else if (strs[i].substring(0, 1).equals("-")) {
                String temp = strs[i].substring(1, strs[i].length());
                opt.put(temp, "true");
            } else {
                index = i;
                break;
            }
            if (i == strs.length - 1) index = i;
        }
        res.put("option", opt);

        List<String> para = new ArrayList<>();
        for (int i = index + 1; i < strs.length; i++) {
            para.add(strs[i]);
        }

        res.put("parameters", para);

        return res;

    }


    public boolean splitString(String s) {

        while (s.charAt(0) == '0') {
            s = s.substring(1);
        }

        boolean res = dfsCheck(0l, s);
        return res;
    }

    private boolean dfsCheck(Long val, String s) {
        // 100 99 98
        if (s.length() == 0) return false;
        if (val - 1 == Long.parseLong(s)) {
            return true;
        }

        long n = val - 1;
        String temp = s;


        if (s.indexOf(String.valueOf(n)) == 0) {
            s = s.substring(String.valueOf(n).length());
            return dfsCheck(n, s);
        } else {
            while (s.length() > 1 && s.charAt(0) == '0') {
                s = s.substring(1);
            }
            if (s.indexOf(String.valueOf(n)) == 0) {

                s = s.substring(String.valueOf(n).length());
                return dfsCheck(n, s);
            } else {
                val = 10 * val + Long.parseLong(temp.substring(0, 1));
                temp = temp.substring(1);
                if (val == 10) {
                    System.out.println(1069);
                }
                return dfsCheck(val, temp);
            }
        }
    }

    public String lengthOfLongestSubstring(String s) {
        int[] index = new int[128];
        int max = 0;
        int lo = 0;
        int hi = 0;
        int start = -1;

        while (hi < s.length()) {
            lo = Math.max(index[s.charAt(hi)], lo);
//            max = Math.max(res, hi - lo + 1);
            if (max < hi - lo + 1) {
                max = hi - lo + 1;
                start = lo;
            }
            index[s.charAt(hi)] = hi + 1;
            hi++;
        }
        return s.substring(start, start + max);
    }

    public void solve(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        boolean[][] checked = new boolean[row][col];

        for (int r = 0; r < row; r++) {
            checked[r][0] = true;
            if (board[r][0] == 'O') {
                dfsModify(board, checked, r, 0);
            }
        }
        for (int c = 0; c < col; c++) {
            checked[0][c] = true;
            if (board[0][c] == 'O') {
                dfsModify(board, checked, 0, c);
            }
        }

        for (int r = 1; r < row; r++) {
            for (int c = 1; c < col; c++) {
                if (board[r][c] == 'O') {
                    dfsModify(board, checked, r, c);
                }
            }
        }

    }

    private void dfsModify(char[][] board, boolean[][] checked, int r, int c) {
//        if ()
    }

    public int numIslands(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int count = 0;

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (grid[r][c] == '1') {
                    count++;
                    dfsIslands(grid, r, c);
                }
            }
        }

        return count;
    }

    private void dfsIslands(char[][] grid, int r, int c) {
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length || grid[r][c] == '0')
            return;
        grid[r][c] = '0';
        dfsIslands(grid, r + 1, c);
        dfsIslands(grid, r - 1, c);
        dfsIslands(grid, r, c + 1);
        dfsIslands(grid, r, c - 1);
    }

    public int maxAreaOfIsland(int[][] grid) {
        int area = 0;
        int row = grid.length;
        int col = grid[0].length;
        boolean[][] seen = new boolean[row][col];

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                int cur = dfsArea(grid, seen, r, c);
                area = Math.max(area, cur);
            }
        }

        return area;
    }

    private int dfsArea(int[][] grid, boolean[][] seen, int r, int c) {
        if (r < 0 || c < 0 || r >= grid.length || c >= grid[0].length || seen[r][c] || grid[r][c] == 0) {
            return 0;
        }
        seen[r][c] = true;
        return 1 + dfsArea(grid, seen, r - 1, c) + dfsArea(grid, seen, r, c - 1)
                + dfsArea(grid, seen, r + 1, c) + dfsArea(grid, seen, r, c + 1);
    }


    public int maxArea(int h, int w, int[] horizontalCuts, int[] verticalCuts) {
        Arrays.sort(horizontalCuts);
        Arrays.sort(verticalCuts);

        int maxH = Math.max(horizontalCuts[0], h - horizontalCuts[horizontalCuts.length - 1]);
        int maxW = Math.max(verticalCuts[0], w - verticalCuts[verticalCuts.length - 1]);

        for (int i = 1; i < verticalCuts.length; i++) {
            maxW = Math.max(maxW, verticalCuts[i] - verticalCuts[i - 1]);
        }

        for (int i = 1; i < horizontalCuts.length; i++) {
            maxH = Math.max(maxH, horizontalCuts[i] - horizontalCuts[i - 1]);
        }


        return (int) (1l * maxW * maxH % 1000000007);
    }


    public boolean[] meetingRoomIII(int[][] intervals, int rooms, int[][] ask) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        int[] count = new int[50000];

        for (int[] i : intervals) {
            int start = i[0];
            int end = i[1];

            while (start < end) {
                count[start]++;
                start++;
            }
        }

        boolean[] res = new boolean[ask.length];

        for (int i = 0; i < ask.length; i++) {
            int start = ask[i][0];
            int end = ask[i][1];

            boolean temp = true;

            while (start < end) {
                if (count[start] >= rooms) {
                    temp = false;
                    break;
                }
                start++;
            }
            res[i] = temp;
        }
        return res;
    }

    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        //(n2 + n) / 2

        int count = 0;
        int lo = 0;
        int hi = 0;


        while (hi < nums.length) {
            while (lo < nums.length && (nums[lo] < left || nums[lo] > right)) {
                lo++;
                hi = lo;
            }

            while (hi < nums.length && nums[hi] <= right && nums[hi] >= left) {
                hi++;
            }

            int n = hi - lo + 1;
            count += (n * n + n) / 2 - 1;
            hi++;
            lo = hi;
        }

        return count;
    }

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode pre = null;
        ListNode cur = head;

        while (cur != null) {
            ListNode temp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = temp;
        }
        return pre;
    }

    public int findPaths(int m, int n, int k, int r, int c) {
        int[][] dp = new int[m][n];
        dp[r][c] = 1;
        int count = 0;
        int mod = 1000000000 + 7;

        for (int i = 0; i < k; i++) {
            int[][] temp = new int[m][n];
            for (int a = 0; a < m; a++) {
                for (int b = 0; b < n; b++) {
                    if (a == 0) count = (count + dp[a][b]) % mod;
                    if (b == 0) count = (count + dp[a][b]) % mod;
                    if (a == m - 1) count = (count + dp[a][b]) % mod;
                    if (b == n - 1) count = (count + dp[a][b]) % mod;

                    temp[a][b] = ((a > 0 ? dp[a - 1][b] : 0) +
                            (b > 0 ? dp[a][b - 1] : 0)) % mod +
                            ((a == m - 1 ? 0 : dp[a + 1][b]) +
                                    (b == n - 1 ? 0 : dp[a][b + 1])) % mod;

                }
            }
            dp = temp;
        }

        return count;
    }

    public double knightProbability(int n, int k, int row, int column) {
        double[][] dp = new double[n][n];
        dp[row][column] = 1;
        int[] dx = {2, 2, 1, 1, -1, -1, -2, -2};
        int[] dy = {1, -1, 2, -2, 2, -2, 1, -1};
        double res = 0;

        for (; k > 0; k--) {
/*            for (double[] d : dp) {
                System.out.println(Arrays.toString(d));
            }
            System.out.println("----------");*/
            double[][] temp = new double[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dp[i][j] == 0) continue;
                    for (int l = 0; l < 8; l++) {
                        int x = i + dx[l];
                        int y = j + dy[l];
                        if (x >= 0 && y >= 0 && x < n && y < n) {
                            temp[x][y] += dp[i][j] / 8;

                        }
                    }
                }
            }
            dp = temp;
        }

        for (double[] d : dp) {
//            System.out.println(Arrays.toString(d));
            for (double i : d) {
                res += i;
            }
        }
        return res;
    }

    public String removeDuplicates(String s) {
        Stack<Character> st = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            if (!st.isEmpty() && st.peek() == s.charAt(i)) {
                st.pop();
                continue;
            } else {
                st.push(s.charAt(i));
            }
        }

        StringBuilder sb = new StringBuilder();

        while (!st.isEmpty()) {
            sb.insert(0, st.pop());
        }

        return new String(sb);
    }


    public List<int[]> getPairs(List<int[]> a, List<int[]> b, int target) {
        if (a == null || b == null) return new ArrayList<>();
        Collections.sort(a, (m, n) -> m[1] - n[1]);
        Collections.sort(b, (m, n) -> n[1] - m[1]);
        int temp = Integer.MIN_VALUE;
        int m = a.size();
        int n = b.size();
        int i = 0;
        int j = 0;
        List<int[]> res = new ArrayList<>();

        while (i < m && j < n) {
            int sum = a.get(i)[1] + b.get(j)[1];
            if (sum > target) {
                j++;
                continue;
            }

            if (temp < sum) {
                temp = sum;
                res.clear();
            }
            res.add(new int[]{a.get(i)[0], b.get(j)[0]});
            int nj = j + 1;
            while (nj < n && b.get(j)[1] == b.get(nj)[1]) {
                res.add(new int[]{a.get(i)[0], b.get(nj)[0]});
            }

            i++;
        }
        return res;
    }

    public boolean hasValidPath(int[][] g) {
        int m = g.length;
        int n = g[0].length;
        boolean[][] vis = new boolean[m][n];
        int[] direction = {0, 1, 0, -1, 0};
        char[] ch = {'R', 'D', 'L', 'U'};

        Map<Integer, Set<Character>> map = new HashMap<>();
        map.put(1, new HashSet<>(Arrays.asList('L', 'R')));
        map.put(2, new HashSet<>(Arrays.asList('U', 'D')));
        map.put(3, new HashSet<>(Arrays.asList('R', 'U')));
        map.put(4, new HashSet<>(Arrays.asList('L', 'U')));
        map.put(5, new HashSet<>(Arrays.asList('D', 'R')));
        map.put(6, new HashSet<>(Arrays.asList('D', 'L')));

        return dfs(g, m, n, 0, 0, map, '!', vis, direction, ch);
    }

    private boolean dfs(int[][] g, int m, int n, int r, int c,
                        Map<Integer, Set<Character>> map, char dir, boolean[][] vis, int[] direction, char[] ch) {
        if (r < 0 || c < 0 || r == m || c == n || vis[r][c] ||
                dir != '!' && !map.get(g[r][c]).contains(dir)) return false;
        if (r == m - 1 && c == n - 1) return true;

        vis[r][c] = true;

        for (int i = 0; i < 4; i++) {
            int nr = r + direction[i];
            int nc = c + direction[i + 1];

            if (dfs(g, m, n, nr, nc, map, ch[i], vis, direction, ch)) return true;
        }

        return false;
    }

    //leetcode 286 - plus
    public void wallsAndGates(int[][] rooms) {
        Queue<int[]> q = new LinkedList<>();
        int m = rooms.length;
        int n = rooms[0].length;
        int[] dir = {0, 1, 0, -1, 0};

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) {
                    q.offer(new int[]{i, j});
                }
            }
        }
        int steps = 0;
        while (!q.isEmpty()) {
            steps++;
            int size = q.size();
            for (int k = 0; k < size; k++) {
                int[] cur = q.poll();
                int r = cur[0];
                int c = cur[1];
                for (int i = 0; i < 4; i++) {
                    int x = r + dir[i];
                    int y = c + dir[i + 1];
                    if (x < 0 || y < 0 || x >= m || y >= n) continue;
                    if (rooms[x][y] == Integer.MAX_VALUE) {
                        rooms[x][y] = steps;
                        q.offer(new int[]{x, y});
                    }
                }
            }
        }
    }

    public int strStr(String s, String t) {
        // Write your code here
        if (t.length() == 0) return 0;
        if (s.length() == 0) return -1;

        for (int i = 0; i <= s.length() - t.length(); i++) {
            int p1 = i;
            int p2 = 0;

            while (p2 < t.length() && s.charAt(p1) == t.charAt(p2)) {
                p1++;
                p2++;
            }
            if (p2 == t.length()) return i;
        }

        return -1;
    }

    private boolean check(String s, String t) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != t.charAt(i)) count++;
        }
        return count == 1;
    }

    public List<String> TopkKeywords(int K, String[] keywords, String[] reviews) {
        Map<String, Integer> map = new HashMap<>();
        Set<String> set;
        for (String s : keywords) {
            map.put(s, 0);
        }

        for (String s : reviews) {
            String[] strs = s.replaceAll("[^a-zA-Z ]", " ")
                                .toLowerCase()
                                .trim()
                                .split("\\s+");
            set = new HashSet<>();
            for (String str : strs) {
                if (map.containsKey(str) && set.add(str)) {
                    map.put(str, map.get(str) + 1);
                }
            }
        }

        PriorityQueue<String> pq = new PriorityQueue<>((a, b)
                -> map.get(a) == map.get(b) ?
                a.compareTo(b) : map.get(b) - map.get(a));

        pq.addAll(map.keySet());
        List<String> res = new LinkedList<>();

        while (!pq.isEmpty() && K-- > 0) {
            if (map.get(pq.peek()) > 0)
                res.add(pq.poll());
        }

        return res;
    }
    public static void main(String[] args) {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        n1.next = n2;
        n2.next = n3;
        System.out.println(n1);
        test(n1);
        System.out.println(n1);
        n1 = null;
        System.out.println(n1);
    }

    private static void test(ListNode n1) {
        n1 = null;
        System.out.println(" ----" + n1);
    }


    List<List<String>> ans = new LinkedList<>();
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        if (!wordList.contains(endWord)) {
            return new LinkedList<>();
        }

        int n = wordList.size();
        boolean[] vis = new boolean[n];
        Queue<Integer> q = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            if (check(beginWord, wordList.get(i))) {
                q.offer(i);
                vis[i] = true;
            }
        }

        if (q.isEmpty()) {
            return new LinkedList<>();
        }

        List<String> cur = new LinkedList<>();
        cur.add(beginWord);


        backtrack(endWord, wordList, cur, q, vis);

        return ans;
    }

    private void backtrack(String target, List<String> list, List<String> cur, Queue<Integer> q, boolean[] vis) {
        if (ans.size() > 0 && ans.get(0).size() < cur.size()) return;

        int size = q.size();
        for (int i = 0; i < size; i++) {
            String s = list.get(q.poll());
            cur.add(s);
            if (s.equals(target)) {
                if (ans.size() == 0) {
                    ans.add(new LinkedList<>(cur));
                } else if (ans.get(0).size() > cur.size()) {
                    ans.clear();
                    ans.add(new LinkedList<>(cur));
                } else if (ans.get(0).size() == cur.size()) {
                    ans.add(new LinkedList<>(cur));
                }
            }

            Queue<Integer> nq = new LinkedList<>();
            for (int j = 0; j < list.size(); j++) {
                if (!vis[j] && check(s, list.get(j))) {
                    vis[j] = true;
                    nq.offer(j);
                    backtrack(target, list, cur, nq, vis);
                    vis[j] = false;
                }
            }

//            backtrack(target, list, cur, nq, vis);

            cur.remove(cur.size()-1);
        }
    }
}

class LRUCache extends LinkedHashMap<Integer, Integer> {
    private int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75F, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}
