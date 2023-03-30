package com.mendes.library.model.DTO.UserDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserUpdateEmailDTO {

    @NotNull(message = "The email is required.")
    private String email;
}
