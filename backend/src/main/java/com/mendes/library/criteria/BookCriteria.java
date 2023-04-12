package com.mendes.library.criteria;

import lombok.Data;

@Data
public class BookCriteria {
    private Long id;
    private String title;
    private String isbn;
    private String author;
}
