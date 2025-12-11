package com.example.booking.enums;

/**
 * Enum representing different user roles in the booking system
 */
public enum UserRole {
    SUPER_ADMIN("Super Admin", "Full system access - can manage everything"),
    ADMIN("Admin", "Can manage doctors and patients"),
    DOCTOR("Doctor", "Can manage their appointments and availability"),
    PATIENT("Patient", "Can book and manage their appointments"),
    STAFF("Staff", "Can assist with appointment management");

    private final String displayName;
    private final String description;

    UserRole(String displayName, String description) {
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

