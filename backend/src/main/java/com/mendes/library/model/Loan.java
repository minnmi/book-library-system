package com.mendes.library.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Entity
@Audited
@Getter
@Setter
@AllArgsConstructor
@Table(name = "loaned")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime initialDate;

    private LocalDateTime finalDate;

    private Integer returned;

    @Column(name = "returned_date")
    private LocalDateTime returnedDate;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public Loan() {}
}
