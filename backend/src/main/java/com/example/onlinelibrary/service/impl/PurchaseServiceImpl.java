package com.example.onlinelibrary.service.impl;

import com.example.onlinelibrary.domain.book.Book;
import com.example.onlinelibrary.domain.exception.ResourceNotFoundException;
import com.example.onlinelibrary.domain.purchase.Purchase;
import com.example.onlinelibrary.domain.user.User;
import com.example.onlinelibrary.repository.PurchaseRepository;
import com.example.onlinelibrary.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing purchase-related operations.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class);

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
     * @param user The user making the purchase.
     * @param book The book being purchased.
     * @return The created {@link Purchase} entity representing the purchase record.
     */
    public Purchase createPurchase(User user, Book book) {

        logger.debug("Creating purchase record for User ID: {} and Book ID: {}", user.getId(), book.getId());

        Purchase purchase = new Purchase();
        purchase.setUser(user);
        purchase.setBook(book);
        purchase.setPurchaseDate(LocalDateTime.now());

        // Save the purchase record
        Purchase savedPurchase = purchaseRepository.save(purchase);

        // Logging the purchase event
        logger.info("Purchase recorded for User ID: {}, Book ID: {}", user.getId(), book.getId());

        return savedPurchase;
    }

    /**
     * Get purchases associated with a specific user.
     *
     * @param user The user whose associated purchases will be retrieved.
     * @return A list of {@link Purchase} entities associated with the user.
     */
    @Override
    public List<Purchase> getPurchasesByUser(User user) {
        logger.debug("Getting purchases for user with ID: {}", user.getId());
        return purchaseRepository.findByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Purchase getPurchaseByIdAndUser(Long purchaseId, User user) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findByIdAndUser(purchaseId, user);

        if (purchaseOptional.isPresent()) {
            Purchase purchase = purchaseOptional.get();
            logger.info("Purchase retrieved: ID={}, User={}, Book={}", purchase.getId(), user.getId(), purchase.getBook().getId());
            return purchase;
        } else {
            logger.warn("Purchase not found or not owned by user: PurchaseID={}, UserID={}", purchaseId, user.getId());
            throw new ResourceNotFoundException("Purchase not found or not owned by user.");
        }
    }

    /**
     * Rate a purchase.
     *
     * @param purchaseId The ID of the purchase to rate.
     * @param rating     The rating to assign to the purchase.
     */
    public void ratePurchase(Long purchaseId, int rating) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(purchaseId);
        purchaseOptional.ifPresent(purchase -> {
            purchase.setRating(rating);
            purchaseRepository.save(purchase);
        });
    }
}
