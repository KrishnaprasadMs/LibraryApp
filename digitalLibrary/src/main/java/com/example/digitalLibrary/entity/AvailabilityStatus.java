package com.example.digitalLibrary.entity;

public enum AvailabilityStatus {
    AVAILABLE("Available"),
    CHECKED_OUT("Checked Out"),
    UNDER_MAINTENANCE("Under Maintenance");

    private final String description;

    AvailabilityStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}