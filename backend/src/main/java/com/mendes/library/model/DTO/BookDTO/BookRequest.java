package com.mendes.library.model.DTO.BookDTO;

import com.mendes.library.model.DTO.AuthorDTO.AuthorResponse;
import com.mendes.library.model.DTO.LiteratureCategoryDTO.LiteratureCategoryDTO;
import com.mendes.library.model.DTO.PublisherDTO.PublisherDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private String name;
    private String isbn;
    private List<AuthorResponse> authors;
    private PublisherDTO publisher;
    private Integer quantity;
    private LiteratureCategoryDTO categoryName;

}
