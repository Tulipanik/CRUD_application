package repositories.impl;

import lombok.extern.slf4j.Slf4j;
import model.Note;
import org.springframework.stereotype.Component;
import repositories.NoteRepository;

import java.util.*;

@Component
@Slf4j
public class NoteRepositoryImpl implements NoteRepository {
    private final Map<String, Note> notes = new HashMap<>();
    private Integer id = 1;
    private static final String NODE_NOT_FOUND_MSG = "Note with id %s does not exist";

    /**
     * This method returns all the notes
     *
     * @return a map of all the notes
     */
    @Override
    public Map<String, Note> getAllNotes() {
        return notes;
    }

    /**
     * This method returns a note by its id
     *
     * @param id the id of the note
     * @return the note with the given id
     * @throws NoteDoesNotExistException if the note does not exist
     */
    @Override
    public Note getNoteById(String id) throws NoteDoesNotExistException {
        if(!notes.containsKey(id)) {
            throw new NoteDoesNotExistException(NODE_NOT_FOUND_MSG.formatted(id));
        }

        return notes.get(id);
    }

    /**
     * This method adds a note
     *
     * @param note the note to be added
     */
    @Override
    public void addNote(Note note) {
        note.setId(id.toString());
        notes.put(id.toString(), note);
        id++;
    }

    /**
     * This method updates a note
     *
     * @param note the note to be updated
     * @throws NoteDoesNotExistException if the note does not exist
     */
    @Override
    public void updateNote(String id, Note note) throws NoteDoesNotExistException {
        if(!notes.containsKey(id)) {
            throw new NoteDoesNotExistException(NODE_NOT_FOUND_MSG.formatted(id));
        }

        notes.get(id).updateNote(note);
    }

    /**
     * This method deletes a note by its id
     *
     * @param id the id of the note
     * @throws NoteDoesNotExistException if the note does not exist
     */
    @Override
    public void deleteNote(String id) throws NoteDoesNotExistException{
        if(!notes.containsKey(id)) {
            throw new NoteDoesNotExistException(NODE_NOT_FOUND_MSG.formatted(id));
        }

        notes.remove(id);
    }

    /**
     * This method deletes all the notes
     */
    @Override
    public void deleteAllNotes() {
        notes.clear();
        id = 1;
    }

    /**
     * This method deletes all the notes that belong to a user
     *
     * @param userId the id of the user
     */
    @Override
    public void deleteAllNotesByUserId(String userId) {
        var notesToBeDeleted = notes.values().stream()
                .filter(note -> note.getUserId().equals(userId)).toList();

        notesToBeDeleted.forEach(note -> notes.remove(note.getId()));
    }

    /**
     * This method returns all the notes that belong to a user
     *
     * @param userId the id of the user
     * @return a list of notes that belong to the user
     */
    @Override
    public List<Note> getAllNotesByUserId(String userId) {
        return notes.values().stream()
                .filter(note -> note.getUserId().equals(userId))
                .toList();
    }

}
