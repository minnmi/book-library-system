package com.mendes.library.model.DTO.LoanedDTO;


import com.mendes.library.model.DTO.BookDTO.BookResponse;
import com.mendes.library.model.DTO.UserDTO.UserDTO;
import com.mendes.library.model.DTO.UserDTO.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;

    private LocalDateTime initialDate;

    private LocalDateTime finalDate;

    private UserDTO user;

    private BookResponse book;

    private Integer returned;

    private LocalDateTime returnedDate;

}
