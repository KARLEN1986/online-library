package com.example.onlinelibrary.importdata.api;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a book retrieved from an API.
 */
@Setter
@Getter
public class APIBookDto {

    private String title;

    private String author;

    private String genre;

    private String description;

    private String isbn;

    private String image;

    private String published;

    private String publisher;


}