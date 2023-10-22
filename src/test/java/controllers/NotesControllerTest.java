package controllers;

import model.Note;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import repositories.impl.NoteRepositoryImpl;
import services.NoteService;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NotesControllerTest {

    private static NotesController notesController;

    @BeforeAll
    static void setUp() {
        notesController = new NotesController(new NoteService(new NoteRepositoryImpl()));
    }

    @AfterEach
    void tearDown() {
        notesController.deleteAll();
    }

    @Test
    void findAll() {
        assertEquals(0, notesController.findAll().size());

        Note note = new Note ("title", "content", "userId");
        notesController.add(note);

        assertEquals(1, notesController.findAll().size());
        assertEquals(note, notesController.findAll().get("1"));
    }

    @Test
    void findById() {
        Note note = new Note ("title", "content", "userId");
        notesController.add(note);

        assertEquals(note, notesController.findById("1"));
    }

    @Test
    void findAllByUserId() {
        assertEquals(0, notesController.findAllByUserId("userId").size());

        Note note = new Note ("title", "content", "userId");
        notesController.add(note);

        assertEquals(1, notesController.findAllByUserId("userId").size());
        assertEquals(note, notesController.findAllByUserId("userId").iterator().next());

        Note note2 = new Note ("title2", "content2", "userId");
        notesController.add(note2);

        assertEquals(2, notesController.findAllByUserId("userId").size());
        assertEquals(note2, notesController.findAllByUserId("userId").stream().toList().get(1));
    }

    @Test
    void add() {
        assertEquals(0, notesController.findAll().size());

        Note note = new Note ("title", "content", "userId");
        notesController.add(note);

        assertEquals(1, notesController.findAll().size());
        assertEquals(note, notesController.findAll().get("1"));
    }

    @Test
    void update() {
        Note note = new Note ("title", "content", "userId");
        notesController.add(note);

        assertEquals(note, notesController.findById("1"));

        Note note2 = new Note ("title2", "content2", "userId");
        notesController.update("1", note2);

        assertEquals(note2, notesController.findById("1"));
    }

    @Test
    void delete() {
        Note note = new Note ("title", "content", "userId");
        notesController.add(note);

        assertEquals(1, notesController.findAll().size());

        notesController.delete("1");

        assertEquals(0, notesController.findAll().size());
    }

    @Test
    void deleteAll() {
        assertEquals(0, notesController.findAll().size());

        Note note = new Note ("title", "content", "userId");
        Note note2 = new Note ("title", "content", "userId");
        notesController.add(note);
        notesController.add(note2);

        assertEquals(2, notesController.findAll().size());

        notesController.deleteAll();

        note.setId(null);
        notesController.add(note);
        assertEquals(1, notesController.findAll().size());

        notesController.deleteAll();

        assertEquals(0, notesController.findAll().size());
    }

    @Test
    void deleteAllByUserId() {
        assertEquals(0, notesController.findAll().size());

        Note note = new Note ("title", "content", "userId");
        Note note2 = new Note ("title", "content", "userId2");
        notesController.add(note);
        notesController.add(note2);

        assertEquals(2, notesController.findAll().size());

        notesController.deleteAllByUserId("userId");

        assertEquals(1, notesController.findAll().size());
        assertEquals(note2, notesController.findAll().get("2"));
    }
}