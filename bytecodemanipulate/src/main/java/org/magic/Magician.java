package org.magic;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;

import java.io.File;
import java.io.IOException;

import static net.bytebuddy.matcher.ElementMatchers.named;

public class Magician {

    public static void main(String[] args) {

        // 조작 작업
        try {
            new ByteBuddy().redefine(Moja.class)
                            .method(named("pullOut")).intercept(FixedValue.value("Rabbit!"))
                            .make().saveIn(new File(
                                    "/Users/minhwi/IdeaProjects/2023-Study-how-to-manipulate-java/bytecodemanipulate/build/classes/java/main/"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 조작과 실행 작업을 동시에 실행하면 바이트 조작이 적용되지 않는다
        // 클래스로더에 의해 new Moja()가 먼저 실행되는데, 이때 Moja의 바이트코드는 조작되지 않은 상태기 때문
        // 바이트 코드를 우선 조작한 후에 생성해야한다.
//        System.out.println(new Moja().pullOut());
    }

}
