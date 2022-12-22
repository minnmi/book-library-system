package com.mendes.library.model.DTO.AuthorDTO;

import com.mendes.library.model.DTO.BookDTO.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {

    private Long id;
    private String name;
    private List<BookDTO> books;
}
