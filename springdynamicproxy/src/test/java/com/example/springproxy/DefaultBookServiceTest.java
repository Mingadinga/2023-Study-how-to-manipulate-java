package springdynamicproxy.src.test.java.com.example.springproxy;

import jakarta.transaction.Transactional;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Transactional
@SpringBootTest
class DefaultBookServiceTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private NoteRepository noteRepository;

    @Qualifier("defaultBookService")
    @Autowired
    BookService bookService;

    @Test
    public void jdkProxy() {

        BookService bookService = (BookService) Proxy.newProxyInstance(
                BookService.class.getClassLoader(), // 사용할 클래스 로더 -> BookService 타입의 프록시 생성
                new Class[]{BookService.class}, // 구현할 인터페이스 목록
                new InvocationHandler() { // 부가기능
                    BookService bookService = new DefaultBookService();
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 부가기능 적용
                        // 유연하지 않다. 부가기능이 많아지면 코드가 계속 커진다.
                        // 스프링 aop가 이 구조를 스프링이 정의한 구조로 만들었다.
                        if (method.getName().equals("rent")) {
                            System.out.println("aaaaaaaaaaa");
                            Object invoke = method.invoke(bookService, args);
                            System.out.println("bbbbbbbbbbb");
                            return invoke;
                        }
                        // 부가기능 미적용
                        return method.invoke(bookService, args);
                    }
                });

        Book book = new Book();
        book.setTitle("spring");
        bookService.rent(book);
    }

    @Test
    public void cglibProxy() {

        MethodInterceptor handler = new MethodInterceptor() {
            // 타깃
            DefaultBookService defaultBookService = new DefaultBookService();
            // 핸들러
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                if (method.getName().startsWith("rent")) {
                    System.out.println("aa");
                    Object invoke = method.invoke(defaultBookService, args);
                    System.out.println("bb");
                    return invoke;
                }
                return method.invoke(defaultBookService, args);
            }
        };

        // 클래스 타입 프록시 생성
        DefaultBookService bookService = (DefaultBookService) Enhancer.create(DefaultBookService.class, handler);


        Book book = new Book();
        book.setTitle("spring");
        bookService.rent(book);
    }

    @Test
    public void byteBuddyProxy() throws Exception {

        Class<? extends DefaultBookService> proxyClass = new ByteBuddy().subclass(DefaultBookService.class)
                .method(named("rent")).intercept(InvocationHandlerAdapter.of(new InvocationHandler() {
                    // 타깃
                    DefaultBookService bookService = new DefaultBookService();

                    // 핸들러
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("aa");
                        Object invoke = method.invoke(bookService, args);
                        System.out.println("bb");
                        return invoke;
                    }
                }))
                .make().load(DefaultBookService.class.getClassLoader()).getLoaded();

        // 클래스 타입 프록시 생성
        DefaultBookService bookService = (DefaultBookService) proxyClass.getConstructor(null).newInstance();

        Book book = new Book();
        book.setTitle("spring");
        bookService.rent(book);
    }

    @Test
    public void mockRepositoryTest() {
        BookRepository mockRepo = mock(BookRepository.class);
        Book book = new Book();
        book.setTitle("Test Book");
        when(mockRepo.findById(any())).thenReturn(Optional.of(book));

        bookService.save(book);
        bookService.rent(book);
    }

    @Test
    public void testLazyInitialization() {
        // 새로운 Book 엔티티 생성
        Book book = new Book();
        book.setTitle("Test Book");
        bookRepository.save(book);

        // Note 엔티티 생성 및 Book에 추가
        Note note1 = new Note();
        note1.setContent("Note 1");
        note1.setBook(book);
        noteRepository.save(note1);

        Note note2 = new Note();
        note2.setContent("Note 2");
        note2.setBook(book);
        noteRepository.save(note2);

        // Book 엔티티 조회 (lazy initialization)
        Book savedBook = bookRepository.findById(book.getId()).orElse(null);

        // Book 엔티티의 Notes 컬렉션에 접근 (lazy initialization 발생)
        List<Note> notes = savedBook.getNotes();

        // Notes 컬렉션의 크기 확인
        assertEquals(2, notes.size());
    }



}