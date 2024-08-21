package com.example.digitalLibrary.ControllerTest;

import com.example.digitalLibrary.controller.OpenPageController;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
public class OpenPageControllerTest {

    private OpenPageController openPageController = new OpenPageController();

    @InjectMocks
    private OpenPageController _openPageController;

    @Mock
    private HttpServletResponse response;


    public OpenPageControllerTest() {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void openAddResourcesTest() {
        String pageName = _openPageController.openAddResources();
        Assertions.assertEquals("addResources.html", pageName);
    }

    @Test
    void openUpdateResourceTest() {
        String pageName = _openPageController.openUpdateResource();
        Assertions.assertEquals("updateResource.html", pageName);
    }

}
