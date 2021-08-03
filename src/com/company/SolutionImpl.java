package com.company;
import java.util.*;

public class SolutionImpl {
    public static List<Integer> solve(int capacity, List<String> ar) {
        LRUCache lruCache = new LRUCache(capacity);
        List<Integer> res = new ArrayList<>();
        System.out.println(ar);
        for (int i = 0; i < ar.size(); i++) {
            if (ar.get(i).equals("GET")) {
                i++;
                int temp = lruCache.get(Integer.parseInt(ar.get(i)));
                res.add(temp);
            } else {
                lruCache.put(Integer.parseInt(ar.get(i+1)), Integer.parseInt(ar.get(i+2)));
                i += 2;
            }
        }

        return res;
    }
    public static String reverseString(String s) {
        int lo = 0;
        int hi = s.length() - 1;

        char[] c = s.toCharArray();
        while (lo < hi) {
            char temp = c[lo];
            c[lo] = c[hi];
            c[hi] = temp;
            lo++;
            hi--;
        }
        return new String(c);
    }

    public static void print2dArray(int[][] arr) {
        for (int[] row : arr) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static boolean isPalindrome(String str) {
        int lo = 0;
        int hi = str.length() - 1;

        while (lo < hi) {
            if (str.charAt(lo) != str.charAt(hi)) {
                return false;
            }
            lo++;
            hi--;
        }

        return true;
    }


    public static void main(String[] args) {


//        Scanner sc = new Scanner(System.in);
//        String input = sc.nextLine();
//        System.out.println(reverseString(input));



        Solution solution = new Solution();

        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
//        l4.next = l2;


//        Map<String, List<String>> map = new HashMap<>();
//        List<List<String>> r = new ArrayList<>();
//        r.addAll(map.values());

        double[] arr = {1.01, 1.991, 1.4, 1.32};
        int[] n1 = {2147483647, -1 , 0, 2147483647};
        int[] n2 = {2147483647, 2147483647, 2147483647, -1};
        int[] n3 = {2147483647, -1, 2147483647, -1};
        int[] n4 = {0, -1, 2147483647, 2147483647};
        int[][] n = new int[4][4];
        n[0] = n1;
        n[1] = n2;
        n[2]= n3;
        n[3] = n4;
        List<String> list = Arrays.asList("hot","dot","dog","lot","log","cog");
        System.out.println(solution.findLadders("hit", "cog", list));
//        int[][] a = {{1,2},{4,5}, {8,10}};
        int[][] ask = {{4,5}, {5,6}};
        List<int[]> a = Arrays.asList(new int[] {1,3}, new int[] {2,5}, new int[] {3,7}, new int[] {4,10});
        List<int[]> b = Arrays.asList(new int[] {1,2}, new int[] {2,3}, new int[] {3,4}, new int[] {4,5});
//
//        for (int[] n : solution.getPairs(a, b, 10)) {
//            System.out.println(Arrays.toString(n));
//        }

        /*        a = [[1, 3], [2, 5], [3, 7], [4, 10]]
        b = [[1, 2], [2, 3], [3, 4], [4, 5]]
        target = 10*/
//
//        Set<Integer> set = new HashSet<>();
//        set.add(1);
//        set.add(1);
//        set.add(0);
//        List<Integer> l = new ArrayList<>(set);
//        String s = "ls     -a";
//
//        System.out.println(solution.maxArea(5, 4, new int[] {1,2,4}, new int[] {1,3}));

//        String[] a = {"amazon","apple","facebook","google","leetcode"};
//        String[] b = {"ec","oc","ceo"};
//        System.out.println(solution.wordSubsets(a, b));
//        List<String> list = Arrays.asList("GET", "2", "PUT","1","100",
//                "PUT", "2", "125","PUT", "3", "150", "GET", "1", "GET", "3");
//
//
//        int[][] matrix = {
//                {1,4,7,11,15},
//                {2,5,8,12,19},
//                {3,6,9,16,22},
//                {10,13,14,17,24},
//                {18,21,23,26,3}
//        };
//
//        int[][] mat = {{-1,-2,-3},
//                {-2,-3,-3},
//                {-3,-3,-2}};
//        int[][] pairs = {{2,1}, {3,0}};


        TreeNode one = new TreeNode(1);
        TreeNode two = new TreeNode(2);
        TreeNode three = new TreeNode(3);
        TreeNode four = new TreeNode(4);

        two.left = three;
        two.right = one;
        three.left = new TreeNode(3);
        three.right = new TreeNode(1);
        one.right = new TreeNode(1);




        int[][] pt = {{0,0,0},{1,1,0},{1,1,0}};


        int[][] m = {{2,1,3,0,-3,3,-4,4,0,-4},
                {-4,-3,2,2,3,-3,1,-1,1,-2},
                {-2,0,-4,2,4,-3,-4,-1,3,4},
                {-1,0,1,0,-3,3,-2,-3,1,0},
                {0,-1,-2,0,-3,-4,0,3,-2,-2},
                {-4,-2,0,-1,0,-3,0,4,0,-3},
                {-3,-4,2,1,0,-4,2,-4,-1,-3},
                {3,-2,0,-4,1,0,1,-3,-1,-1},
                {3,-4,0,2,0,-2,2,-4,-2,4},
                {0,4,0,-3,-4,3,3,-1,-2,-2}};




    }
}
