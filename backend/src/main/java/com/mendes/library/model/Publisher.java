package com.mendes.library.model;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Data
@Audited
@Table(name = "publisher")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

}
