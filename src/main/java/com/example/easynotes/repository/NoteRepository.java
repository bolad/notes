package com.example.easynotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.easynotes.model.Notes;

import java.util.List;

@Repository
/*
 * Spring Data JPA has got us covered here. It comes with a JpaRepository interface which 
 * defines methods for all the CRUD operations on the entity, and a default implementation
 *  of JpaRepository called SimpleJpaRepository.
 *  
 *  @Repository annotation tells Spring to bootstrap the repository during component scan.
 */
public interface NoteRepository extends JpaRepository<Notes, Long> {

    Notes findFirstByOrderByCreatedAtDesc();

    List<Notes> findAllByOrOrderByCreatedAtDesc();

}
