package com.mendes.library.model.DTO.UserDTO;

import com.mendes.library.model.DTO.BookDTO.BookDTO;
import com.mendes.library.model.DTO.LoanedDTO.LoanDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLoansBooksDTO {

    private Long id;

    @NotNull(message = "The Name is required.")
    @NotBlank(message = "The name can't be blank")
    private String name;

    private BookDTO book;

    private LoanDTO loan;
}
