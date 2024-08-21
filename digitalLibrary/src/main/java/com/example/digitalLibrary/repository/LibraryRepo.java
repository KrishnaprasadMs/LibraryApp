package com.example.digitalLibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.digitalLibrary.entity.Library;

public interface LibraryRepo extends JpaRepository<Library, Integer>{

	
}
