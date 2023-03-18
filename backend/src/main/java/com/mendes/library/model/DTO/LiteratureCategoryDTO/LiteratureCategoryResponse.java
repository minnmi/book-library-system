package com.mendes.library.model.DTO.LiteratureCategoryDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LiteratureCategoryResponse {

    private Long id;
    private String categoryName;

}
