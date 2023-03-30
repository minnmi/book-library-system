package com.mendes.library.repository;

import com.mendes.library.model.DTO.UserDTO.UserLoansBooksDTO;
import com.mendes.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional(readOnly = true)
    Optional<User> findByEmail(String email);

    @Transactional(readOnly = true)
    Optional<User> findByUsername(String username);

    @Query("select new com.mendes.library.model.DTO.UserDTO.UserLoansBooksDTO(u.id, u.name, b.id, b.name, l.id, l.initialDate, l.finalDate, l.returned, l.returnedDate)  " +
            "from User u " +
            "inner join Loan l " +
            "on u.id = l.user.id " +
            "inner join Book b on b.id = l.book.id " +
            "where u.id = :userId")
    List<UserLoansBooksDTO> findLoansByUserId(Long userId);

}
