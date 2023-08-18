package com.example.onlinelibrary.importdata;

import com.example.onlinelibrary.domain.book.Book;
import com.example.onlinelibrary.domain.user.Authority;
import com.example.onlinelibrary.domain.user.User;
import com.example.onlinelibrary.importdata.api.reader.APIReader;
import com.example.onlinelibrary.dataextractor.UserDataExtractor;
import com.example.onlinelibrary.repository.AuthorityRepository;
import com.example.onlinelibrary.repository.BookRepository;
import com.example.onlinelibrary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.example.onlinelibrary.domain.enums.AuthorityName.*;

import java.util.List;

/**
 * Loads initial data from CSV and API sources into the application.
 */
@Component
@RequiredArgsConstructor
@DependsOn({"userRepository", "bookRepository"})
@Slf4j
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final AuthorityRepository authorityRepository;
    private final UserDataExtractor userDataExtractor;
    private final APIReader apiReader;

    /**
     * Loads initial data from CSV and API sources into the application.
     *
     * @param args Command-line arguments.
     * @throws Exception If an error occurs during data loading.
     */
    @Override
    public void run(String... args) throws Exception {
        createAuthorities();
        loadUsersFromCSV();
        loadAdminAndSuperAdminUsers();
        fetchBooksFromAPI();
        linkAdminUsersWithBooks();
    }

    /**
     * Creates and saves predefined authority roles.
     * This method deletes all existing authorities and then creates new ones.
     */
    private void createAuthorities() {

        logger.info("Creating and saving predefined authority roles...");

        authorityRepository.deleteAll();
        Authority userRole = new Authority();
        userRole.setName(ROLE_USER);

        Authority adminRole = new Authority();
        adminRole.setName(ROLE_ADMIN);

        Authority superAdminRole = new Authority();
        superAdminRole.setName(ROLE_SUPER_ADMIN);

        authorityRepository.saveAll(List.of(userRole, adminRole, superAdminRole));

        logger.info("Predefined authority roles created and saved.");

    }

    /**
     * Loads user data from CSV file into the database.
     */
    @Transactional
    public void loadUsersFromCSV() {
        logger.info("Loading user data from CSV...");
        userRepository.deleteAll();
        List<User> users = userDataExtractor.parseUsersFromCSV();
        userRepository.saveAll(users);
        logger.info("User data loaded from CSV and saved.");
    }

    /**
     * Loads user data for admin and super admin into the database.
     */
    @Transactional
    public void loadAdminAndSuperAdminUsers() {
        logger.info("Loading admin and super admin users...");
        List<User> users = userDataExtractor.addAdminAndSuperAdminUsers();
        userRepository.saveAll(users);
        logger.info("Admin and super admin users loaded and saved.");
    }

    /**
     * Fetches book data from the API and stores it in the database.
     */
    @Transactional
    public void fetchBooksFromAPI() {
        logger.info("Fetching book data from API...");
        bookRepository.deleteAll();
        List<Book> booksFromAPI = apiReader.fetchBooksFromAPI();
        bookRepository.saveAll(booksFromAPI);
        logger.info("Book data fetched from API and saved.");
    }

    /**
     * Links admin users with all books from the API.
     */
    @Transactional
    public void linkAdminUsersWithBooks() {
        logger.info("Linking admin users with books...");
        List<User> adminUsers = userRepository.findByAuthorities_Name(ROLE_ADMIN);
        List<Book> allBooksFromAPI = bookRepository.findAll();

        for (User adminUser : adminUsers) {
            adminUser.setBooks(allBooksFromAPI);
            userRepository.save(adminUser);
        }
        logger.info("Admin users linked with books.");
    }


}