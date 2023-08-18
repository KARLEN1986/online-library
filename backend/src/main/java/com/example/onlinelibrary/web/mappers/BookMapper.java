package com.example.onlinelibrary.web.mappers;

import com.example.onlinelibrary.domain.book.Book;
import com.example.onlinelibrary.web.dto.book.BookDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper interface for converting Book and BookDto objects.
 * Uses the MapStruct library with Spring component model.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {
    /**
     * Converts a Book object to a BookDto object.
     *
     * @param book The Book object to be converted.
     * @return The corresponding BookDto object.
     */
    BookDto toDto(Book book);

    /**
     * Converts a list of Book objects to a list of BookDto objects.
     *
     * @param books The list of Book objects to be converted.
     * @return The corresponding list of BookDto objects.
     */
    List<BookDto> toDto(List<Book> books);

    /**
     * Converts a BookDto object to a Book object.
     *
     * @param dto The BookDto object to be converted.
     * @return The corresponding Book object.
     */
    Book toEntity(BookDto dto);

}
