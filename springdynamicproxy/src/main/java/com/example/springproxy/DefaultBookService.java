package springdynamicproxy.src.main.java.com.example.springproxy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultBookService implements BookService {

    @Autowired BookRepository bookRepository;

    @Override
    public void rent(Book book) {
        System.out.println("Rent the Book : "+book.getTitle());
    }

    public Book getBook(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    public void save(Book book) {
        bookRepository.save(book);
    }
}
