package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Library;

public interface LibraryRepo extends JpaRepository<Library, Integer>{

	
}
