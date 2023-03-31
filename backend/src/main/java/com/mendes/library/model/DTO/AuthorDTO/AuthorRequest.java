package com.mendes.library.model.DTO.AuthorDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRequest {

    @NotNull(message = "The author name is required.")
    private String name;
}
