package org.example;

import java.lang.annotation.*;

// 애노테이션은 주석과 마찬가지이므로 정보가 클래스에는 남지만
// 바이트코드를 로딩하고 나서 메모리는 남지 않음
// 같이 읽어오고 싶다면 Retention 옵션으로 Runtime을 주어야한다
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
// 상속이 가능한 애노테이션. 이 애노테이션을 사용한 클래스를 상속받는 클래스에서 리플렉션 조회로 이 애노테이션 조회 가능
@Inherited
public @interface MyAnnotation {
    String name() default "min";
    int number() default 100;
}
