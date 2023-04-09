package org.example;

public class Book {
    private String name;

    private static String B;

    private static final String C = "Book";

    public String d;

    protected String e;

    public Book() {}

    public Book(String name, String d, String e) {
        this.name = name;
        this.d = d;
        this.e = e;
    }

    private void f() {
        System.out.println("F");
    }

    public void g() {
        System.out.println("g");
    }

    public int h() {
        return 100;
    }
}
