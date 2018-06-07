package com.example.easynotes.controller;

import java.util.List;

import javax.validation.Valid;

import com.example.easynotes.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Notes;
import com.example.easynotes.repository.NoteRepository;

@RestController
/*
 * @RestController annotation is a combination of Spring’s @Controller and @ResponseBody annotations.

   The @Controller annotation is used to define a controller and the @ResponseBody annotation is used to 
   indicate that the return value of a method should be used as the response body of the request.
 */
@RequestMapping("/api")
/*
 * declares that the url for all the apis in this controller will start with /api.
 */
public class NotesController {
	
	@Autowired
	NoteService noteService;

	//Create a new Note(POST/api/notes)
	@PostMapping("/notes")
	/*
	 * The @RequestBody annotation is used to bind the request body with a method parameter.

       The @Valid annotation makes sure that the request body is valid. Remember, we had marked
       Note’s title and content with @NotBlank annotation in the Note model?

	   If the request body doesn’t have a title or a content, then spring will return a 400 BadRequest
	   error to the client.
	 */
	public Notes createNote(@Valid @RequestBody Notes note) {
		return noteService.createNote(note);
	}

	//Get all Notes(GET/api/notes)
	@GetMapping("/notes")
	/*
	 *  The @GetMapping("/notes") annotation is a short form of 
	 *  @RequestMapping(value="/notes", method=RequestMethod.GET).
	 */
	public List<Notes> getAllNotes(){
		return noteService.getAllNotes();
	}

	//Get a single Note(GET/api/notes/{noteId})
	@GetMapping("/notes/{id}")
	/*
	 * The @PathVariable annotation is used to bind a path variable with a method parameter
	 */
	public Notes getNoteById(@PathVariable(value="id") Long noteId) {
		return noteService.getNoteById(noteId)
				.orElseThrow(() -> new ResourceNotFoundException("Note", "id",noteId));
	}

	//Get first note ordered by created date desc
	@GetMapping("/notes/first")
	public Notes getFirstNote(){
		return noteService.findFirstNote();
	}
	
	//Update a Note(PUT/api/notes/{noteId})
	@PutMapping("/notes/{id}")
	public Notes updateNote(@PathVariable(value="id") Long noteId, @Valid @RequestBody Notes noteDetails) {
		Notes note = noteService.getNoteById(noteId).orElseThrow
				(()-> new ResourceNotFoundException("Note", "id", noteId));
		
		note.setTitle(noteDetails.getTitle());
		note.setContent(noteDetails.getContent());
		note.setUpdatedAt(noteDetails.getUpdatedAt());
		
		Notes updatedNote = noteService.createNote(note);
		
		return updatedNote;
	}
	
	// Delete a Note(DELETE/api/{nodeId})
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long noteId) {
	    Notes note = noteService.getNoteById(noteId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", noteId));

	    noteService.delete(note);

	    return ResponseEntity.ok().build();
	}

	
	

}
