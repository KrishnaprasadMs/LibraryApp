package com.example.digitalLibrary.entity;

public enum Genre {
    FICTION("Fiction"),
    NON_FICTION("Non-Fiction"),
    SCIENCE_FICTION("Science Fiction"),
    SCIENCE("Science"),
    HISTORY("History");

    private final String description;

    Genre(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
