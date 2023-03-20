package com.mendes.library.model.DTO.UserDTO;

import com.mendes.library.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoles {
    private Set<Role> roles;

    private UserDTO user;
}
