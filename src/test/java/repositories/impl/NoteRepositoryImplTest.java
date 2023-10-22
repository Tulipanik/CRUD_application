package repositories.impl;

import model.Note;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repositories.NoteRepository;

import static org.junit.jupiter.api.Assertions.*;

class NoteRepositoryImplTest {

    private static NoteRepositoryImpl noteRepository;

    @BeforeAll
    static void setUp() {
        noteRepository = new NoteRepositoryImpl();
    }

    @AfterEach
    void tearDown() {
        noteRepository.deleteAllNotes();
    }

    @Test
    void getAllNotes() {
        assertEquals(0, noteRepository.getAllNotes().size());

        Note note = new Note("title", "content", "userId");
        Note note2 = new Note("title2", "content2", "userId");

        noteRepository.addNote(note);
        assertEquals(1, noteRepository.getAllNotes().size());

        noteRepository.addNote(note2);

        assertEquals(2, noteRepository.getAllNotes().size());
        assertEquals(note, noteRepository.getAllNotes().get("1"));
        assertEquals(note2, noteRepository.getAllNotes().get("2"));
    }

    @Test
    void getNoteById() {
        Note note = new Note("title", "content", "userId");
        note.setId("1");

        noteRepository.addNote(note);
        try {
            assertEquals(note, noteRepository.getNoteById("1"));
        } catch (NoteRepository.NoteDoesNotExistException e) {
            fail("Note should exist");
        }

        assertThrows(NoteRepository.NoteDoesNotExistException.class, () -> noteRepository.getNoteById("2"));
    }

    @Test
    void addNote() {
        assertEquals(0, noteRepository.getAllNotes().size());

        Note note = new Note("title", "content", "userId");
        note.setId("1");
        noteRepository.addNote(note);

        assertEquals(1, noteRepository.getAllNotes().size());
        assertEquals(note, noteRepository.getAllNotes().get("1"));
    }

    @Test
    void updateNote() {
        Note note = new Note("title", "content", "userId");
        note.setId("1");
        Note note2 = new Note("title2", "content2", "userId");

        noteRepository.addNote(note);
        try {
            noteRepository.updateNote("1", note2);
        } catch (NoteRepository.NoteDoesNotExistException e) {
            fail("Note should exist");
        }

        assertEquals("title2", noteRepository.getAllNotes().get("1").getTitle());
        assertEquals("content2", noteRepository.getAllNotes().get("1").getContent());

        assertThrows(NoteRepository.NoteDoesNotExistException.class, () -> noteRepository.updateNote("2", note2));
    }

    @Test
    void deleteNote() {
        Note note = new Note("title", "content", "userId");
        note.setId("1");

        noteRepository.addNote(note);
        try {
            noteRepository.deleteNote("1");
        } catch (NoteRepository.NoteDoesNotExistException e) {
            fail("Note should exist");
        }

        assertEquals(0, noteRepository.getAllNotes().size());

        assertThrows(NoteRepository.NoteDoesNotExistException.class, () -> noteRepository.deleteNote("2"));
    }

    @Test
    void deleteAllNotes() {
        assertEquals(0, noteRepository.getAllNotes().size());

        Note note = new Note("title", "content", "userId");
        Note note2 = new Note("title", "content", "userId");
        noteRepository.addNote(note);
        noteRepository.addNote(note2);

        assertEquals(2, noteRepository.getAllNotes().size());

        noteRepository.deleteAllNotes();

        note.setId(null);
        noteRepository.addNote(note);
        assertEquals(1, noteRepository.getAllNotes().size());

        noteRepository.deleteAllNotes();

        assertEquals(0, noteRepository.getAllNotes().size());
    }

    @Test
    void deleteAllNotesByUserId() {
        assertEquals(0, noteRepository.getAllNotes().size());

        Note note = new Note ("title", "content", "userId");
        Note note2 = new Note ("title", "content", "userId2");
        noteRepository.addNote (note);
        noteRepository.addNote (note2);

        assertEquals(2, noteRepository.getAllNotes().size());

        noteRepository.deleteAllNotesByUserId("userId");

        assertEquals(1, noteRepository.getAllNotes().size());
        assertEquals(note2, noteRepository.getAllNotes().get("2"));
    }

    @Test
    void getAllNotesByUserId() {
        assertEquals(0, noteRepository.getAllNotes().size());

        Note note = new Note ("title", "content", "userId");
        Note note2 = new Note ("title", "content", "userId2");
        noteRepository.addNote (note);
        noteRepository.addNote (note2);

        assertEquals(2, noteRepository.getAllNotes().size());

        assertEquals(1, noteRepository.getAllNotesByUserId("userId").size());
        assertEquals(note, noteRepository.getAllNotesByUserId("userId").get(0));
    }
}