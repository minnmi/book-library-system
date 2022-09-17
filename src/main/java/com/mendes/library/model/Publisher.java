package com.mendes.library.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "publisher")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

}
