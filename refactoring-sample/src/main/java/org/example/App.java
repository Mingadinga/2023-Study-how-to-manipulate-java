package org.example;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ClassNotFoundException, NoSuchFieldException {
        // Class를 통해 Book, MyBook, MyInterface 등에 접근가능
        // 클래스 로딩이 끝나면 클래스 타입의 인스턴스(Class)를 만들어서 힙에 넣어준다.

        // 타입으로 가져오기
        Class<Book> bookClass = Book.class;

        // 인스턴스로 가져오기
        Book book = new Book();
        Class<? extends Book> bookClass1 = new Book().getClass();

        // fqcn으로 가져오기
        Class<?> aClass = Class.forName("org.example.Book");

        // 가져온 클래스 인스턴스로 정보 참조하기
        Field[] fields = bookClass.getFields(); // public만 리턴
        Arrays.stream(fields).forEach(System.out::println);

        Field[] declaredFields = bookClass.getDeclaredFields(); // 모든 필드 리턴
        Arrays.stream(declaredFields).forEach(System.out::println);

        Field name = bookClass.getDeclaredField("d");// 특정 필드 리턴(private 포함)
        System.out.println(name);

        // 값을 가져오기 - 인스턴스 필요함

        Arrays.stream(bookClass.getDeclaredFields()).forEach(f -> {
            try{
                f.setAccessible(true); // private도 접근 가능하도록 변경 (헐)
                System.out.printf("%s %s\n", f, f.get(book));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

        // 메소드
        Arrays.stream(bookClass.getMethods()).forEach(System.out::println);
        // 상속받은 객체의 메소드까지 다 나온다(다만 private은 안나옴)
        /*
            public int org.example.Book.h()
            public void org.example.Book.g()
            public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException
            public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException
            public final void java.lang.Object.wait() throws java.lang.InterruptedException
            public boolean java.lang.Object.equals(java.lang.Object)
            public java.lang.String java.lang.Object.toString()
            public native int java.lang.Object.hashCode()
            public final native java.lang.Class java.lang.Object.getClass()
            public final native void java.lang.Object.notify()
            public final native void java.lang.Object.notifyAll()
         */

        Arrays.stream(bookClass.getDeclaredMethods()).forEach(System.out::println);


        // 생성자
        Arrays.stream(bookClass.getConstructors()).forEach(System.out::println);
        /*
            public org.example.Book()
            public org.example.Book(java.lang.String,java.lang.String,java.lang.String)
         */

        // 상위 클래스
        System.out.println(MyBook.class.getSuperclass());
        /*
            class org.example.Book
         */

        // 인터페이스
        Arrays.stream(MyBook.class.getInterfaces()).forEach(System.out::println);
        /*
            interface org.example.MyInterface
         */

        // 필드의 접근자나 키워드 확인
        Arrays.stream(bookClass.getDeclaredFields()).forEach(f -> {
            int modifiers = f.getModifiers();
            System.out.println(f);
            System.out.println(Modifier.isPrivate(modifiers));
            System.out.println(Modifier.isStatic(modifiers));
            System.out.println(f.getType());
        });

        // 메소드 정보 확인
        Arrays.stream(bookClass.getDeclaredMethods()).forEach(e -> {
            System.out.println(e);

            int modifiers = e.getModifiers();
            System.out.println(Modifier.isPrivate(modifiers));
            System.out.println(Modifier.isStatic(modifiers));

            System.out.println(e.getReturnType());
            System.out.println(e.getParameterTypes());
        });

    }
}
