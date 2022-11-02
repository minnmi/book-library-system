package com.mendes.library.model.DTO;


import com.mendes.library.model.Author;
import com.mendes.library.model.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long id;
    private String name;
    private String isbn;
    private List<AuthorIdDTO> authors;
    private PublisherIdDTO publisher;

}
