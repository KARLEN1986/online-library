package com.example.onlinelibrary.service;

import com.example.onlinelibrary.domain.book.Book;

import java.util.Comparator;
import java.util.List;

/**
 * Service interface for providing book recommendations to users.
 */
public interface RecommendationService {

    /**
     * Recommend a list of books for a specific user, sorted using the given comparator.
     *
     * @param userId            The ID of the user for whom book recommendations are generated.
     * @param sortingComparator The {@link Comparator<Book>} used to sort the recommended books.
     * @return A list of recommended {@link Book} entities for the user.
     */
    List<Book> recommendBooksForUser(Long userId, Comparator<Book> sortingComparator);
}
