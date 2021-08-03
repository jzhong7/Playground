package com.company.handwritten;

import java.util.Arrays;
import java.util.Stack;

public class MyStack {

    private Object[] elements;
    private int capacity = 16;
    private int size;

    public MyStack() {
        this.elements = new Object[capacity];
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Object push(Object o) {
        if (size() >= elements.length) {
            resize();
        }
        elements[size] = o;
        size++;
        return o;
    }

    public Object peek() {
        if (size() == 0) {
            throw new RuntimeException("stack is empty");
        }
        return elements[size-1];
    }

    public Object pop() {
        if (isEmpty()) {
            return null;
        }

        Object o = peek();
        elements[size-1] = null;
        size--;
        return o;
    }

    private void resize() {
        int oldCapacity = elements.length;
        int newCapacity = 2 * oldCapacity;
        Object[] old = elements;

        elements = new Object[newCapacity];
        for (int i = 0; i < oldCapacity; i++) {
            elements[i] = old[i];
        }

        old = null;
    }

    @Override
    public String toString() {
        return "MyStack{" +
                "elements=" + Arrays.toString(elements) +
                ", capacity=" + elements.length +
                ", size=" + size +
                '}';
    }
}

class StackTest {
    public static void main(String[] args) {
        MyStack myStack = new MyStack();

        System.out.println(myStack.isEmpty());

        myStack.push(1);
        System.out.println(myStack.peek().equals(1));

        myStack.push("hello");
        System.out.println(myStack.size() == 2);

        System.out.println(myStack);
        myStack.push("world");
        System.out.println(myStack);
        System.out.println(myStack.size() == 3 && myStack.peek().equals("world"));
        myStack.push("world");
        myStack.push("world");
        System.out.println(myStack);
//
//        myStack.pop();
//        myStack.pop();
//        System.out.println(myStack.peek().equals(1));
//        myStack.pop();
//        System.out.println(myStack.isEmpty());
//        System.out.println(myStack.peek());

    }
}