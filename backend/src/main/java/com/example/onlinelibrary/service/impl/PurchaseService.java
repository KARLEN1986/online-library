package com.example.onlinelibrary.service.impl;

import com.example.onlinelibrary.domain.book.Book;
import com.example.onlinelibrary.domain.purchase.Purchase;
import com.example.onlinelibrary.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing purchase-related operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseService.class);

    private final PurchaseRepository purchaseRepository;

    /**
     * Get purchases associated with a specific user.
     *
     * @param userId The ID of the user whose associated purchases will be retrieved.
     * @return A list of {@link Purchase} entities associated with the user.
     */
    public List<Purchase> getPurchasesByUserId(Long userId) {
        logger.debug("Getting purchases for user with ID: {}", userId);
        return purchaseRepository.findByUserId(userId);
    }

    /**
     * Create a purchase record for a user buying a book.
     *
     * @param userId The ID of the user making the purchase.
     * @param book   The book being purchased.
     * @return The created {@link Purchase} entity representing the purchase record.
     */
    public Purchase purchaseBook(Long userId, Book book) {

        logger.debug("Creating purchase record for User ID: {} and Book ID: {}", userId, book.getId());

        Purchase purchase = new Purchase();
        purchase.setUserId(userId);
        purchase.setBook(book);

        // Save the purchase record
        Purchase savedPurchase = purchaseRepository.save(purchase);

        // Logging the purchase event
        logger.info("Purchase recorded for User ID: {}, Book ID: {}", userId, book.getId());

        return savedPurchase;
    }

}
