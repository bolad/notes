package com.example.easynotes.service;

import com.example.easynotes.model.Notes;
import com.example.easynotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    // create a note
    public Notes createNote(Notes note){
        return noteRepository.save(note);
    }

    // search all notes
    public List<Notes> getAllNotes(){
        return noteRepository.findAll();
    }

    //get note by id
    public Optional<Notes> getNoteById(Long noteId){
        return noteRepository.findById(noteId);
    }

    public void delete(Notes note){
        noteRepository.delete(note);

    }

    //find first note ordered by last created date
    public Notes findFirstNote(){
        return noteRepository.findFirstByOrderByCreatedAtDesc();
    }


}
