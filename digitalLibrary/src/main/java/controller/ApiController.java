package controller;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.service.annotation.GetExchange;

import entity.Library;
import jakarta.servlet.http.HttpServletResponse;
import service.LibraryService;

@Controller
@RequestMapping("api")
public class ApiController {
	
	org.slf4j.Logger logger =  LoggerFactory.getLogger(ApiController.class) ;
	
	@Autowired
	LibraryService libraryService;
	
	@GetMapping("resources")
	public void viewLibrary(HttpServletResponse response) throws IOException {
		
		try {
			List<Library> LibraryList = libraryService.getAllLibrary();
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("data Fetched....");
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.getWriter().write("Error in Fetching Data");
			logger.error(e.getMessage());
		}
		
		
	}

}
