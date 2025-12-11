package com.example.booking.enums;

/**
 * Enum representing user account status
 */
public enum UserStatus {
    ACTIVE("Active", "User account is active"),
    INACTIVE("Inactive", "User account is inactive"),
    SUSPENDED("Suspended", "User account is suspended"),
    PENDING("Pending", "User account is pending approval"),
    ON_LEAVE("On Leave", "User is on leave");

    private final String displayName;
    private final String description;

    UserStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}

