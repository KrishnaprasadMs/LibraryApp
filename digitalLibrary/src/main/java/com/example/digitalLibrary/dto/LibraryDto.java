package com.example.digitalLibrary.dto;

public class LibraryDto {

    private String title;
    private String author;
    private String type;
    private String genre;
    private String publicationDate;
    private String isbn;
    private String availabilityStatus;

    public LibraryDto() {
    }

    public LibraryDto(String title, String author, String type, String genre, String publicationDate, String isbn, String availabilityStatus) {
        this.title = title;
        this.author = author;
        this.type = type;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
        this.availabilityStatus = availabilityStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }
}
