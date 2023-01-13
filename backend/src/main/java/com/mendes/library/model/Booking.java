package com.mendes.library.model;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Audited
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    private Date currentDate;

    private Integer priority;


}
