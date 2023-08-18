package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.domain.purchase.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
