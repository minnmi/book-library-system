package com.mendes.library.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String isbn;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "book_authorship",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @JoinColumn(name = "publisher_id", nullable = false)
    @ManyToOne
    private Publisher publisher;

    @JoinColumn(name = "literature_category_id", nullable = false)
    @ManyToOne
    private LiteratureCategory literatureCategory;
}
