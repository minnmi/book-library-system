package com.mendes.library.model.DTO.LoanedDTO;


import com.mendes.library.model.Book;
import com.mendes.library.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanedDTO {

    private Long id;
    private LocalDateTime initialDate;
    private LocalDateTime finalDate;
    private User user;
    private Book book;
    private Integer returned;
    private LocalDateTime returnedDate;

}
