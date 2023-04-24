package com.mendes.library.repository;

import com.mendes.library.criteria.BookCriteria;
import com.mendes.library.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Book> search(Pageable pageable, BookCriteria criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root <Book> root = cq.from(Book.class);

        List<Predicate> predicates = new ArrayList();

        if (Objects.nonNull(criteria.getId())) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }

        if (Objects.nonNull(criteria.getTitle())) {
            predicates.add(cb.like(root.get("name"), "%" + criteria.getTitle() + "%"));
        }

        if (Objects.nonNull(criteria.getIsbn())) {
            predicates.add(cb.like(root.get("isbn"), criteria.getIsbn() + "%"));
        }

        cq.where(predicates.toArray(new Predicate[]{}));

        var result = em.createQuery(cq).getResultList();
        return new PageImpl<>(result, pageable, result.size());
    }
}
