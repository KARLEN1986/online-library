package com.example.onlinelibrary.web.security.expression;

import com.example.onlinelibrary.domain.enums.AuthorityName;
import com.example.onlinelibrary.service.UserService;
import com.example.onlinelibrary.web.security.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Custom security expression methods for checking user's access rights.
 */
@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final UserService userService;

    /**
     * Checks whether the authenticated user can access the specified user.
     *
     * @param id The ID of the user to check access for.
     * @return True if the authenticated user can access the specified user, false otherwise.
     */
    public boolean canAccessUser(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JwtUserDetails user = (JwtUserDetails) authentication.getPrincipal();
        Long userId = user.getId();

        return userId.equals(id) || hasAnyRole(authentication, AuthorityName.ROLE_ADMIN, AuthorityName.ROLE_USER);
    }

    /**
     * Checks whether the authenticated user can access the specified book.
     *
     * @param bookId The ID of the book to check access for.
     * @return True if the authenticated user can access the specified book, false otherwise.
     */
    public boolean canAccessBook(Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        JwtUserDetails user = (JwtUserDetails) authentication.getPrincipal();
        Long userId = user.getId();

        return userService.isBookAssignToUser(userId, bookId);
    }

    private boolean hasAnyRole(Authentication authentication, AuthorityName... roles) {
        for (AuthorityName role : roles) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
            if (authentication.getAuthorities().contains(authority)) {
                return true;
            }
        }
        return false;
    }

}
