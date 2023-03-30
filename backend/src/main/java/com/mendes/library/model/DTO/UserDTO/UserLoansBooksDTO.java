package com.mendes.library.model.DTO.UserDTO;

import com.mendes.library.model.Book;
import com.mendes.library.model.DTO.BookDTO.BookDTO;
import com.mendes.library.model.DTO.LoanedDTO.LoanDTO;
import com.mendes.library.model.Loan;
import com.mendes.library.model.User;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLoansBooksDTO {
    public UserLoansBooksDTO(Long userId, String username, Long bookId, String bookTitle, LocalDateTime initialDate,
                             LocalDateTime finalDate, Integer returned, LocalDateTime returnedDate) {
        this.user = new UserDTO(userId, username);
        this.book = new BookDTO(bookId, bookTitle);
        this.loan = new LoanDTO(initialDate, finalDate, returned, returnedDate);
    }

    private UserDTO user;
    private BookDTO book;
    private LoanDTO loan;
}
