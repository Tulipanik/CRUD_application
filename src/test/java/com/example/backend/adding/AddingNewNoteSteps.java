package com.example.backend.adding;

import com.example.backend.controllers.NotesController;
import com.example.backend.dto.NoteDto;
import com.example.backend.mappings.NoteDtoMapper;
import com.example.backend.model.Note;
import lombok.extern.slf4j.Slf4j;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Pending;
import org.jbehave.core.annotations.When;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.steps.Steps;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AddingNewNoteSteps extends Steps {

  @Autowired
  private NotesController notesController;
  @Autowired
  private NoteDtoMapper noteDtoMapper;
  private NoteDto responseNote;

  @Given("Gdy w Bazie są trzy notatki")
  public void givenThreeNotesInDatabase() {
    for (int i = 0; i < 3; i++) {
      Note note = new Note();
      note.setTitle("Title " + i);
      note.setContent("Content " + i);
      note.setUserId("user-id");
      log.info("Adding note: {}", note);
      notesController.add(noteDtoMapper.toDto(note));
      log.info("Added note: {}", note);
    }

  }

  @When("Użytkownik doda nową notatkę z tytułem $title i treścią $content")
  public void whenIProvideContentAndTitle(String content, String title) {
    Note note = new Note();
    note.setTitle(title);
    note.setContent(content);
    responseNote = notesController.add(noteDtoMapper.toDto(note));
  }

  @Then("Nowa notatka zostanie zapisana w bazie danych")
  public void thenNoteIsSavedInDatabase() {
    try {
      NoteDto response = notesController.findById(responseNote.getId());
      Assert.assertNotNull(response);
    } catch (Exception e) {
      assert false;
    }
  }

  @Then("W bazie danych będzie cztery notatki")
  public void thenDatabaseHasFourNotes() {
    try {
      var response = notesController.findAll();
      Assert.assertEquals(4, response.size());
    } catch (Exception e) {
      assert false;
    }
  }
}

