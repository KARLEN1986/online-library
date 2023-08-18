package com.example.onlinelibrary.dataextractor;

import com.example.onlinelibrary.domain.user.Authority;
import com.example.onlinelibrary.domain.user.User;
import com.example.onlinelibrary.repository.AuthorityRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.example.onlinelibrary.domain.enums.AuthorityName.*;

/**
 * Parses user data from a CSV file and creates User objects.
 */
@Component
@RequiredArgsConstructor
public class UserDataExtractor {

    // CSV file path
    private static final String CSV_FILE_PATH = "C:\\Workspace\\quadlabels\\online-library\\backend\\src\\main\\resources\\csv\\data-users.csv";

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    /**
     * Parses user data from the CSV file and creates User objects.
     * return List of User objects created from the CSV data.
     *
     * @return {@link List<User>}
     */
    public List<User> parseUsersFromCSV() {
        List<User> users = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
            String[] line;
            boolean firstLineSkipped = false;
            while ((line = csvReader.readNext()) != null) {
                if (!firstLineSkipped) {
                    firstLineSkipped = true;
                    continue;
                }
                User user = createUser(line);
                addUserAuthorities(user);
                users.add(user);
            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    /**
     * Adds Admin and Super Admin users to the list.
     *
     * @return A list containing the created Admin and Super Admin users.
     */
    public List<User> addAdminAndSuperAdminUsers() {
        List<User> users = new ArrayList<>();
        String[][] userDataForAdminAndSuperAdmin = {
                {"Admin", "(793) 205-4828", "admin@gmail.com", "7588 Cure Street", "38632", "United States", "ADMIN", "648 61449 82813 446", "Oct 1, 2023", "121"},
                {"Super Admin", "(793) 206-4828", "superadmin@gmail.com", "7555 Fuse Av", "38633", "United States", "SUPERADMIN", "648 61455 82813 446", "Oct 5, 2023", "151"}
        };

        for (String[] userData : userDataForAdminAndSuperAdmin) {
            User user = createUser(userData);
            addUserAuthorities(user);
            users.add(user);
        }
        return users;
    }

    /**
     * Creates a User object based on the provided data array.
     *
     * @param data The data array containing user details.
     * @return A User object with the configured attributes.
     */
    private User createUser(String[] data) {
        User user = new User();
        user.setName(data[0]);
        user.setPhone(data[1]);
        user.setEmail(data[2]);
        user.setAddress(data[3]);
        user.setPostalZip(data[4]);
        user.setCountry(data[5]);
        user.setPassword(passwordEncoder.encode(data[6]));
        user.setPan(data[7]);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH);
        LocalDate localDate = LocalDate.parse(data[8], formatter);
        LocalDateTime expirationDate = localDate.atStartOfDay();
        user.setExpirationDate(expirationDate);
        user.setCvv(data[9]);
        return user;
    }

    /**
     * Adds ROLE_USER , ROLE_ADMIN and ROLE_SUPER_ADMIN authority to the user.
     *
     * @param user The user to whom the authority is added.
     */
    private void addUserAuthorities(User user) {
        Set<Authority> authorities = new HashSet<>();
        if (user.getName().equals("Admin")) {
            Authority adminAuthority = authorityRepository.findByName(ROLE_ADMIN);
            authorities.add(adminAuthority);
        } else if (user.getName().equals("Super Admin")) {
            Authority superAdminAuthority = authorityRepository.findByName(ROLE_SUPER_ADMIN);
            authorities.add(superAdminAuthority);
        } else {
            Authority userAuthority = authorityRepository.findByName(ROLE_USER);
            authorities.add(userAuthority);
        }

        user.setAuthorities(authorities);
    }
}
