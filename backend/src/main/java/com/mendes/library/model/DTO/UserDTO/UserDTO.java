package com.mendes.library.model.DTO.UserDTO;

import com.mendes.library.model.DTO.RolesDTO.RolesDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private boolean enabled;

}
