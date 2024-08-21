package com.example.digitalLibrary.ServiceTest;

import com.example.digitalLibrary.Exception.InvalidInputException;
import com.example.digitalLibrary.Exception.ResourceCreationException;
import com.example.digitalLibrary.Exception.ResourceNotFoundException;
import com.example.digitalLibrary.dto.LibraryDto;
import com.example.digitalLibrary.entity.AvailabilityStatus;
import com.example.digitalLibrary.entity.BookType;
import com.example.digitalLibrary.entity.Genre;
import com.example.digitalLibrary.entity.Library;
import com.example.digitalLibrary.repository.LibraryRepo;
import com.example.digitalLibrary.service.LibraryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceImplTest {

    @InjectMocks
    private LibraryServiceImpl libraryService;

    @Mock
    private LibraryRepo libraryRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllLibrary_ShouldReturnAllLibraries() {

        List<Library> libraries = new ArrayList<>();
        libraries.add(new Library("Title1", "Author1", BookType.EBOOK, LocalDate.now(), Genre.FICTION, "1234567890123", AvailabilityStatus.AVAILABLE));
        libraries.add(new Library("Title2", "Author2", BookType.AUDIO_BOOK, LocalDate.now(), Genre.NON_FICTION, "9876543210123", AvailabilityStatus.CHECKED_OUT));
        when(libraryRepo.findAll()).thenReturn(libraries);

        List<Library> result = libraryService.getAllLibrary();

        assertEquals(2, result.size());
        verify(libraryRepo).findAll();
    }

    @Test
    void getFilteredAndSortedLibrary_ShouldFilterAndSort() {

        List<Library> libraries = new ArrayList<>();
        libraries.add(new Library("Title1", "Author1", BookType.EBOOK, LocalDate.now().minusDays(1), Genre.FICTION, "1234567890123", AvailabilityStatus.AVAILABLE));
        libraries.add(new Library("Title2", "Author2", BookType.EBOOK, LocalDate.now().minusDays(2), Genre.NON_FICTION, "9876543210123", AvailabilityStatus.CHECKED_OUT));
        when(libraryRepo.findAll()).thenReturn(libraries);


        List<Library> result = libraryService.getFilteredAndSortedLibrary("EBOOK", LocalDate.now().minusDays(3), "FICTION", "publication_date");


        assertEquals(1, result.size());
        assertEquals("Title1", result.get(0).getTitle());
    }

    @Test
    void getResourcesByType_ShouldReturnFilteredResources() {

        List<Library> libraries = new ArrayList<>();
        libraries.add(new Library("Title1", "Author1", BookType.EBOOK, LocalDate.now(), Genre.FICTION, "1234567890123", AvailabilityStatus.AVAILABLE));
        when(libraryRepo.findAll()).thenReturn(libraries);

        List<Library> result = libraryService.getResourcesByType(BookType.EBOOK);

        assertEquals(1, result.size());
        assertEquals("Title1", result.get(0).getTitle());
    }

    @Test
    void getResourcesPublishedAfter_ShouldReturnFilteredResources() {

        List<Library> libraries = new ArrayList<>();
        libraries.add(new Library("Title1", "Author1", BookType.EBOOK, LocalDate.now().minusDays(1), Genre.FICTION, "1234567890123", AvailabilityStatus.AVAILABLE));
        when(libraryRepo.findAll()).thenReturn(libraries);

        List<Library> result = libraryService.getResourcesPublishedAfter(LocalDate.now().minusDays(2));

        assertEquals(1, result.size());
        assertEquals("Title1", result.get(0).getTitle());
    }

    @Test
    void getResourcesByGenre_ShouldReturnFilteredResources() {
        List<Library> libraries = new ArrayList<>();
        libraries.add(new Library("Title1", "Author1", BookType.EBOOK, LocalDate.now(), Genre.FICTION, "1234567890123", AvailabilityStatus.AVAILABLE));
        when(libraryRepo.findAll()).thenReturn(libraries);


        List<Library> result = libraryService.getResourcesByGenre(Genre.FICTION);

        assertEquals(1, result.size());
        assertEquals("Title1", result.get(0).getTitle());
    }

    @Test
    void addResource_ShouldAddLibrarySuccessfully() throws InvalidInputException, ResourceCreationException {

        LibraryDto libraryDTO = new LibraryDto();
        libraryDTO.setTitle("New Book");
        libraryDTO.setAuthor("New Author");
        libraryDTO.setType("EBOOK");
        libraryDTO.setPublicationDate(LocalDate.now().toString());
        libraryDTO.setGenre("FICTION");
        libraryDTO.setIsbn("1234567890123");
        libraryDTO.setAvailabilityStatus("AVAILABLE");


        libraryService.addResource(libraryDTO);


        verify(libraryRepo).save(any(Library.class)); // Verify that save was called
    }

    @Test
    void addResource_InvalidInput_ShouldThrowInvalidInputException() {

        LibraryDto libraryDTO = new LibraryDto();
        libraryDTO.setTitle("New Book");
        libraryDTO.setAuthor("New Author");
        libraryDTO.setType("INVALID_TYPE"); // Invalid type to trigger exception


        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            libraryService.addResource(libraryDTO);
        });
        assertEquals("Invalid input provided: No enum constant com.example.digitalLibrary.entity.BookType.INVALID_TYPE", exception.getMessage());
    }

    @Test
    void updateAvailabilityStatus_ShouldUpdateSuccessfully() throws ResourceNotFoundException, InvalidInputException, ResourceCreationException {

        Long resourceId = 1L;
        AvailabilityStatus availabilityStatus = AvailabilityStatus.AVAILABLE;
        Library library = new Library("Title1", "Author1", BookType.EBOOK, LocalDate.now(), Genre.FICTION, "1234567890123", AvailabilityStatus.CHECKED_OUT);
        when(libraryRepo.findById(resourceId.intValue())).thenReturn(Optional.of(library));


        libraryService.updateAvailabilityStatus(resourceId, availabilityStatus);


        assertEquals(AvailabilityStatus.AVAILABLE, library.getAvailabilityStatus());
        verify(libraryRepo).save(library);
    }

    @Test
    void updateAvailabilityStatus_ResourceNotFound_ShouldThrowResourceNotFoundException() {

        Long resourceId = 1L;
        AvailabilityStatus availabilityStatus = AvailabilityStatus.AVAILABLE;

        ResourceCreationException exception = assertThrows(ResourceCreationException.class, () -> {
            libraryService.updateAvailabilityStatus(resourceId, availabilityStatus);
        });
        assertEquals("Error creating resource: Resource not found", exception.getMessage());
    }
}
