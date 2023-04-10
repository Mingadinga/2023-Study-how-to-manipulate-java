package org.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ManipulateApp {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchFieldException {

        // 생성자 가져와서 인스턴스 생성하기
        Class<?> bookClass = Class.forName("org.example.Book");

        // 기본 생성자
        Constructor<?> constructor = bookClass.getConstructor(null);
        Book book1 = (Book)constructor.newInstance();

        // 문자열 하나를 받는 생성자
        Constructor<?> stringConstructor = bookClass.getConstructor(String.class);
        Book book2 = (Book)stringConstructor.newInstance("별 헤는 밤");


        // static 필드 조회, 수정
        // Book의 static A 필드 값
        Field a = Book.class.getDeclaredField("A");
        System.out.println(a.get(null)); // static 필드라 인스턴스가 null
        a.set(null, "AAAAAA");
        System.out.println(a.get(null));


        // book1의 private B 필드 조회, 수정
        Field b = Book.class.getDeclaredField("B");
        b.setAccessible(true);
        System.out.println(b.get(book1));
        b.set(book1, "BBBBBB");
        System.out.println(b.get(book1));

        // 메소드 호출
        Method c = Book.class.getDeclaredMethod("c");
        c.setAccessible(true);
        c.invoke(book1); // 인스턴스, c에 필요한 매개변수들

        Method sum = Book.class.getDeclaredMethod("sum", int.class, int.class);
        int invoke = (int)sum.invoke(book1, 1, 2);
        System.out.println(invoke);


    }
}
