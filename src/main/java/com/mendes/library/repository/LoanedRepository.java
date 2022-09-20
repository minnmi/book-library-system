package com.mendes.library.repository;

import com.mendes.library.model.Loaned;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanedRepository extends JpaRepository<Loaned, Long> {
}
