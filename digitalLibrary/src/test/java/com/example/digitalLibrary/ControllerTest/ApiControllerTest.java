package com.example.digitalLibrary.ControllerTest;

import com.example.digitalLibrary.Exception.InvalidInputException;
import com.example.digitalLibrary.Exception.ResourceCreationException;
import com.example.digitalLibrary.Exception.ResourceNotFoundException;
import com.example.digitalLibrary.controller.ApiController;
import com.example.digitalLibrary.dto.LibraryDto;
import com.example.digitalLibrary.entity.BookType;
import com.example.digitalLibrary.entity.Genre;
import com.example.digitalLibrary.entity.Library;
import com.example.digitalLibrary.service.LibraryService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApiControllerTest {

    @InjectMocks
    private ApiController apiController;

    @Mock
    private LibraryService libraryService;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() throws IOException {
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void getResourcesByType_ShouldReturnData() throws IOException {

        String type = "EBOOK";
        List<Library> libraries = new ArrayList<>();
        libraries.add(new Library());
        when(libraryService.getResourcesByType(BookType.EBOOK)).thenReturn(libraries);
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        apiController.getResourcesByType(type, response);


        verify(response).setStatus(HttpServletResponse.SC_OK);

    }

    @Test
    void getResourcesByType_InvalidType_ShouldReturnNotFound() throws IOException {

        String type = "INVALID_TYPE";
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        apiController.getResourcesByType(type, response);


        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);

    }

    @Test
    void getResourcesPublishedAfter_ShouldReturnData() throws IOException {

        String publishedAfter = LocalDate.now().minusDays(1).toString();
        List<Library> libraries = new ArrayList<>();
        libraries.add(new Library()); // Add a mock library object
        when(libraryService.getResourcesPublishedAfter(any(LocalDate.class))).thenReturn(libraries);
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        apiController.getResourcesPublishedAfter(publishedAfter, response);


        verify(response).setStatus(HttpServletResponse.SC_OK);

    }

    @Test
    void getResourcesPublishedAfter_InvalidDateFormat_ShouldReturnNotFound() throws IOException {

        String publishedAfter = "invalid-date";
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        apiController.getResourcesPublishedAfter(publishedAfter, response);

        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);

    }

    @Test
    void getResourcesByGenre_ShouldReturnData() throws IOException {
        String genre = "FICTION";
        List<Library> libraries = new ArrayList<>();
        libraries.add(new Library()); // Add a mock library object
        when(libraryService.getResourcesByGenre(Genre.FICTION)).thenReturn(libraries);
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        apiController.getResourcesByGenre(genre, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);

    }

    @Test
    void getResourcesByGenre_InvalidGenre_ShouldReturnNotFound() throws IOException {

        String genre = "INVALID_GENRE";
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        apiController.getResourcesByGenre(genre, response);

        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);

    }

    @Test
    void getSortedResources_ShouldReturnData() throws IOException {

        String sortBy = "HISTORY";
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        apiController.getSortedResources(sortBy, response);

        verify(response).setStatus(HttpServletResponse.SC_OK);

    }

    @Test
    void getSortedResources_InvalidSortBy_ShouldReturnNotFound() throws IOException {

        String sortBy = "INVALID_SORT";
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));

        apiController.getSortedResources(sortBy, response);


        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);

    }

    @Test
    void addResource_ShouldAddDataSuccessfully() throws IOException, InvalidInputException, ResourceCreationException {

        LibraryDto libraryDTO = new LibraryDto();
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        doNothing().when(libraryService).addResource(libraryDTO);


        apiController.addResource(libraryDTO, response);


        verify(response).setStatus(HttpServletResponse.SC_OK);

    }

    @Test
    void addResource_ErrorInAdding_ShouldReturnBadRequest() throws IOException, InvalidInputException, ResourceCreationException {

        LibraryDto libraryDTO = new LibraryDto();
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        doThrow(new RuntimeException("Error adding resource")).when(libraryService).addResource(libraryDTO);

        apiController.addResource(libraryDTO, response);
        verify(response).setStatus(HttpServletResponse.SC_BAD_REQUEST);

    }

    @Test
    void updateAvailabilityStatus_ShouldUpdateSuccessfully() throws InvalidInputException, ResourceNotFoundException, ResourceCreationException {

        Long resourceId = 1L;
        String status = "AVAILABLE";

        ResponseEntity<String> responseEntity = apiController.updateAvailabilityStatus(resourceId, status);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Availability status updated successfully.", responseEntity.getBody());
    }

    @Test
    void updateAvailabilityStatus_InvalidStatus_ShouldReturnBadRequest() {

        Long resourceId = 1L;
        String status = "INVALID_STATUS";


        ResponseEntity<String> responseEntity = apiController.updateAvailabilityStatus(resourceId, status);


        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid availability status provided.", responseEntity.getBody());
    }
}