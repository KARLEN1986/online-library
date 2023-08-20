package com.example.onlinelibrary.service;

import com.example.onlinelibrary.domain.book.Book;
import com.example.onlinelibrary.domain.exception.ResourceNotFoundException;
import com.example.onlinelibrary.domain.purchase.Purchase;
import com.example.onlinelibrary.domain.user.User;

import java.util.List;

/**
 * Service interface for managing purchase-related operations.
 */
public interface PurchaseService {

    /**
     * Get a list of purchases associated with a specific user ID.
     *
     * @param userId The ID of the user whose associated purchases will be retrieved.
     * @return A list of {@link Purchase} entities associated with the user.
     */
    List<Purchase> getPurchasesByUserId(Long userId);

    /**
     * Create a purchase record for a user buying a book.
     *
     * @param user The user making the purchase.
     * @param book The book being purchased.
     * @return The created {@link Purchase} entity representing the purchase record.
     */
    Purchase createPurchase(User user, Book book);

    /**
     * Rate a purchase.
     *
     * @param purchaseId The ID of the purchase to rate.
     * @param rating     The rating to assign to the purchase.
     */
    void ratePurchase(Long purchaseId, int rating);

    /**
     * Get all purchases made by the specified user.
     *
     * @param user The user for whom to retrieve purchases.
     * @return A list of purchases made by the user.
     */
    List<Purchase> getPurchasesByUser(User user);

    /**
     * Get a purchase by its ID and user.
     *
     * @param purchaseId The ID of the purchase to retrieve.
     * @param user       The user associated with the purchase.
     * @return The {@link Purchase} entity if found, otherwise null.
     * @throws ResourceNotFoundException if the purchase is not found or not owned by the user.
     */
    Purchase getPurchaseByIdAndUser(Long purchaseId, User user);
}
