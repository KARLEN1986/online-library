package com.example.onlinelibrary.domain.purchase;

import com.example.onlinelibrary.domain.book.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Column(name = "rating")
    private int rating;

}
