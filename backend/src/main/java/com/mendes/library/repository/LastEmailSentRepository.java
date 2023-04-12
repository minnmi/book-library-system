package com.mendes.library.repository;

import com.mendes.library.model.LastEmailSent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastEmailSentRepository extends JpaRepository<LastEmailSent, Long> {
}
