package com.example.booking.enums;

/**
 * Enum representing doctor specializations
 */
public enum Specialization {
    GENERAL_PHYSICIAN("General Physician"),
    CARDIOLOGIST("Cardiologist"),
    DERMATOLOGIST("Dermatologist"),
    PEDIATRICIAN("Pediatrician"),
    ORTHOPEDIC("Orthopedic"),
    NEUROLOGIST("Neurologist"),
    PSYCHIATRIST("Psychiatrist"),
    GYNECOLOGIST("Gynecologist"),
    OPHTHALMOLOGIST("Ophthalmologist"),
    ENT_SPECIALIST("ENT Specialist"),
    DENTIST("Dentist"),
    RADIOLOGIST("Radiologist"),
    ANESTHESIOLOGIST("Anesthesiologist"),
    SURGEON("Surgeon");

    private final String displayName;

    Specialization(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

