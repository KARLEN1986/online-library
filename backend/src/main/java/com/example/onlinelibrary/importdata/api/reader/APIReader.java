package com.example.onlinelibrary.importdata.api.reader;

import com.example.onlinelibrary.domain.book.Book;
import com.example.onlinelibrary.importdata.api.APIBookDto;
import com.example.onlinelibrary.importdata.api.APIResponse;
import com.example.onlinelibrary.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * API Reader class for books data
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class APIReader {

    private static final Logger logger = LoggerFactory.getLogger(APIReader.class);
    private static final String API_URL = "https://fakerapi.it/api/v1/books?_quantity=100&_locale=en_US";

    /**
     * Method fetch books from API
     *
     * @return {@link List<Book>}
     */
    public List<Book> fetchBooksFromAPI() {

        logger.info("Fetching books data from API...");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<APIResponse> responseEntity = restTemplate.exchange(
                API_URL,
                HttpMethod.GET,
                null,
                APIResponse.class
        );

        List<Book> fetchedBooks = new ArrayList<>();

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            APIResponse apiResponse = responseEntity.getBody();
            if (apiResponse != null) {
                List<APIBookDto> apiResponseData = apiResponse.getData();
                if (apiResponseData != null) {
                    for (APIBookDto apiBookDto : apiResponseData) {
                        Book book = new Book();
                        book.setTitle(apiBookDto.getTitle());
                        book.setAuthor(apiBookDto.getAuthor());
                        book.setGenre(apiBookDto.getGenre());
                        book.setDescription(apiBookDto.getDescription());
                        book.setIsbn(apiBookDto.getIsbn());
                        book.setImage(apiBookDto.getImage());

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate localDate = LocalDate.parse(apiBookDto.getPublished(), formatter);
                        LocalDateTime localDateTime = localDate.atStartOfDay();
                        book.setPublished(localDateTime);

                        book.setPublisher(apiBookDto.getPublisher());
                        fetchedBooks.add(book);
                    }
                }
            }
        }

        logger.info("Fetched {} books from API", fetchedBooks.size());

        return fetchedBooks;
    }
}
