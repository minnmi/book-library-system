package com.mendes.library.repository;

import com.mendes.library.criteria.BookCriteria;
import com.mendes.library.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRepositoryCustom {
    Page<Book> search(Pageable pageable, BookCriteria criteria);
}
