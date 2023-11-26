package com.example.backend.adding;

import static org.hamcrest.Matchers.containsString;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;

@Slf4j
public class AddNoteSteps extends Steps {

  private String apiUrl;
  private String requestBody;
  private Response response;

  @Given("I have api endpoint \"$apiUrl\"")
  public void givenIHaveApiEndpoint(String apiUrl) {
    log.info("API URL: {}", apiUrl);
    this.apiUrl = apiUrl;
  }

  @When("I send POST request with body:$requestBody")
  public void whenISendPostRequestWithBody(String requestBody) {
    this.requestBody = requestBody;
    log.info("Request: {}", requestBody);
    this.response = RestAssured.given()
        .contentType("application/json")
        .body(requestBody)
        .post(apiUrl);
    log.info("Response: {}", response.asString());
  }

  @Then("response status code should be $statusCode")
  public void thenResponseStatusShouldBe(int statusCode) {
    response.then().statusCode(statusCode);
  }

  @Then("it should return Json:$expectedJson")
  public void thenItReturnJson(String expectedJson) {
    // there should be a better way to do this
    // but I had trouble with escaping quotes
    // and new line characters
    response.then()
        .body(containsString("\"title\":\"My first note\""))
        .body(containsString("\"content\":\"This is my first note\""))
        .body(containsString("\"userId\":\"1\""));

  }
}