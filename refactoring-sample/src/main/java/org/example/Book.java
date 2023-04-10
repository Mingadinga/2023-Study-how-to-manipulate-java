package org.example;

import java.lang.annotation.Target;

public class Book {

    public static String A = "A";

    private String B = "B";
    private String name;

    private void c() {
        System.out.println("C");
    }

    public Book() {}
    public Book(String name) {this.name = name;}

    public int sum(int left, int right) {
        return left + right;
    }

}
