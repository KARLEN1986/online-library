package com.example.onlinelibrary.web.controller;

import com.example.onlinelibrary.domain.book.Book;
import com.example.onlinelibrary.domain.user.User;
import com.example.onlinelibrary.service.BookService;
import com.example.onlinelibrary.service.UserService;
import com.example.onlinelibrary.web.dto.book.BookDto;
import com.example.onlinelibrary.web.dto.user.UserDto;
import com.example.onlinelibrary.web.dto.validation.OnCreate;
import com.example.onlinelibrary.web.dto.validation.OnUpdate;
import com.example.onlinelibrary.web.mappers.BookMapper;
import com.example.onlinelibrary.web.mappers.UserMapper;
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
 * Controller class for user-related operations.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Controller", description = "User API")
@Slf4j
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final BookService bookService;

    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    /**
     * Get a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return A {@link UserDto} representing the user.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get UserDto by id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public UserDto getById(@PathVariable Long id) {
        logger.info("Received request to get user with ID: {}", id);
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    /**
     * Update a user's information.
     *
     * @param userDto The {@link UserDto} containing the updated user information.
     * @return A {@link UserDto} representing the updated user.
     */
    @PutMapping
    @Operation(summary = "Update user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#userDto.id)")
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
        logger.info("Received request to update user with ID: {}", userDto.getId());
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }

    /**
     * Delete a user by their ID.
     *
     * @param id The ID of the user to delete.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public void deleteById(@PathVariable Long id) {
        logger.info("Received request to delete user with ID: {}", id);
        userService.delete(id);
        logger.info("User with ID {} deleted successfully", id);
    }

    /**
     * Add a book to a user's collection.
     *
     * @param id  The ID of the user to whom the book will be added.
     * @param dto The {@link BookDto} containing book information.
     * @return A {@link BookDto} representing the added book.
     */
    @PostMapping("/{id}/books")
    @Operation(summary = "Add book to user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public BookDto createBook(@PathVariable Long id,
                              @Validated(OnCreate.class) @RequestBody BookDto dto) {
        logger.info("Received request to add a book to user with ID: {}", id);
        Book book = bookMapper.toEntity(dto);
        Book createdBook = bookService.create(book, id);
        logger.info("Book added to user with ID: {}", id);
        return bookMapper.toDto(createdBook);
    }

    /**
     * Get all books belonging to a specific user.
     *
     * @param id The ID of the user whose books will be retrieved.
     * @return A list of {@link BookDto} representing the user's books.
     */
    @GetMapping("/{id}/books")
    @Operation(summary = "Get all User books")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public List<BookDto> getBooksByUserId(@PathVariable Long id) {
        logger.info("Received request to get books for user with ID: {}", id);
        List<Book> books = bookService.getAllByUserId(id);
        return bookMapper.toDto(books);
    }

}
