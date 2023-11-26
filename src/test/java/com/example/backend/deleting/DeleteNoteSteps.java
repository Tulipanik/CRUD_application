package com.example.backend.deleting;

import com.example.backend.model.Note;
import io.restassured.RestAssured;
import lombok.extern.slf4j.Slf4j;
import io.restassured.response.Response;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DeleteNoteSteps extends Steps {
    private String apiUrl;
    private Response response;
    private List<Note> notes;

    @Given("I have api endpoint \"$apiUrl\" and in the database I have $notesCount notes")
    public void givenIHaveApiEndpointAndNotes(String apiUrl, int notesCount) {
        log.info("API URL: {}", apiUrl);
        log.info("Notes count: {}", notesCount);
        this.apiUrl = apiUrl;

        for(int i = 0; i < notesCount; i++) {
            RestAssured.given()
                    .contentType("application/json")
                    .body("""
                            {
                                "title": "Dummy note",
                                "content": "This is my dummy note",
                                "userId": "1"
                            }
                            """)
                    .post(apiUrl);
        }

        notes = RestAssured.given()
                .contentType("application/json")
                .get(apiUrl)
                .jsonPath()
                .getList(".", Note.class);
    }

    @Given("I DELETE $deleteNoteId note")
    public void deleteNoteWithGivenId(String deleteNoteId) {
        var deleteNoteActualId = GetNoteId(deleteNoteId);
        log.info("Deleting note: {}", deleteNoteActualId);
        RestAssured.given()
                .contentType("application/json")
                .delete(apiUrl + "/" + deleteNoteActualId);
    }

    @When("I send DELETE request for $noteId note")
    public void whenISendDeleteRequestForNoteId(String noteId) {
        var noteActualId = GetNoteId(noteId);
        log.info("Note id: {}", noteActualId);
        this.response = RestAssured.given()
                .contentType("application/json")
                .delete(apiUrl + "/" + noteActualId);
        log.info("Response: {}", this.response.asString());
    }

    @Then("response status code should be $statusCode")
    public void thenResponseStatusShouldBe(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @AfterScenario
    public void clearDatabase() throws Exception {
        this.response = RestAssured.given()
                .contentType("application/json")
                .delete(apiUrl);
    }

    private String GetNoteId(String index) {
        var parsedId = Integer.parseInt(index);
        return parsedId < 0 || parsedId > notes.size() ? index : notes.get(parsedId).getId();
    }
}
