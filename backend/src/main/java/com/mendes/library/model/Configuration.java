package com.mendes.library.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;

@Data
@Entity
@Table(name = "configuration")
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "maximum_number_books_user", nullable = false)
    @Min(value = 0)
    private int maximumNumberBooksUser;

    @Column(name = "maximum_loan_period", nullable = false)
    @Min(value = 0)
    private int maximumLoanPeriod;

    @Column(name = "proportion_books_stock", nullable = false)
    @DecimalMin(value = "0.0")
    private float proportionBooksStock;

    @Column(name = "booking_time_out", nullable = false)
    public int bookingTimeOut;
}
