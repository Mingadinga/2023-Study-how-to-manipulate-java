package springdynamicproxy.src.main.java.com.example.springproxy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Note {
    @Id
    @GeneratedValue
    private Integer id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

}