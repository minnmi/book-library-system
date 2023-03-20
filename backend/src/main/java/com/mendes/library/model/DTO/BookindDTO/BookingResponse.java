package com.mendes.library.model.DTO.BookindDTO;

import com.mendes.library.model.DTO.BookDTO.BookDTO;
import com.mendes.library.model.DTO.UserDTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {

    private Long id;
    private UserDTO user;
    private BookDTO book;
    private LocalDateTime currentDate;
    private Integer priority;
}
