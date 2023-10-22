package services;

import lombok.extern.slf4j.Slf4j;
import model.Note;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import repositories.impl.NoteRepositoryImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class NoteServiceTest {

    private static NoteService noteService;

    @BeforeAll
    public static void setUp() {
        noteService = new NoteService(new NoteRepositoryImpl());
    }

    @AfterEach
    public void tearDown() {
        noteService.deleteAll();
    }

    @Test
    void test_findAll() {
        assertEquals(0, noteService.findAll().size());

        noteService.add(new Note("1", "1", "title"));
        assertEquals(1, noteService.findAll().size());

        noteService.add(new Note("2", "2", "title"));
        assertEquals(2, noteService.findAll().size());

        var notes = noteService.findAll();
        assertEquals(2, notes.size());
        assertEquals("1", notes.values().stream().toList().get(0).getId());
        assertEquals("2", notes.values().stream().toList().get(1).getId());
    }

    @Test
    void findById() {
        assertEquals(0, noteService.findAll().size());

        Note note1 = new Note ("1", "1", "title");
        Note note2 = new Note ("2", "2", "title");

        noteService.add(note1);
        assertEquals(note1, noteService.findById("1"));

        noteService.add(note2);
        assertEquals(note2, noteService.findById("2"));

        assertThrows(ResponseStatusException.class, () -> noteService.findById("3"));

        assertEquals(2, noteService.findAll().size());
    }

    @Test
    void add() {
        assertEquals(0, noteService.findAll().size());

        Note note1 = new Note ("1", "1", "title");
        Note note2 = new Note ("2", "2", "title");

        noteService.add(note1);
        assertEquals(note1, noteService.findById("1"));

        noteService.add(note2);
        assertEquals(note2, noteService.findById("2"));

        assertEquals(2, noteService.findAll().size());
    }

    @Test
    void update() {
        assertEquals(0, noteService.findAll().size());

        Note note1 = new Note ("1", "1", "title");

        noteService.add(note1);
        assertEquals(note1, noteService.findById("1"));

        Note note3 = new Note ("3", "3", "title");
        note3.setId("1");
        noteService.update("1", note3);

        assertThrows(ResponseStatusException.class, () -> noteService.update("10", note1));

        assertEquals(note3, noteService.findById("1"));
    }

    @Test
    void delete() {
        assertEquals(0, noteService.findAll().size());

        Note note1 = new Note ("1", "1", "title");

        noteService.add(note1);
        assertEquals(note1, noteService.findById("1"));

        noteService.delete("1");
        assertEquals(0, noteService.findAll().size());

        assertThrows(ResponseStatusException.class, () -> noteService.delete("1"));
    }

    @Test
    void deleteAll() {
        assertEquals(0, noteService.findAll().size());

        Note note1 = new Note ("1", "1", "title");
        Note note2 = new Note ("2", "2", "title");

        noteService.add(note1);
        assertEquals(note1, noteService.findById("1"));

        noteService.add(note2);
        assertEquals(note2, noteService.findById("2"));

        assertEquals(2, noteService.findAll().size());

        noteService.deleteAll();
        assertEquals(0, noteService.findAll().size());
    }

    @Test
    void deleteAllByUserId() {
        assertEquals(0, noteService.findAll().size());

        Note note1 = new Note ("1", "1", "title");
        Note note2 = new Note ("2", "2", "title");
        Note note3 = new Note ("3", "3", "title_3");

        noteService.add(note1);
        assertEquals(note1, noteService.findById("1"));

        noteService.add(note2);
        assertEquals(note2, noteService.findById("2"));

        noteService.add(note3);
        assertEquals(note3, noteService.findById("3"));

        assertEquals(3, noteService.findAll().size());

        noteService.deleteAllByUserId("title");
        assertEquals(1, noteService.findAll().size());
    }

    @Test
    void findAllByUserId() {
        assertEquals(0, noteService.findAll().size());

        Note note1 = new Note ("1", "1", "title");
        Note note2 = new Note ("2", "2", "title");

        noteService.add(note1);
        assertEquals(note1, noteService.findById("1"));

        noteService.add(note2);
        assertEquals(note2, noteService.findById("2"));

        assertEquals(2, noteService.findAll().size());

        assertEquals(2, noteService.findAllByUserId("title").size());
        assertEquals(0, noteService.findAllByUserId("title1").size());
    }
}