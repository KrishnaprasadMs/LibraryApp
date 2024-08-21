package com.example.digitalLibrary.controller;

import com.example.digitalLibrary.Exception.ResourceNotFoundException;
import com.example.digitalLibrary.dto.LibraryDto;
import com.example.digitalLibrary.entity.AvailabilityStatus;
import com.example.digitalLibrary.entity.BookType;
import com.example.digitalLibrary.entity.Genre;
import com.example.digitalLibrary.entity.Library;
import com.example.digitalLibrary.service.LibraryService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
@RequestMapping("api")
public class ApiController {

    Logger logger = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    LibraryService libraryService;


    @GetMapping("resources")
    public void viewLibrary(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String published_after,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false, defaultValue = "title") String sortBy,
            HttpServletResponse response) throws IOException {
        try {
            LocalDate publishedAfterDate = null;
            if (published_after != null) {
                publishedAfterDate = LocalDate.parse(published_after);
            }

            List<Library> libraryList = libraryService.getFilteredAndSortedLibrary(
                    type, publishedAfterDate, genre, sortBy);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Data fetched: " + libraryList.toString());
        } catch (DateTimeParseException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid date format for published_after. Use YYYY-MM-DD.");
            logger.error("Date parsing error: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Error in fetching data");
            logger.error(e.getMessage());
        }
    }

    @GetMapping("resources/type")
    public void getResourcesByType(@RequestParam String type, HttpServletResponse response) throws IOException {
        try {
            BookType bookType = BookType.valueOf(type);
            List<Library> libraryList = libraryService.getResourcesByType(bookType);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Data fetched: " + libraryList.toString());
        } catch (IllegalArgumentException | IOException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Error in fetching data");
            logger.error(e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Error in fetching data");
            logger.error(e.getMessage());
        }
    }


    @GetMapping("resources/published_after")
    public void getResourcesPublishedAfter(@RequestParam String published_after, HttpServletResponse response) throws IOException {
        try {
            LocalDate publishedAfterDate = LocalDate.parse(published_after);
            List<Library> libraryList = libraryService.getResourcesPublishedAfter(publishedAfterDate);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Data fetched: " + libraryList.toString());
        } catch (DateTimeParseException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Invalid date format for published_after. Use YYYY-MM-DD.");
            logger.error(e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Error in fetching data");
            logger.error(e.getMessage());
        }
    }

    @GetMapping("resources/genre")
    public void getResourcesByGenre(@RequestParam String genre, HttpServletResponse response) throws IOException {
        try {
            Genre bookGenre = Genre.valueOf(genre);
            List<Library> libraryList = libraryService.getResourcesByGenre(bookGenre);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Data fetched: " + libraryList.toString());
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Invalid genre provided.");
            logger.error(e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Error in fetching data");
            logger.error(e.getMessage());
        }
    }

    @GetMapping("resources/sorted")
    public void getSortedResources(
            @RequestParam(required = false, defaultValue = "title") String sortBy, HttpServletResponse response) throws IOException {
        try {
            Genre bookGenre = Genre.valueOf(sortBy);
            List<Library> libraryList = libraryService.getResourcesByGenre(bookGenre);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Data fetched: " + libraryList.toString());
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Invalid sort by provided");
            logger.error(e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Error in fetching data");
            logger.error(e.getMessage());
        }
    }

    @PostMapping("resources")
    public void addResource(@Valid LibraryDto libraryDTO, HttpServletResponse response) throws IOException {
        try {
            libraryService.addResource(libraryDTO);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("data added successfully");
        } catch (Exception e) {
            logger.error("Error adding resource: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Error In Adding Data");

        }
    }


    @PatchMapping("resources/{id}/availability")
    public ResponseEntity<String> updateAvailabilityStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            AvailabilityStatus availabilityStatus = AvailabilityStatus.valueOf(status);
            libraryService.updateAvailabilityStatus(id, availabilityStatus);
            return ResponseEntity.ok("Availability status updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid availability status provided.");
        } catch (ResourceNotFoundException e) {
            logger.error("Book with id= " + id + " not Found ");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Error updating availability status: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating availability status.");
        }
    }

}
