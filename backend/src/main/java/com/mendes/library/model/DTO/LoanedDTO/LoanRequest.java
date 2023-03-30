package com.mendes.library.model.DTO.LoanedDTO;

import com.mendes.library.model.DTO.BookDTO.BookDTO;
import com.mendes.library.model.DTO.BookDTO.BookResponse;
import com.mendes.library.model.DTO.UserDTO.UserDTO;
import com.mendes.library.model.DTO.UserDTO.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime initialDate;

    private LocalDateTime finalDate;

    private UserDTO user;

    @NotNull(message = "The book is required.")
    private BookDTO book;

    private Integer returned;

    private LocalDateTime returnedDate;

}
