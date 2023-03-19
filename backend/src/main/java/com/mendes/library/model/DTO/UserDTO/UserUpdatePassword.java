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
public class UserUpdatePassword {

    @NotNull(message = "The email is required.")
    private String email;

    @NotNull(message = "The Old Password is required.")
    private String oldpassword;

    @NotNull(message = "The New Password is required.")
    private String newpassword;

    @NotNull(message = "The Confirm Password is required.")
    private String confirmpassword;

}

