package services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Note;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import repositories.NoteRepository;

import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class NoteService {
    private final NoteRepository noteRepository;

    public Map<String, Note> findAll() {
        return noteRepository.getAllNotes();
    }

    public Note findById(String id) {
        try {
            return noteRepository.getNoteById(id);
        } catch (NoteRepository.NoteDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public void add(@NotNull Note note) {
        if(note.getId() != null && !note.getId().isEmpty()) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Id of Note should not be provided");
        }

        noteRepository.addNote(note);
    }

    public void update(String id, Note note) {
        try {
            noteRepository.updateNote(id, note);
        } catch (NoteRepository.NoteDoesNotExistException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public void delete(String id) {
        try {
            noteRepository.deleteNote(id);
        } catch (NoteRepository.NoteDoesNotExistException e) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public void deleteAll() {
        noteRepository.deleteAllNotes();
    }

    public void deleteAllByUserId(String userId) {
        noteRepository.deleteAllNotesByUserId(userId);
    }

    public Collection<Note> findAllByUserId(String userId) {
        return noteRepository.getAllNotesByUserId(userId);
    }
}
