package com.example.digitalLibrary.entity;

public enum BookType {
    EBOOK("Electronic Book"),
    AUDIO_BOOK("Audio Book"),
    RESEARCH_PAPER("Research Paper");

    private final String description;

    BookType(String description) {
        this.description = description;
    }


    public String getDescription() {
        return description;
    }


}
