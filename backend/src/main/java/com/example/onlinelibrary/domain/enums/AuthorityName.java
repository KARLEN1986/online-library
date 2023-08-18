package com.example.onlinelibrary.domain.enums;

/**
 * Enumeration representing the possible authority roles in the application.
 * These roles are used for access control and permission management.
 */
public enum AuthorityName {

    /**
     * Standard user role.
     */
    ROLE_USER,

    /**
     * Administrator role with additional privileges.
     */
    ROLE_ADMIN,

    /**
     * Super administrator role with the highest level of privileges.
     */
    ROLE_SUPER_ADMIN

}