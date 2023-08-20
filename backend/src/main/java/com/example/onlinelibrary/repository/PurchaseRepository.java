package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.domain.purchase.Purchase;
import com.example.onlinelibrary.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing purchase entities.
 */
@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    /**
     * Find purchases associated with a specific user.
     *
     * @param userId The ID of the user whose associated purchases will be retrieved.
     * @return A list of {@link Purchase} entities associated with the user.
     */
    List<Purchase> findByUserId(Long userId);

    /**
     * Find purchases associated with a specific user.
     *
     * @param user The user whose associated purchases will be retrieved.
     * @return A list of {@link Purchase} entities associated with the user.
     */
    List<Purchase> findByUser(User user);

    /**
     * Find a purchase by its ID and associated user.
     *
     * @param purchaseId The ID of the purchase to retrieve.
     * @param user       The user associated with the purchase.
     * @return The {@link Purchase} entity if found, otherwise null.
     */
    Optional<Purchase> findByIdAndUser(Long purchaseId, User user);

}
