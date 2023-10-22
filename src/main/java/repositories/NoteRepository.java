package repositories;

import model.Note;

import java.util.List;
import java.util.Map;

public interface NoteRepository {
    Map<String, Note> getAllNotes();

    Note getNoteById(String id) throws NoteDoesNotExistException;

    void addNote(Note note);

    void updateNote(String id, Note note) throws NoteDoesNotExistException;

    void deleteNote(String id) throws NoteDoesNotExistException;

    void deleteAllNotes();

    void deleteAllNotesByUserId(String userId);

    List<Note> getAllNotesByUserId(String userId);

    class NoteDoesNotExistException extends Exception {
        public NoteDoesNotExistException(String message) {
            super(message);
        }
    }
}
