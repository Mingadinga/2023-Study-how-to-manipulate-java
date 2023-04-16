package springdynamicproxy.src.main.java.com.example.springproxy;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Entity
public class Book {
    @Id @Column(name = "book_id")
    @GeneratedValue
    Integer id;
    String title;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    List<Note> notes;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
