package com.example.backend.deleting;

import static org.hamcrest.Matchers.containsString;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import java.util.regex.Pattern;

@Slf4j
public class DeleteAllNotesFromGroupSteps extends Steps {

    private String apiUrl;
    private Response response;

    @Given("I have api endpoint \"$apiUrl\"")
    public void givenIHaveApiEndpoint(String apiUrl) {
        log.info("API URL: {}", apiUrl);
        this.apiUrl = apiUrl;
        RestAssured.given().delete(apiUrl);
    }

    @Given("I have api endpoint and three notes in database \"$apiUrl\"")
    public void givenIHaveApiEndpointThreeElements(String apiUrl) {
        String requestBody = "{\n" +
                "  \"title\": \"My first note\",\n" +
                "  \"content\": \"This is my first note\",\n" +
                "  \"userId\": \"1\"\n" +
                "}";
        RestAssured.given().delete(apiUrl);
        for (int i = 0; i < 3; i++) {
            RestAssured.given()
                    .contentType("application/json")
                    .body(requestBody)
                    .post(apiUrl);
        }
        log.info("API URL: {}", apiUrl);
        this.apiUrl = apiUrl;
    }

    @Given("I have api endpoint and three notes with different groups\n" +
            "in database \"$apiUrl\"")
    public void givenIHaveApiEndpointThreeElementsDifferentGroups(String apiUrl) {
        String requestBody1 = "{\n" +
                "  \"title\": \"My first note\",\n" +
                "  \"content\": \"This is my first note\",\n" +
                "  \"userId\": \"Students\"\n" +
                "}";
        String requestBody2 = "{\n" +
                "  \"title\": \"My first note\",\n" +
                "  \"content\": \"This is my first note\",\n" +
                "  \"userId\": \"Professors\"\n" +
                "}";
        RestAssured.given().delete(apiUrl);
        for (int i = 0; i < 2; i++) {
            RestAssured.given()
                    .contentType("application/json")
                    .body(requestBody1)
                    .post(apiUrl);
        }
        RestAssured.given()
                .contentType("application/json")
                .body(requestBody2)
                .post(apiUrl);
        log.info("API URL: {}", apiUrl);
        this.apiUrl = apiUrl;
    }

    @When("I send GET request without arguments")
    public void whenISendGetRequestWithoutArguments() {
        this.response = RestAssured.given()
                .contentType("application/json")
                .get(apiUrl);
        log.info("Response: {}", response.asString());
    }

    @Then("response status code should be $statusCode")
    public void thenResponseStatusShouldBe(int statusCode) {
        response.then().statusCode(statusCode);
    }

    @Then("it should return empty Json:$expectedJson")
    public void thenItReturnJson(String expectedJson) {
        // there should be a better way to do this,
        // but I had trouble with escaping quotes
        // and new line characters
        response.then()
                .body(containsString("[]"));
    }

    @Then("it should return Json with three notes")
    public void thenItReturnJsonWithThreeNotes() {
        // Define a regex pattern to match the expected JSON structure
        String regexPattern = "\\[\\{"
                + "\"id\":\"[a-zA-Z0-9-]+\","
                + "\"title\":\"My first note\","
                + "\"content\":\"This is my first note\","
                + "\"userId\":\"1\"\\},"
                + "\\{"
                + "\"id\":\"[a-zA-Z0-9-]+\","
                + "\"title\":\"My first note\","
                + "\"content\":\"This is my first note\","
                + "\"userId\":\"1\"\\},"
                + "\\{"
                + "\"id\":\"[a-zA-Z0-9-]+\","
                + "\"title\":\"My first note\","
                + "\"content\":\"This is my first note\","
                + "\"userId\":\"1\"\\}\\]";

        // Get the response body as a string
        String responseBody = response.getBody().asString();

        // Use regex pattern to match the response body
        if (!responseBody.matches(regexPattern)) {
            throw new AssertionError("Response body does not match the expected pattern");
        }
    }

    @Then("it should return Json with three notes with different groups")
    public void thenItReturnJsonWithThreeNotesDifferentGroups() {
        // Define a regex pattern to match the expected JSON structure
        String regexPattern = "\\[\\{"
                + "\"id\":\"[a-zA-Z0-9-]+\","
                + "\"title\":\"My first note\","
                + "\"content\":\"This is my first note\","
                + "\"userId\":\"Students\"\\},"
                + "\\{"
                + "\"id\":\"[a-zA-Z0-9-]+\","
                + "\"title\":\"My first note\","
                + "\"content\":\"This is my first note\","
                + "\"userId\":\"Students\"\\},"
                + "\\{"
                + "\"id\":\"[a-zA-Z0-9-]+\","
                + "\"title\":\"My first note\","
                + "\"content\":\"This is my first note\","
                + "\"userId\":\"Professors\"\\}\\]";

        // Get the response body as a string
        String responseBody = response.getBody().asString();

        // Use regex pattern to match the response body
        if (!responseBody.matches(regexPattern)) {
            throw new AssertionError("Response body does not match the expected pattern");
        }
    }
}

/*
        this.requestBody = requestBody;
        log.info("Request: {}", requestBody);
        this.response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post(apiUrl);
        log.info("Response: {}", response.asString());

                response.then()
                .body(containsString("\"title\":\"My first note\""))
                .body(containsString("\"content\":\"This is my first note\""))
                .body(containsString("\"userId\":\"1\""));
 */
