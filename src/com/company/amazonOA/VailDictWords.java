package com.company.amazonOA;

import java.util.*;

class TrieNode {
    public TrieNode[] children;
    public boolean isEnd;
    public int val;

    public TrieNode() {
        this.children = new TrieNode[256];
        this.isEnd = false;
        this.val = 0;
    }
}

class Trie {
    private TrieNode root;
    public List<String> words;

    public Trie(String[] words) {
        this.root = new TrieNode();
        for (String word : words) {
            insert(word);
        }
    }

    public Trie(Map<String, Integer> map) {
        this.root = new TrieNode();
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            String key = e.getKey();
            Integer value = e.getValue();
            insert(key, value);
        }
    }

    public void insert(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (cur.children[c] == null) {
                cur.children[c] = new TrieNode();
            }
            cur = cur.children[c];
        }
        cur.isEnd = true;
    }
    public void insert(String word, int val) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (cur.children[c] == null) {
                cur.children[c] = new TrieNode();
            }
            cur = cur.children[c];
            cur.val += val;
        }
        cur.isEnd = true;
    }

    public boolean search(String word) {
        TrieNode cur = root;
        for (char c : word.toCharArray()) {
            if (cur.children[c] == null) {
                return false;
            }
            cur = cur.children[c];
        }
        return cur.isEnd;
    }

    public int getSumWithPrefix(String prefix) {
        TrieNode cur = root;
        for (char c : prefix.toCharArray()) {
            if (cur.children[c] == null) {
                return 0;
            }
            cur = cur.children[c];
        }
        return cur.val;
    }

    public List<String> getWordWithPrefix(String prefix) {
        words.clear();
        List<String> res = words;
        TrieNode cur = root;
        for (char c : prefix.toCharArray()) {
            if (cur.children[c] == null) {
                return res;
            }
            cur = cur.children[c];
        }

        StringBuilder sb = new StringBuilder(prefix);
        getWords(sb, cur, res);

        return res;
    }

    private void getWords(StringBuilder sb, TrieNode cur, List<String> res) {
        if (cur == null) return;
        if (cur.isEnd) {
            res.add(sb.toString());
            for (TrieNode t : cur.children) {
//                sb +=
                getWords(sb, t, res);
            }
        }
    }
}

public class VailDictWords {
    String[] dict = {"a","and","also", "qnbpt", "qnbut", "qnbps", "roast", "smart", "snast", "zebra"};

    public String[] loadDict() {
        return dict;
    }

    public List<String> searchWordsWithNum(String nums) {
        List<String> res = new LinkedList<>();
        if (nums == null || nums.length() == 0) {
            return res;
        }
        String[] dict = loadDict();
        Trie trie = new Trie(dict);

        String[] mapping = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        backtrack(res, trie, mapping, nums, new StringBuilder());

        return res;
    }

    private void backtrack(List<String> res, Trie trie, String[] mapping, String nums, StringBuilder sb) {
        if (nums.length() == 0) {
            if (trie.search(sb.toString())) {
                res.add(sb.toString());
            }
            return;
        }

        String cur = mapping[nums.charAt(0) - '0'];
        for (char c : cur.toCharArray()) {
            sb.append(c);
            backtrack(res, trie, mapping, nums.substring(1), sb);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(new VailDictWords().searchWordsWithNum("76278"));
    }
}

class AdwordAdPrefix {
    /*
        Adwords:
        Apple: 70
        Appre: 20
        Appro: 5
        Leni: 20
        Length: 5

        Adprefix:
        App: 75
        Len: 75
        Return value: App, Leni, Length
* */
    public static void main(String[] args) {
        Map<String, Integer> adword = new HashMap<>();
        adword.put("Apple", 70);
        adword.put("Appre", 20);
        adword.put("Appro", 5);
        adword.put("Leni", 20);
        adword.put("Length", 5);
        Map<String, Integer> adprefix = new HashMap<>();
        adprefix.put("App", 75);
        adprefix.put("Len", 75);
//
//        Trie trie = new Trie(adword);
//        List<String> prefix = new LinkedList<>();
//        for (Map.Entry<String, Integer> e : adprefix.entrySet()) {
//            String key = e.getKey();
//            int val = e.getValue();
//            if (trie.getSumWithPrefix(key) >= val) {
//                prefix.add(key);
//            }
//        }


    }
}
