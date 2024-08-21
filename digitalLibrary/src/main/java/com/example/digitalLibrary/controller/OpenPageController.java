package com.example.digitalLibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OpenPageController {

    @GetMapping("openAddResources")
    public String openAddResources() {
        return "addResources.html";
    }

    @GetMapping("updateResource")
    public String openUpdateResource() {
        return "updateResource.html";
    }
}
