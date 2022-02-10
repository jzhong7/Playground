package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class Lottery {
    static int[] arr = {1, 2, 3, 4, 5, 10, 11, 15, 17, 19, 20, 21, 22, 23, 31, 32, 33, 34, 39, 42, 46, 48, 49, 53,
            55, 56, 62, 64, 70, 72, 75, 76, 78, 79, 84, 85, 94, 96, 98, 101, 102, 104, 105, 114, 121, 124, 128,
            136, 139, 141, 142, 146, 148, 152, 155, 160, 169, 198, 200, 206, 207, 208, 215, 221, 226, 234, 236,
            238, 239, 240, 279, 283, 287, 297, 300, 309, 312, 322, 337, 338, 347, 394, 406, 416, 437, 438, 448,
            461, 494, 538, 543, 560, 572, 581, 617, 647, 771};
    static Set<Integer> done = new HashSet<>(Arrays.asList(312, 771, 647, 617, 20, 309, 121, 49, 85, 287, 438, 139,
            98, 239, 4, 208, 34));

    public static int getWinner() {
        int rd = new Random().nextInt(97);
        while (done.contains(arr[rd])) {
            rd = new Random().nextInt(97);
        }
        return arr[rd];
    }

    public static void main(String[] args) {
        System.out.println(getWinner());
    }

}

