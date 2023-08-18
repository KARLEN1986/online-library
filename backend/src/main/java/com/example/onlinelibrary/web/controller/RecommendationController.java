package com.example.onlinelibrary.web.controller;

import com.example.onlinelibrary.domain.book.Book;
import com.example.onlinelibrary.service.RecommendationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

/**
 * Controller class responsible for handling recommendations related API requests.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recommendations")
@Tag(name = "Recommendation Controller", description = "Recommendation API")
@Slf4j
public class RecommendationController {

    private static final Logger logger = LoggerFactory.getLogger(RecommendationController.class);
    private final RecommendationService recommendationService;

    /**
     * Get book recommendations for a user based on their preferences.
     *
     * @param userId The ID of the user for whom recommendations are generated.
     * @return A list of recommended books for the user.
     */
    @GetMapping("/{userId}")
    public List<Book> getRecommendations(@PathVariable Long userId) {

        logger.info("Received request to get recommendations for user with ID: {}", userId);

        // Define a sorting comparator based on book rating in descending order
        Comparator<Book> ratingComparator = Comparator.comparingDouble(Book::getRating)
                .reversed();

        // Get recommended books for the user with the specified comparator
        List<Book> recommendedBooks = recommendationService.recommendBooksForUser(userId, ratingComparator);

        logger.info("Recommended {} books for user with ID: {}", recommendedBooks.size(), userId);

        return recommendedBooks;
    }
}
