package com.mendes.library.model.DTO.LoanedDTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.mendes.library.model.Book;
import com.mendes.library.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @JsonFormat(pattern = "MM/dd/yyyy HH:mm")
    private LocalDateTime initialDate;
    @JsonFormat(pattern = "MM/dd/yyyy HH:mm")
    private LocalDateTime finalDate;
    private User user;
    private Book book;
    private Integer returned;

    @JsonFormat(pattern = "MM/dd/yyyy HH:mm")
    private LocalDateTime returnedDate;

}
