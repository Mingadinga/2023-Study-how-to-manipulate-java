package springdynamicproxy.src.main.java.com.example.springproxy;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceProxy implements BookService{

    @Autowired
    @Qualifier("defaultBookService")
    private BookService target;

    @Override
    public void rent(Book book) {
        System.out.println("loging!!!");
        target.rent(book);
        System.out.println("loging!!!");
    }

    @Override
    public void save(Book book) {
        target.save(book);
    }

    // rent로 넘어온 Book의 Title을 hibernate으로 바꾸는 조작
    public void rentHibernate(Book book) {
        System.out.println("loging!!!");
        System.out.println("hibernate"); // 타깃 위임 안하고 여기서 처리
        System.out.println("loging!!!");
    }
}
