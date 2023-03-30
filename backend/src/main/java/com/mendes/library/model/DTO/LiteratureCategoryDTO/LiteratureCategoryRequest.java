package com.mendes.library.model.DTO.LiteratureCategoryDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LiteratureCategoryRequest {
    @NotNull(message = "The category name is required.")
    private String categoryName;

}
