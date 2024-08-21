package com.example.digitalLibrary.service;

import com.example.digitalLibrary.Exception.InvalidInputException;
import com.example.digitalLibrary.Exception.ResourceCreationException;
import com.example.digitalLibrary.Exception.ResourceNotFoundException;
import com.example.digitalLibrary.dto.LibraryDto;
import com.example.digitalLibrary.entity.AvailabilityStatus;
import com.example.digitalLibrary.entity.BookType;
import com.example.digitalLibrary.entity.Genre;
import com.example.digitalLibrary.entity.Library;
import com.example.digitalLibrary.repository.LibraryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryServiceImpl implements LibraryService {
    @Autowired
    LibraryRepo libraryRepo;

    @Override
    public List<Library> getAllLibrary() {

        return libraryRepo.findAll();
    }

    @Override
    public List<Library> getFilteredAndSortedLibrary(String type, LocalDate publishedAfterDate, String genre, String sortBy) {
        List<Library> libraryList = getAllLibrary();

        if (type != null) {
            libraryList = libraryList.stream()
                    .filter(library -> library.getType().name().equals(type))
                    .collect(Collectors.toList());
        }

        if (publishedAfterDate != null) {
            libraryList = libraryList.stream()
                    .filter(library -> library.getPublicationDate().isAfter(publishedAfterDate))
                    .collect(Collectors.toList());
        }

        if (genre != null) {
            libraryList = libraryList.stream()
                    .filter(library -> library.getGenre().name().equals(genre))
                    .collect(Collectors.toList());
        }

        if ("publication_date".equals(sortBy)) {
            libraryList = libraryList.stream()
                    .sorted((l1, l2) -> l1.getPublicationDate().compareTo(l2.getPublicationDate()))
                    .collect(Collectors.toList());
        } else { // Default to sorting by title
            libraryList = libraryList.stream()
                    .sorted((l1, l2) -> l1.getTitle().compareTo(l2.getTitle()))
                    .collect(Collectors.toList());
        }

        return libraryList;
    }

    @Override
    public List<Library> getResourcesByType(BookType bookType) {
        return getAllLibrary().stream().filter(library -> library.getType().equals(bookType)).collect(Collectors.toList());
    }

    @Override
    public List<Library> getResourcesPublishedAfter(LocalDate publishedAfterDate) {
        return getAllLibrary().stream().filter(library -> library.getPublicationDate().isAfter(publishedAfterDate)).collect(Collectors.toList());
    }

    @Override
    public List<Library> getResourcesByGenre(Genre bookGenre) {
        return getAllLibrary().stream().filter(library -> library.getGenre().equals(bookGenre)).collect(Collectors.toList());
    }

    @Override
    public List<Library> getSortedResources(String sortBy) {
        List<Library> libraryList = getAllLibrary(); // Assuming this method retrieves all libraries

        if ("publication_date".equalsIgnoreCase(sortBy)) {
            return libraryList.stream()
                    .sorted((l1, l2) -> l1.getPublicationDate().compareTo(l2.getPublicationDate()))
                    .collect(Collectors.toList());
        } else { // Default to sorting by title
            return libraryList.stream()
                    .sorted((l1, l2) -> l1.getTitle().compareToIgnoreCase(l2.getTitle()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public void addResource(LibraryDto libraryDTO) throws InvalidInputException, ResourceCreationException {
        try {
            Library library = new Library(libraryDTO.getTitle(), libraryDTO.getAuthor(), BookType.valueOf(libraryDTO.getType()), LocalDate.parse(libraryDTO.getPublicationDate()), Genre.valueOf(libraryDTO.getGenre()), libraryDTO.getIsbn(), AvailabilityStatus.valueOf(libraryDTO.getAvailabilityStatus()));
            save(library);
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid input provided: " + e.getMessage());
        } catch (Exception e) {
            throw new ResourceCreationException("Error creating resource: " + e.getMessage());
        }
    }

    Library save(Library library) {
        return libraryRepo.save(library);
    }

    @Override
    public void updateAvailabilityStatus(Long id, AvailabilityStatus availabilityStatus) throws ResourceNotFoundException, InvalidInputException, ResourceCreationException {
        try {
            Library library = libraryRepo.findById(id.intValue()).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
            library.setAvailabilityStatus(availabilityStatus);
            save(library);
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("Invalid input provided: " + e.getMessage());
        } catch (Exception e) {
            throw new ResourceCreationException("Error creating resource: " + e.getMessage());
        }
    }
}