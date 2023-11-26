package com.example.backend.groupdeleteing;

import com.example.backend.model.Note;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;

import java.util.List;

@Slf4j
public class DeleteGroupNoteSteps extends Steps {
    private String apiUrl;
    private Response response;

    @Given("I have api endpoint \"$apiUrl\" and in the database I have $notesCount notes assigned to user $userId")
    public void givenIHaveApiEndpointAndNotesWithAssignedUserId(String apiUrl, int notesCount, String userId) {
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
                                "userId": "%s"
                            }
                            """.formatted(userId))
                    .post(apiUrl);
        }
    }

    @Given("I DELETE all notes for user $userId")
    public void deleteAllNotesForGivenUser(String userId) {
        log.info("Deleting notes for user: {}", userId);
        this.response = RestAssured.given()
                .contentType("application/json")
                .delete(apiUrl + "/" + userId);
    }

    @When("I send DELETE request for user $userId")
    public void whenISendDeleteRequestForGivenUserId(String userId) {
        apiUrl = apiUrl + "/" + userId;
        log.info("User id: {}", userId);
        this.response = RestAssured.given()
                .contentType("application/json")
                .delete(apiUrl);
        log.info("Response: {}", response.asString());
    }

    @Then("response status code should be $statusCode and remaining notes should be $notesRemaining")
    public void thenResponseStatusShouldBeAndNotesRemainingShouldBe(int statusCode, int notesRemaining) {
        response.then().statusCode(statusCode);
        // TODO check count
    }
}
