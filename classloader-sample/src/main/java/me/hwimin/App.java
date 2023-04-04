package me.hwimin;

/**
 * Hello world!
 *
 */
public class App
{

    // initializer
    static String name;

    static {

    }

    // book의 레퍼런스는 링크 과정에서 심볼릭 메모리 레퍼런스이다.
    // 즉 실제 레퍼런스가 아니라 논리적엔 레퍼런스를 가리킨다.
    // Resolve 과정에서 실제 힙에 들어있는 Book 인스턴스의 레퍼런스를 가리키도록 한다.
    // 단 이 작업은 optional이다.
    Book book = new Book();

    public static void main( String[] args )
    {
        // static 하게 얻기
        ClassLoader classLoader = App.class.getClassLoader();
        System.out.println(classLoader);

        // 객체로 얻기
        App app = new App();
        System.out.println(app.getClass().getClassLoader());

        // 계층형 구조를 가지는 클래스 로더
        System.out.println(classLoader); // $AppClassLoader@3b192d32
        System.out.println(classLoader.getParent()); // $PlatformClassLoader@56cbfb61
        System.out.println(classLoader.getParent().getParent()); // null - 부트스트랩 클래스로더. 네이티브 코드로 구현되므로 vm마다 달라서 자바 코드로 참조할 수 없다
    }
}
