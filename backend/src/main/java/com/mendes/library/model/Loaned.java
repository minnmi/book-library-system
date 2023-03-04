package com.mendes.library.model;


import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Audited
@Data
@Builder
@Table(name = "loaned")
public class Loaned {

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

}
