package com.mendes.library.model.DTO.BookDTO;

import com.mendes.library.model.DTO.AuthorDTO.AuthorResponse;
import com.mendes.library.model.DTO.LiteratureCategoryDTO.LiteratureCategoryDTO;
import com.mendes.library.model.DTO.PublisherDTO.PublisherDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {
    private String name;
    @NotNull(message = "The isbn is required.")
    private String isbn;
    @NotNull(message = "The author is required.")
    private List<AuthorResponse> authors;
    @NotNull(message = "The publisher is required.")
    private PublisherDTO publisher;
    @NotNull(message = "The quantity is required.")
    private Integer quantity;
    @NotNull(message = "The category name is required.")
    private LiteratureCategoryDTO categoryName;

}
