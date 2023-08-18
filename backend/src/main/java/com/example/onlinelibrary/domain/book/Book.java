package com.example.onlinelibrary.domain.book;

import com.example.onlinelibrary.domain.purchase.Purchase;
import com.example.onlinelibrary.domain.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "genre")
    private String genre;

    @Column(name = "description")
    private String description;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "image")
    private String image;

    @Column(name = "published")
    private LocalDateTime published;

    @Column(name = "publisher")
    private String publisher;

    @OneToMany(mappedBy = "book")
    private List<Purchase> purchases;

    @Column(name = "rating")
    private double rating;

    @ManyToMany(mappedBy = "books", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> users;

    public double calculateRating() {
        if (purchases == null || purchases.isEmpty()) {
            return 0.0;
        }

        double totalRating = purchases.stream()
                .mapToDouble(Purchase::getRating)
                .sum();

        return totalRating / purchases.size();
    }

    public int getPurchaseCount() {
        return purchases != null ? purchases.size() : 0;
    }

}
