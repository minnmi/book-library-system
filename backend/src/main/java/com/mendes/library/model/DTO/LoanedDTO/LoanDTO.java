package com.mendes.library.model.DTO.LoanedDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {

    private LocalDateTime initialDate;

    private LocalDateTime finalDate;

    private Integer returned;

    private LocalDateTime returnedDate;
}
