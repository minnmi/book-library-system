package com.mendes.library.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

@Table(name = "configuration")
@Entity
@Data
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    @Min(value = 0)
    private int maximumNumberBooksUser;

    @Column(nullable = false)
    @Min(value = 0)
    private int maximumBookingPeriod;

    @Column(nullable = false)
    @DecimalMin(value = "0.0")
    private float proportionBooksStock;
}
