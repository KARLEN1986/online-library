package com.example.onlinelibrary.web.controller;

import com.example.onlinelibrary.domain.book.Book;
import com.example.onlinelibrary.domain.exception.AccessDeniedException;
import com.example.onlinelibrary.domain.purchase.Purchase;
import com.example.onlinelibrary.domain.user.User;
import com.example.onlinelibrary.service.BookService;
import com.example.onlinelibrary.service.PurchaseService;
import com.example.onlinelibrary.service.UserService;
import com.example.onlinelibrary.web.dto.purchase.PurchaseDto;
import com.example.onlinelibrary.web.mappers.PurchaseMapper;
import com.example.onlinelibrary.web.security.expression.CustomSecurityExpression;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing purchase-related operations.
 */
@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
@Tag(name = "Purchase Controller", description = "Purchase API")
@Slf4j
public class PurchaseController {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);

    private final PurchaseService purchaseService;
    private final BookService bookService;
    private final UserService userService;
    private final PurchaseMapper purchaseMapper;
    private final CustomSecurityExpression customSecurityExpression;

    /**
     * Buy a book.
     *
     * @param bookId The ID of the book to buy.
     * @return A {@link PurchaseDto} representing the purchase.
     */
    @PostMapping("/buy/{bookId}")
    @Operation(summary = "Buy a book")
    public PurchaseDto buyBook(@PathVariable Long bookId) {
        User user = getCurrentAuthenticatedUser();
        Book book = bookService.getBookById(bookId);

        // Check if the user can access the book
        if (!customSecurityExpression.canAccessBook(bookId)) {
            throw new AccessDeniedException();
        }

        Purchase purchase = purchaseService.createPurchase(user, book);
        logger.info("User {} bought the book with ID {}", user.getEmail(), bookId);
        return purchaseMapper.toDto(purchase);
    }

    /**
     * Get all purchases made by the currently authenticated user.
     *
     * @return A list of {@link PurchaseDto} representing the user's purchases.
     */
    @GetMapping("/user")
    @Operation(summary = "Get all purchases made by the user")
    public List<PurchaseDto> getUserPurchases() {
        User user = getCurrentAuthenticatedUser();
        List<Purchase> purchases = purchaseService.getPurchasesByUser(user);
        logger.info("Retrieved {} purchases for user {}", purchases.size(), user.getEmail());
        return purchaseMapper.toDto(purchases);
    }

    /**
     * Get purchase details by its ID.
     *
     * @param purchaseId The ID of the purchase to retrieve.
     * @return A {@link PurchaseDto} representing the purchase details.
     */
    @GetMapping("/{purchaseId}")
    @Operation(summary = "Get purchase details by ID")
    public PurchaseDto getPurchaseDetails(@PathVariable Long purchaseId) {
        User user = getCurrentAuthenticatedUser();
        Purchase purchase = purchaseService.getPurchaseByIdAndUser(purchaseId, user);

        if (purchase != null) {
            logger.info("User {} retrieved details of purchase with ID {}", user.getEmail(), purchaseId);
            return purchaseMapper.toDto(purchase);
        } else {
            logger.warn("User {} attempted to access invalid purchase with ID {}", user.getEmail(), purchaseId);
            throw new EntityNotFoundException("Purchase not found or not owned by user.");
        }
    }

    /**
     * Rate a purchase.
     *
     * @param purchaseId The ID of the purchase to rate.
     * @param rating     The rating to assign to the purchase.
     * @return A {@link ResponseEntity} indicating the result of the rating operation.
     */
    @PostMapping("/rate/{purchaseId}/{rating}")
    @Operation(summary = "Rate a purchase")
    public ResponseEntity<String> ratePurchase(@PathVariable Long purchaseId, @PathVariable int rating) {
        User user = getCurrentAuthenticatedUser();
        Purchase purchase = purchaseService.getPurchaseByIdAndUser(purchaseId, user);

        if (purchase != null) {
            purchaseService.ratePurchase(purchaseId, rating);
            logger.info("User {} rated the purchase with ID {}", user.getEmail(), purchaseId);
            return ResponseEntity.ok("Purchase rated successfully.");
        } else {
            logger.warn("Invalid purchase or not owned by user: purchaseId={}, userId={}", purchaseId, user.getId());
            return ResponseEntity.badRequest().body("Invalid purchase or not owned by user.");
        }
    }

    private User getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getByUsername(authentication.getName());
    }
}
