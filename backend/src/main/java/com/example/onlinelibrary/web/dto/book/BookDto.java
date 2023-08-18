package com.example.onlinelibrary.web.dto.book;

import com.example.onlinelibrary.web.dto.validation.OnCreate;
import com.example.onlinelibrary.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class BookDto {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Title must be not null.", groups = {OnCreate.class, OnUpdate.class})
    @Length(max = 255, message = "Title length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String title;

    @Length(max = 255, message = "Author length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String author;

    @Length(max = 255, message = "Genre length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String genre;

    @Length(max = 255, message = "Description length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String description;

    @Length(max = 255, message = "Isbn length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String isbn;

    @Length(max = 255, message = "Image length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String image;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime published;

    @Length(max = 255, message = "Publisher length must be smaller than 255 symbols.", groups = {OnCreate.class, OnUpdate.class})
    private String publisher;

}
