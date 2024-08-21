package com.example.digitalLibrary.service;

import java.time.LocalDate;
import java.util.List;

import com.example.digitalLibrary.Exception.InvalidInputException;
import com.example.digitalLibrary.Exception.ResourceCreationException;
import com.example.digitalLibrary.Exception.ResourceNotFoundException;
import com.example.digitalLibrary.dto.LibraryDto;
import com.example.digitalLibrary.entity.AvailabilityStatus;
import com.example.digitalLibrary.entity.BookType;
import com.example.digitalLibrary.entity.Genre;
import com.example.digitalLibrary.entity.Library;

public interface LibraryService {
	
	List<Library> getAllLibrary();

	List<Library> getFilteredAndSortedLibrary(String type, LocalDate publishedAfterDate, String genre, String sortBy);

	List<Library> getResourcesByType(BookType bookType);

	List<Library> getResourcesPublishedAfter(LocalDate publishedAfterDate);

	List<Library> getResourcesByGenre(Genre bookGenre);

	List<Library> getSortedResources(String sortBy);

	void addResource(LibraryDto libraryDTO) throws InvalidInputException, ResourceCreationException;

	void updateAvailabilityStatus(Long id, AvailabilityStatus availabilityStatus) throws ResourceNotFoundException, InvalidInputException, ResourceCreationException;
}
