package com.company.handwritten;

import java.util.Arrays;

class MyEntry {
    Object key;
    Object value;
    MyEntry next;

    public MyEntry(Object key, Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "MyEntry{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}

public class MyHashMap {
    private final MyEntry[] table;
    private static final int SIZE = 16;

    public MyHashMap() {
        this.table = new MyEntry[SIZE];
    }

    public void put(Object key, Object value) {
        int index = key.hashCode() % SIZE;
        MyEntry cur = table[index];

        if (cur == null) {
            table[index] = new MyEntry(key, value);
        } else {
            while (cur != null) {
                if (cur.key.equals(key)) {
                    cur.value = value;
                    return;
                }
                /* add to the end of the list
                if (cur.next == null) {
                    cur.next = new MyEntry(key, value, null);
                    return;
                }*/
                cur = cur.next;
            }

            // add to the head
            MyEntry newEntry = new MyEntry(key, value);
            newEntry.next = table[index];
            table[index] = newEntry;

        }
    }

    public MyEntry get(Object key) {
        int index = key.hashCode() % SIZE;
        if (table[index] == null) return null;
        MyEntry cur = table[index];
        while (cur != null) {
            if (cur.key.equals(key)) return cur;
            cur = cur.next;
        }
        return null;
    }

    @Override
    public String toString() {
        return "MyHashMap{" +
                "table=" + Arrays.toString(table) +
                '}';
    }
}

class TestHashMap {
    public static void main(String[] args) {
        MyHashMap map = new MyHashMap();

        map.put(3,5);
        System.out.println(map.get(3));
        System.out.println(map);

        map.put(3,6);
        System.out.println(map.get(3));
        System.out.println(map);

        map.put(19,7);
        System.out.println(map.get(19));
        System.out.println(map);

        map.put(3,6);
        System.out.println(map.get(3));
        System.out.println(map);

        map.put("a","b");
        System.out.println(map.get("a"));
        System.out.println(map);

        System.out.println(map.get(1));
    }
}