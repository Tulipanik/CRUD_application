package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {

    @Test
    void testToString() {
        Note note = new Note("title", "content", "userId");
        note.setId("id");

        assertEquals("""
                        Title: title
                        Content: content
                        Creation Date:""" + " " + note.getCreationDate() + """
                        
                        ID: id
                        User ID: userId
                        """,
                note.toString());
    }

    @Test
    void updateNote() {
        Note note = new Note ("title", "content", "userId");
        Note note2 = new Note ("title2", "content2", "userId2");

        note.updateNote (note2);

        assertEquals("title2", note.getTitle());
        assertEquals("content2", note.getContent());
    }

    @Test
    void testEquals() {
        Note note = new Note ("title", "content", "userId");
        Note note2 = new Note ("title2", "content2", "userId2");
        Note note3 = new Note ("title", "content", "userId");

        note.setId("id");
        note2.setId("id2");
        note3.setId("id");

        assertNotEquals(note, note2);
        assertEquals(note, note3);
    }

    @Test
    void testHashCode() {
        Note note = new Note ("title", "content", "userId");
        Note note2 = new Note ("title2", "content2", "userId2");
        Note note3 = new Note ("title", "content", "userId");

        note.setId("id");
        note2.setId("id2");
        note3.setId("id");

        assertNotEquals(note.hashCode(), note2.hashCode());
        assertEquals(note.hashCode(), note3.hashCode());
    }

    @Test
    void getTitle() {
        Note note = new Note ("title", "content", "userId");
        assertEquals("title", note.getTitle());

        note.setTitle("title2");
        assertEquals("title2", note.getTitle());
    }

    @Test
    void getContent() {
        Note note = new Note ("title", "content", "userId");
        assertEquals("content", note.getContent());

        note.setContent("content2");
        assertEquals("content2", note.getContent());
    }

    @Test
    void getId() {
        Note note = new Note ("title", "content", "userId");
        assertNull(note.getId());

        note.setId("id");
        assertEquals("id", note.getId());
    }

    @Test
    void getCreationDate() {
        Note note = new Note ("title", "content", "userId");
        assertNotNull(note.getCreationDate());
    }

    @Test
    void getUserId() {
        Note note = new Note ("title", "content", "userId");
        assertEquals("userId", note.getUserId());
    }

    @Test
    void setTitle() {
        Note note = new Note ("title", "content", "userId");
        assertEquals("title", note.getTitle());
    }

    @Test
    void setContent() {
        Note note = new Note ("title", "content", "userId");
        assertEquals("content", note.getContent());
    }

    @Test
    void setId() {
        Note note = new Note ("title", "content", "userId");
        assertNull(note.getId());
    }
}