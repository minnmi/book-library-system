package com.mendes.library.model.DTO.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String name;
    @NotNull(message = "The username is required.")
    private String username;
    @NotNull(message = "The email is required.")
    private String email;
    @NotNull(message = "The password is required.")
    private String password;
    private boolean enabled;

}
