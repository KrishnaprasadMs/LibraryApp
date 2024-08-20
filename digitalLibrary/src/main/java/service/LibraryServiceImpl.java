package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Library;
import repository.LibraryRepo;

@Service
public class LibraryServiceImpl implements LibraryService{
	@Autowired
	LibraryRepo libraryRepo ;
	
	@Override
	public List<Library> getAllLibrary() {
		
		return libraryRepo.findAll();
	}

}
