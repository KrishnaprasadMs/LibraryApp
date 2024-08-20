package entity;



import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Library {
	
	@Id
	private String id ;
	
	private String title ;
	
	
	private String author ;
	
	//(Enum: EBOOK, AUDIO_BOOK, RESEARCH_PAPER)
	
	private String type ;
	
	//(Enum: FICTION, NON_FICTION, SCIENCE_FICTION,SCIENCE, HISTORY, etc.)
	private String genre ;
	
	private Date DatepublicationDate ;
	
	
	private String isbn ;
	
	
	private String availabilityStatus ;
	//(Enum: AVAILABLE, CHECKED_OUT,UNDER_MAINTENANCE)
	
}
