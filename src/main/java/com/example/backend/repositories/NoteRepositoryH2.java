package com.example.backend.repositories;

import com.example.backend.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepositoryH2 extends JpaRepository<Note, String> {

}
