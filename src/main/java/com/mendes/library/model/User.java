package com.mendes.library.model;

import lombok.Data;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Data
@Audited
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;

    @Length(min = 7, max = 20)
    private String password;
}
