package com.mendes.library.model.DTO.BookDTO;


import com.mendes.library.model.DTO.AuthorDTO.AuthorResponse;
import com.mendes.library.model.DTO.LiteratureCategoryDTO.LiteratureCategoryIdDTO;
import com.mendes.library.model.DTO.PublisherDTO.PublisherIdDTO;
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
    private List<AuthorResponse> authors;
    private PublisherIdDTO publisher;
    private Integer quantity;
    private LiteratureCategoryIdDTO categoryName;

}
