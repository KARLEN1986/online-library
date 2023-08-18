package com.example.onlinelibrary.repository;

import com.example.onlinelibrary.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing book entities.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Modifying
    @Query(value = "INSERT INTO user_books (user_id, book_id) VALUES (:userId, :bookId)", nativeQuery = true)
    void assignBookToUser(@Param("userId") Long userId, @Param("bookId") Long bookId);

    /**
     * Find books by their genre.
     *
     * @param genre The genre of the books to retrieve.
     * @return A list of {@link Book} entities with the specified genre.
     */
    List<Book> findByGenre(String genre);

    /**
     * Find books associated with a specific user.
     *
     * @param userId The ID of the user whose associated books will be retrieved.
     * @return A list of {@link Book} entities associated with the user.
     */
    List<Book> findByUsers_Id(Long userId);
}
