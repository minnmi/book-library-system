package com.mendes.library.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "book",
uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "isbn"})})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String isbn;

    private Integer quantity;

    private Boolean returned;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "book_authorship",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;

    @JoinColumn(name = "publisher_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Publisher publisher;

    @JoinColumn(name = "literature_category_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private LiteratureCategory literatureCategory;
}
