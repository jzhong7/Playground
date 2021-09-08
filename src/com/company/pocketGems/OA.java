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
                    && (idx == 0 || Math.abs(a[i] - b[idx-1]) > d)) {
                System.out.println(a[i]);
                count++;
            }
        }
        return count;
    }


    public static void main(String[] args) {
        OA o = new OA();
        System.out.println(o.reductor(new int[] {-100,13,20}, new int[] {1,4,13}, 3));
//        System.out.println(Arrays.binarySearch(new int[] {1,2,5}, 0));
    }
}

