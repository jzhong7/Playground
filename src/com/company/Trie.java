package com.company;

import jdk.nashorn.internal.runtime.linker.LinkerCallSite;

import java.util.*;

class TrieNode {
    char c;
    TrieNode parent;
    HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
    boolean isEnd;

    public TrieNode() {
    }

    public TrieNode(char c) {
        this.c = c;
    }
}

public class Trie {
    private final TrieNode root;
    ArrayList<String> words;
    TrieNode prefixRoot;
    String curPrefix;

    public Trie() {
        root = new TrieNode();
        words = new ArrayList<String>();
    }

    public Trie(List<String> list) {
        root = new TrieNode();
        words = new ArrayList<String>();
        for (String s : list) {
            insert(s);
        }
    }

    public void insert(String word) {
        HashMap<Character, TrieNode> children = root.children;
        TrieNode crntparent;
        crntparent = root;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            TrieNode t;
            if (children.containsKey(c)) {
                t = children.get(c);
            } else {
                t = new TrieNode(c);
                t.parent = crntparent;
                children.put(c, t);
            }

            children = t.children;
            crntparent = t;

            if (i == word.length() - 1) {
                t.isEnd = true;
            }
        }
    }

    public boolean search(String word) {
        TrieNode t = searchNode(word);
        return t != null && t.isEnd;
    }

    public boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }


    public TrieNode searchNode(String cardNum) {
        Map<Character, TrieNode> children = root.children;
        TrieNode t = null;

        for (int i = 0; i < cardNum.length(); i++) {
            char c = cardNum.charAt(i);
            if (children.containsKey(c)) {
                t = children.get(c);
                children = t.children;
            } else {
                return null;
            }
        }

        prefixRoot = t;
        curPrefix = cardNum;
//        words.clear();
        return t;
    }

    void wordsFinderTraversal(TrieNode node) {
        if (node == null) return;

        if (node.isEnd) {

            TrieNode altair;
            altair = node;

            Stack<String> hstack = new Stack<String>();

            while (altair != prefixRoot) {
                hstack.push(Character.toString(altair.c));
                altair = altair.parent;
            }
            StringBuilder wrd = new StringBuilder(curPrefix);
            while (!hstack.empty()) {
                wrd.append(hstack.pop());
            }
            words.add(wrd.toString());
        }
    }

    public List<String> findPrefix(String cardNum) {
        words.clear();
        List<String> res = words;
        StringBuilder cur = new StringBuilder();
        for (int i = 0; i < cardNum.length(); i++) {
            cur.append(cardNum.charAt(i));
            TrieNode tn = this.searchNode(cur.toString());
            this.wordsFinderTraversal(tn);
        }
        return res;
    }

    public static void main(String[] args) {
        // lower        upper , bin
        Map<String, List<String>> map = new HashMap<>();
        map.put("11", Arrays.asList("15", "a"));
        map.put("33", Arrays.asList("35", "b"));
        map.put("112", Arrays.asList("119", "c"));
        map.put("1121", Arrays.asList("1129", "d"));
        map.put("11211", Arrays.asList("11211", "f"));
        map.put("11212", Arrays.asList("11218", "g"));


        // lower
        List<String> list = Arrays.asList("11", "33", "112", "1121", "11211", "11212");
        List<String> lxxx = Arrays.asList("15", "35", "119", "1129", "11211", "11218");
//                                          a,
        Trie prefixTree = new Trie(list);

        List<String> res = prefixTree.findPrefix("12712xxx");

        System.out.println(res);
        for (String s : res) {
            System.out.println(map.get(s).get(1));
        }

//        System.out.println(prefixTree.findPrefix("113xxxxx"));

    }
}