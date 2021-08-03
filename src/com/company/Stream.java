package com.company;




import javax.xml.crypto.dsig.Reference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

//it is an interface, but only has one abstract method; ex: runnable, comparator, callable
@FunctionalInterface    //optional
interface MyFunctionalInterface {
    void print(String s);

    default void getSum(int a, int b) {
        System.out.println("Sum is "  + (a+b));
    }

    static void getDiff(int a, int b) {
        System.out.println("Diff. is " + (a-b));
    }

    //Can override the method from java.lang.Object class,
    //even these methods are abstract,
    //but the implementation of functional interface inherited from object in default,
    //containing the implementation of these abstract method.
    @Override
    boolean equals(Object o1);
}

class Peron {
    String name;
    int age;
    String sex;

    public Peron(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}

public class Stream {
    public static List<String> search(List<Peron> list) {
        //new Person(name, age, sex)
        //write the stream logic hear
        List<String> res = new ArrayList<>();
        list.stream()
                .filter(peron -> peron.name.startsWith("a"))
                .filter(peron ->  peron.name.length() == 3)
                .forEach(peron -> res.add(peron.name));
        return res;
    }

    public static String getString(List<Integer> list) {
        //Write the stream logic hear
        return list.stream().map(integer -> integer % 2 == 0 ? "e" + integer : "o" + integer).collect(Collectors.joining(","));
    }

    public static Double average(List<Integer> list) {
        //write the stream logic hear
        return list.stream().mapToDouble(val -> val).average().orElse(0.0);
    }

    public static List<String> upperCase(List<String> list) {
        //write the stream logic hear
        return list.stream().map(s -> s.toUpperCase()).collect(Collectors.toList());
    }



    public static void main(String[] args) {
        Peron p1 = new Peron("ac1", 1, "M");
        Peron p2 = new Peron("ac2", 1, "M");
        Peron p3 = new Peron("ab3", 1, "M");
        Peron p4 = new Peron("ab", 1, "M");
        Peron p5 = new Peron("abca", 1, "M");
        List<Peron> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        list.add(p5);

        List<Integer> nums = Arrays.asList(1,2,3,4,5,6,7,8);

//        System.out.println(search(list));
//        System.out.println(getString(nums));

        List<String> l = Arrays.asList("abd", "aaa","aa","aaaa");
//        List<String> nl = l.stream().filter(s -> s.startsWith("a") && s.length() == 3).collect(Collectors.toList());
//        List<String> res = new ArrayList<>();
//
//        nl.forEach(s -> res.add(s));
//        System.out.println(res);
//        System.out.println(upperCase(l));

        // lambda expression used to implement the abstract method of the functional interface
        MyFunctionalInterface f = s -> {
            System.out.println(s + " world!");
        };

        f.print("Hello");
        f.getSum(20, 25);

        //static method
        MyFunctionalInterface.getDiff(100, 50);
        System.out.println(f.equals(f));

        // Function<T, R> apply()
        Function<Integer, Integer> f1 = a -> a * 2;
        // twice of a
        System.out.println(f1.apply(3));


        //BiFunction<T, T, R> apply()
        BiFunction<Integer, Integer, Integer> f2 = (a, b) -> a * b;
        //product of a and b
        System.out.println(f2.apply(2, 4));

        //Supplier<T> get();
        Supplier<Integer> f3 = () -> (int) (Math.random() * 100);
        //get a random num [0,100]
        System.out.println(f3.get());

        //Consumer<T> accept()
        Consumer<String> f4 = s -> {
            System.out.printf("Hello, %s!", s);
        };
        f4.accept("Java");

//        Reference
    }
}
