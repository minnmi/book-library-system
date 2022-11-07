package com.mendes.library.model.DTO.LoanedDTO;


import com.mendes.library.model.Book;
import com.mendes.library.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanedDTO {

    private Long id;
    private Date initialDate;
    private Date finalDate;
    private User user;
    private Book book;

}
