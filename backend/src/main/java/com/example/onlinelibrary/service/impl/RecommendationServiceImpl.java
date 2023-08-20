package com.example.onlinelibrary.service.impl;

import com.example.onlinelibrary.domain.book.Book;
import com.example.onlinelibrary.domain.purchase.Purchase;
import com.example.onlinelibrary.service.BookService;
import com.example.onlinelibrary.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementation of the RecommendationService interface for recommending books to users.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

    private static final Logger logger = LoggerFactory.getLogger(RecommendationServiceImpl.class);

    private final PurchaseServiceImpl purchaseService;

    private final BookService bookService;

    @Override
    public List<Book> recommendBooksForUser(Long userId, Comparator<Book> sortingComparator) {
        List<Purchase> userPurchases = purchaseService.getPurchasesByUserId(userId);
        Set<String> userGenres = userPurchases.stream()
                .map(purchase -> bookService.getBookById(purchase.getBook().getId()))
                .map(Book::getGenre)
                .collect(Collectors.toSet());

        List<Book> recommendedBooks = new ArrayList<>();
        for (String genre : userGenres) {
            List<Book> booksInGenre = bookService.getBooksByGenre(genre);
            recommendedBooks.addAll(booksInGenre);
        }

        recommendedBooks.removeIf(book -> userPurchases.stream()
                .anyMatch(purchase -> purchase.getBook().getId().equals(book.getId())));

        recommendedBooks.sort(sortingComparator);

        logger.info("Recommended books for User ID {}: {}", userId, recommendedBooks);

        return recommendedBooks;
    }

}
