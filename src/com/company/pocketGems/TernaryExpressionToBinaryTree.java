package com.company.pocketGems;
/*

Input: "a?b?c:d:e"
        Output:
         a
        / \
       b   e
      / \
     c   d

*/

import java.util.*;

class Node {
    char val;
    Node left;
    Node right;

    public Node() {
    }

    public Node(char c) {
        this.val = c;
    }

    public Node(char c, Node left, Node right) {
        this.val = c;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "Node{" +
                "c=" + val +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}

public class TernaryExpressionToBinaryTree {

    public Node toBST(String s) {
        if (s == null || s.length() == 0) return new Node();
        char[] chars = s.toCharArray();
        Node root = new Node(chars[0]);
        Stack<Node> stack = new Stack<>();
        stack.push(root);

        for (int i = 1; i < chars.length; i += 2) {
            Node next = new Node(chars[i+1]); // i is '?' or ':', i+1 is node

            if (chars[i] == '?') {
                stack.peek().left = next;
            } else {
                // ':'
                stack.pop(); // pop the sibling
                while (!stack.isEmpty() && stack.peek().right != null) {
                    stack.pop();
                }
                stack.peek().right = next;
            }
            stack.push(next);

        }


        return root;
    }

    private static void test(String s) {
        TernaryExpressionToBinaryTree t = new TernaryExpressionToBinaryTree();
        List<Character> res = new ArrayList<>();
        preorder(t.toBST(s), res);
        System.out.println(res);
//        levelOrder(t.toBST(s));
    }

    private static void preorder(Node root, List<Character> res) {
        if (root == null) {
//            res.add('-');
            return;
        }
        res.add(root.val);
        preorder(root.left, res);
        preorder(root.right, res);
    }

    private static void levelOrder(Node root) {
        if (root == null) {
            System.out.println("EMPTY NODE");
        }
        List<Character> res = new ArrayList<>();
        Queue<Node> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Node cur = q.poll();
                res.add(cur.val);
                if (cur.left != null) {
                    q.add(cur.left);
                }
                if (cur.right != null) {
                    q.add(cur.right);
                }
            }
        }

        System.out.println(res);
    }

    public static void main(String[] args) {
        test("a?b:c");
        test("a?b?c:d:e");
        test("a?b?c?d:e:f:g");
        test("a?b:c?d:e");
        test("a?b:c?d:e?f:e");
        test("a?b?c?d:e:f?g:h:i?j:k");
    }

}
