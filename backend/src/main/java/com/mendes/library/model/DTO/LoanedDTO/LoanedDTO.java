package com.mendes.library.model.DTO.LoanedDTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.mendes.library.model.Book;
import com.mendes.library.model.DTO.BookDTO.BookDTO;
import com.mendes.library.model.DTO.UserDTO.UserDTO;
import com.mendes.library.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanedDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    private LocalDateTime initialDate;

    private LocalDateTime finalDate;
    private UserDTO user;
    private BookDTO book;
    private Integer returned;

    private LocalDateTime returnedDate;

}
