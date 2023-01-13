package com.mendes.library.model;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
@Data
@Table(name = "literature_category")
public class LiteratureCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String categoryName;

}
