package com.example.onlinelibrary.web.controller;

import com.example.onlinelibrary.domain.book.Book;
import com.example.onlinelibrary.service.BookService;
import com.example.onlinelibrary.web.dto.book.BookDto;
import com.example.onlinelibrary.web.dto.validation.OnUpdate;
import com.example.onlinelibrary.web.mappers.BookMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for managing book-related operations.
 */
@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Validated
@Tag(name = "Book Controller", description = "Book API")
@Slf4j
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;

    private final BookMapper bookMapper;


    /**
     * Get a book by its ID.
     *
     * @param id The ID of the book to retrieve.
     * @return A {@link BookDto} representing the book.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get BookDto by id")
    @PreAuthorize("@customSecurityExpression.canAccessBook(#id)")
    public BookDto getById(@PathVariable Long id) {
        logger.info("Received request to retrieve book with ID: {}", id);
        Book book = bookService.getBookById(id);
        logger.info("Retrieved book: {}", book.getTitle());
        return bookMapper.toDto(book);
    }

    /**
     * Get all books.
     *
     * @return A list of {@link BookDto} representing all books.
     */
    @GetMapping
    @Operation(summary = "Get all books")
    public List<BookDto> getAllBooks() {
        logger.info("Received request to retrieve all books");
        List<Book> allBooks = bookService.getAllBooks();
        logger.info("Retrieved {} books", allBooks.size());
        return bookMapper.toDto(allBooks);
    }

    /**
     * Delete a book by its ID.
     *
     * @param id The ID of the book to delete.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book")
    @PreAuthorize("@customSecurityExpression.canAccessBook(#id)")
    public void deleteById(@PathVariable Long id) {
        logger.info("Received request to delete book with ID: {}", id);
        bookService.delete(id);
        logger.info("Book with ID {} deleted", id);
    }

    /**
     * Update a book.
     *
     * @param bookDto The {@link BookDto} representing the updated book information.
     * @return A {@link BookDto} representing the updated book.
     */
    @PutMapping
    @Operation(summary = "Update book")
    @PreAuthorize("@customSecurityExpression.canAccessBook(#bookDto.id)")
    public BookDto update(@Validated(OnUpdate.class) @RequestBody BookDto bookDto) {
        logger.info("Received request to update book with ID: {}", bookDto.getId());
        Book book = bookMapper.toEntity(bookDto);
        Book updatedBook = bookService.update(book);
        logger.info("Updated book: {}", updatedBook.getTitle());
        return bookMapper.toDto(updatedBook);
    }


}
