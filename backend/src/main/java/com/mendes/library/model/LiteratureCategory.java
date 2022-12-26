package com.mendes.library.model;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "literature_category")
public class LiteratureCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String categoryName;

}
