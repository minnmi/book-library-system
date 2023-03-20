package com.mendes.library.model.DTO.PublisherDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PublisherRequest {
    @NotNull(message = "The name is required.")
    private String name;
}
