package com.example.backend.displaying;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;

import java.net.ConnectException;
import java.rmi.UnexpectedException;

import static org.hamcrest.Matchers.containsString;

@Slf4j
public class DisplayingNoteSteps extends Steps {
    private String apiUrl;
    private String chosenId;
    private String responseBody;
    private Response inputResponse;
    private Response outputResponse;
    private final int StatusOK=201;

    @Given("I have api endpoint \"$apiUrl\"")
    public void givenIHaveApiEndpoint(String apiUrl) {
        log.info("API URL: {}", apiUrl);
        this.apiUrl = apiUrl;
    }

    @Given("Chosen note is in DataBase")
    public void givenHaveNoteInDB() throws ConnectException {
        int userId=111;
        this.responseBody ="{ \"title\": \"My first note\"," +
                "\"content\": \"This is my first note\"," +
                " \"userId\": \""+userId+"\"}";
        log.info("Request: {}", responseBody);
        this.inputResponse = RestAssured.given()
                .contentType("application/json")
                .body(responseBody)
                .post(apiUrl);
        log.info("Response: {}", inputResponse.asString());
        if(inputResponse.statusCode()!=StatusOK){
            throw new ConnectException("Can't connect API! Status Code: "+ inputResponse.statusCode());
        }
        this.inputResponse = RestAssured.given()
                .contentType("application/json")
                .get(apiUrl+"/user/"+userId);
        log.info("Response: {}", inputResponse.asString());
        //Getting id of first note
        var body=inputResponse.body().asString();
        chosenId=body.substring(body.indexOf(":\"")+2,body.indexOf("\","));
    }

    @Given("Chosen note is not in DataBase")
    public void givenHaveNoteIsNotInDB(){
        String idThatIsNotInDB="111222333444555666777888999";
        chosenId=idThatIsNotInDB;
    }

    @When("I send GET request with chosen id")
    public void whenISendGetRequestWithChosenId() {
        this.outputResponse = RestAssured.given()
                .contentType("application/json")
                .get(apiUrl+"/"+chosenId);
        log.info("Response: {}", outputResponse.asString());
    }

    @Then("It return note with id equal to chosen id")
    public void thenItReturnNoteWithIdEqualToChosenId() throws Exception {
        outputResponse.then()
                .body(containsString(chosenId));
    }

    @Then("It return status code \"$statusCode\"")
    public void thenItReturnStatusCode(int statusCode) throws Exception {
        if(outputResponse.statusCode()!=statusCode)
        {
            throw new UnexpectedException("Status code is different that expected! Status Code: "+outputResponse.statusCode());
        }
    }
}
